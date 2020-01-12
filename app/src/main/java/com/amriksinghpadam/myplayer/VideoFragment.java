package com.amriksinghpadam.myplayer;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class VideoFragment extends Fragment {
    private ViewPager viewPager;
    private VideoHeaderPagerAdapter adapter;
    private ArrayList<VideoHeaderModel> model;
    private Integer[] color = null;
    private ArgbEvaluator argb = new ArgbEvaluator();
    private View view;
    private CardView cardView;
    private TextView headingText;
    private RecyclerView videoListrecyclerView;
    private ArrayList artistIdList = new ArrayList();
    private ArrayList singerImageArrayList = new ArrayList();
    private ArrayList singerNameArrayList = new ArrayList();
    private ArrayList videoCountArrayList = new ArrayList();
    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private RelativeLayout progressBarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setActionBar(null);
        view = inflater.inflate(R.layout.fragment_video, container, false);
        viewPager = view.findViewById(R.id.videoViewPagerId);
        cardView = view.findViewById(R.id.videoCardViewId);
        headingText = view.findViewById(R.id.headerHeadingId);
        videoListrecyclerView = view.findViewById(R.id.videoRecyclerViewId);
        progressBarLayout = view.findViewById(R.id.progressBar_layout_id);
        progressBarLayout.setVisibility(View.GONE);

        model = new ArrayList<>();
        // adding image, title, desc in model object
        model.add(new VideoHeaderModel(R.drawable.mostwatch, "Most Watched", "Most Watched"));
        model.add(new VideoHeaderModel(R.drawable.newvid, "New Video", "New Video"));
        model.add(new VideoHeaderModel(R.drawable.punjabi, "Hindi & Punjabi", "Hindi & Punjabi"));
        model.add(new VideoHeaderModel(R.drawable.english, "English Video", "English Video"));
        //initializing colors for viewpager
        Integer[] temp_color = {
                getResources().getColor(R.color.most_watch_back_color),
                getResources().getColor(R.color.new_video_back_color),
                getResources().getColor(R.color.hindi_punjabi_back_color),
                getResources().getColor(R.color.english_video_back_color),
        };
        color = temp_color;
        adapter = new VideoHeaderPagerAdapter(model, getContext(),null,progressBarLayout);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(110, 0, 110, 0);
        headingText.setText(model.get(0).getTitle());

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < adapter.getCount() - 1 && position < color.length - 1) {
                    viewPager.setBackgroundColor(
                            (Integer) argb.evaluate(positionOffset,
                                    color[position],
                                    color[position + 1]));
                } else {
                    viewPager.setBackgroundColor(color[color.length - 1]);
                }
            }
            @Override
            public void onPageSelected(int position) {
                headingText.setText(model.get(position).getTitle());
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        videoListrecyclerView.setLayoutManager(layoutManager);
        // Get Artist Data
        ArrayList<JSONObject> artistArrayList = SharedPrefUtil.getSideNavArtistJsonResponse(getContext(),null);
        for (int i=0;i<artistArrayList.size();i++){
            try {
                JSONObject obj = artistArrayList.get(i);
                singerImageArrayList.add(obj.getString(APIConstant.IMAGEURL));
                singerNameArrayList.add(obj.getString("artistname"));
                artistIdList.add(obj.getString("artistid"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 8; i++) {
            videoCountArrayList.add(String.valueOf(2 * (i + 1)));
        }
        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(),singerImageArrayList,
                singerNameArrayList,artistIdList,progressBarLayout);
        videoListrecyclerView.setAdapter(videoRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.tempCount = 0;
        videoRecyclerViewAdapter.tempCount = 0;
    }
}
