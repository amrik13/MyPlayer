package com.amriksinghpadam.myplayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.amriksinghpadam.api.APIConstent;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SongFragment extends Fragment {

    private CarouselView carouselView;
    //private int[] carouselImages = {R.drawable.aaaae,R.drawable.bbb,R.drawable.ccc,R.drawable.ddd,R.drawable.image,R.drawable.images};
    private RecyclerView recyclerView,recyclerView2, recyclerView3,recyclerView4;
    private TextView moreFeature,moreLatest,moreDiscover;
    private ArrayList artistImageArrayList = new ArrayList();
    private ArrayList artistTitleArrayList = new ArrayList();
    private ArrayList latestImageArrayList = new ArrayList();
    private ArrayList latestTitleArrayList = new ArrayList();
    private ArrayList discoverImageArrayList = new ArrayList();
    private ArrayList discoverTitleArrayList = new ArrayList();
    PlayerLayoutAdapter playerLayoutAdapter,playerLayoutAdapter2,playerLayoutAdapter3;
    private ImageView topImgLayout1,topImgLayout2,topImgLayout3,topImgLayout4;
    private ArrayList topImgURLList = new ArrayList();
    private ArrayList autoCarouselImgURLList = new ArrayList();
    private ArrayList autoCarouselTitleURLList = new ArrayList();
    private int tempCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setActionBar(null);
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        carouselView = view.findViewById(R.id.carouselView);
        recyclerView = view.findViewById(R.id.artistRecyclerViewId);
        recyclerView2 = view.findViewById(R.id.latestSongRecyclerViewId);
        recyclerView3 = view.findViewById(R.id.generRecyclerViewId);
        moreFeature = view.findViewById(R.id.more_fetured_id);
        moreLatest = view.findViewById(R.id.more_latest_id);
        moreDiscover = view.findViewById(R.id.more_discover_id);
        topImgLayout1 = view.findViewById(R.id.topImg1);
        topImgLayout2 = view.findViewById(R.id.topImg2);
        topImgLayout3 = view.findViewById(R.id.topImg3);
        topImgLayout4 = view.findViewById(R.id.topImg4);

        ArrayList<JSONObject> topImgArrayList = SharedPrefUtil.getTopImageJsonResponse(getContext());
        if(topImgArrayList.size()>0 && topImgArrayList!=null) {
            for (int i = 0; i < topImgArrayList.size(); i++) {
                try {
                    topImgURLList.add(topImgArrayList.get(i).getString(APIConstent.IMAGEURL));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            loadTopBlockImage();
        }else{
            showToast(getResources().getString(R.string.empty_api_response));
        }

        ArrayList<JSONObject> autoCarouselArrayList = SharedPrefUtil.getTopAutoCarouselJsonResponse(getContext());
        if(autoCarouselArrayList.size()>0 && autoCarouselArrayList!=null) {
            for (int i = 0; i < autoCarouselArrayList.size(); i++) {
                try {
                    autoCarouselImgURLList.add(autoCarouselArrayList.get(i).getString(APIConstent.IMAGEURL));
                    autoCarouselTitleURLList.add(autoCarouselArrayList.get(i).getString(APIConstent.TITLE));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            showToast(getResources().getString(R.string.empty_api_response));
        }


        ArrayList<JSONObject> artistArrayList = SharedPrefUtil.getSideNavArtistJsonResponse(getContext(),null);
        for (int i=0;i<artistArrayList.size();i++){
            try {
                JSONObject obj = artistArrayList.get(i);
                artistImageArrayList.add(obj.getString(APIConstent.IMAGEURL));
                artistTitleArrayList.add(obj.getString("artistname"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayList<JSONObject> latestArrayList = SharedPrefUtil.getSideNavLatestJsonResponse(getContext(),null);
        for (int i=0;i<latestArrayList.size();i++){
            try {
                JSONObject obj = latestArrayList.get(i);
                latestImageArrayList.add(obj.getString("songbannerurl"));
                latestTitleArrayList.add(obj.getString("songtitle"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayList<JSONObject> discoverArrayList = SharedPrefUtil.getSideNavDiscoverJsonResponse(getContext(),null);
        for (int i=0;i<discoverArrayList.size();i++){
            try {
                JSONObject obj = discoverArrayList.get(i);
                discoverImageArrayList.add(obj.getString(APIConstent.IMAGEURL));
                discoverTitleArrayList.add(obj.getString("language").toUpperCase());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(autoCarouselImgURLList!=null && autoCarouselImgURLList.size()>0) {
            carouselView.setPageCount(autoCarouselImgURLList.size());
            carouselView.setImageListener(imageListener);
        }else{
            showToast("oops! unable to load images.");
        }
        final String type_song = getResources().getString(R.string.song);
        moreFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempCount == 0) {
                    Intent intent = new Intent(getContext(), CommonPlayerGridView.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(APIConstent.TITLE, getResources().getString(R.string.artist_title));
                    bundle.putString(APIConstent.TYPE,type_song);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                    tempCount++;
                }
            }
        });
        moreLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempCount == 0){
                    Intent intent = new Intent(getContext(),CommonPlayerGridView.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(APIConstent.TITLE,getResources().getString(R.string.latest_song));
                    bundle.putString(APIConstent.TYPE,type_song);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    tempCount++;
                }
            }
        });
        moreDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempCount == 0) {
                    Intent intent = new Intent(getContext(), CommonPlayerGridView.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(APIConstent.TITLE,getResources().getString(R.string.discover));
                    bundle.putString(APIConstent.TYPE,type_song);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    tempCount++;
                }
            }
        });
        initRecyclerView();
        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            Glide.with(getContext()).load(autoCarouselImgURLList.get(position)).into(imageView);
        }
    };

    public void initRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        playerLayoutAdapter = new PlayerLayoutAdapter(getContext(),artistImageArrayList,artistTitleArrayList,false);
        recyclerView.setAdapter(playerLayoutAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        playerLayoutAdapter2 = new PlayerLayoutAdapter(getContext(),latestImageArrayList,latestTitleArrayList,true);
        recyclerView2.setAdapter(playerLayoutAdapter2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager3);
        playerLayoutAdapter3 = new PlayerLayoutAdapter(getContext(),discoverImageArrayList,discoverTitleArrayList,false);
        recyclerView3.setAdapter(playerLayoutAdapter3);
    }

    public void loadTopBlockImage(){
        if(topImgURLList.size()>=4 && topImgURLList!=null) {

            Glide.with(getContext()).load(topImgURLList.get(0).toString()).into(topImgLayout1);
            Glide.with(getContext()).load(topImgURLList.get(1).toString()).into(topImgLayout2);
            Glide.with(getContext()).load(topImgURLList.get(2).toString()).into(topImgLayout3);
            Glide.with(getContext()).load(topImgURLList.get(3).toString()).into(topImgLayout4);

        }else{
            showToast("oops! unable to load images.");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        playerLayoutAdapter.tempCount = 0;
        playerLayoutAdapter2.tempCount = 0;
        playerLayoutAdapter3.tempCount = 0;
        this.tempCount = 0;
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}
