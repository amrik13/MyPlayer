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
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList singerImageArrayList = new ArrayList();
    private ArrayList singerNameArrayList = new ArrayList();
    private ArrayList videoCountArrayList = new ArrayList();
    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setActionBar(null);
        view = inflater.inflate(R.layout.fragment_video, container, false);
        viewPager = view.findViewById(R.id.videoViewPagerId);
        cardView = view.findViewById(R.id.videoCardViewId);
        headingText = view.findViewById(R.id.headerHeadingId);
        videoListrecyclerView = view.findViewById(R.id.videoRecyclerViewId);
        model = new ArrayList<>();
        // adding image, title, desc in model object
        model.add(new VideoHeaderModel(R.drawable.a, "Most Watched", "Most Watched"));
        model.add(new VideoHeaderModel(R.drawable.b, "New Video", "New Video"));
        model.add(new VideoHeaderModel(R.drawable.c, "Hindi & Punjabi", "Hindi & Punjabi"));
        model.add(new VideoHeaderModel(R.drawable.d, "English Video", "English Video"));
        //initializing colors for viewpager
        Integer[] temp_color = {
                getResources().getColor(R.color.yellow1),
                getResources().getColor(R.color.blue2),
                getResources().getColor(R.color.green3),
                getResources().getColor(R.color.red4),
        };
        color = temp_color;
        adapter = new VideoHeaderPagerAdapter(model, getContext());
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
        for (int i = 0; i < 8; i++) {
            singerImageArrayList.add(getResources().getDrawable(R.drawable.image));
            singerNameArrayList.add("Singer Name " + (i + 1));
            videoCountArrayList.add(String.valueOf(2 * (i + 1)));
        }
        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(
                getContext(), singerImageArrayList,
                singerNameArrayList,
                videoCountArrayList);
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
