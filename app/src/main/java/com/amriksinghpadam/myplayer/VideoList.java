package com.amriksinghpadam.myplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.amriksinghpadam.api.VideoAPIRequestWithFilter;

import org.json.JSONObject;

import java.util.ArrayList;

public class VideoList extends AppCompatActivity {
    private Toolbar vListToolbar;
    private Spinner listSpinner;
    private RelativeLayout noDataImgView;
    private RecyclerView videoListRecyclerView;
    private RelativeLayout progressBarLayout;
    private ArrayList artistIdList = new ArrayList();
    private ArrayList singerArrayList = new ArrayList();
    private ArrayList videoTittleList = new ArrayList();
    private ArrayList videoBannerList = new ArrayList();
    private ArrayList videoURLList = new ArrayList();
    private ArrayList descriptionList = new ArrayList();
    private ArrayList contentIdList = new ArrayList();
    private int artistId;
    private String selectedSingerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        videoListRecyclerView = findViewById(R.id.videoListRecyclerViewId);
        listSpinner = findViewById(R.id.spinnerSinger);
        vListToolbar = findViewById(R.id.vListToolbarId);
        noDataImgView = findViewById(R.id.nodata_layout);
        noDataImgView.setVisibility(View.GONE);
        progressBarLayout = findViewById(R.id.progressBar_id);
        progressBarLayout.setVisibility(View.GONE);
        setSupportActionBar(vListToolbar);

        String singerName = getIntent().getStringExtra(APIConstant.SINGER_NAME);
        singerArrayList.addAll(getIntent().getStringArrayListExtra(APIConstant.ARTIST_ARRAY_LIST));
        artistIdList.addAll(getIntent().getStringArrayListExtra(APIConstant.ARTIST_ID_LIST));
        int position = 0;
        for (int i = 0; i < singerArrayList.size(); i++) {
            if(singerArrayList.get(i).toString().equals(singerName)){
                position = i;
            }
        }
        SpinnerItemAdapter adapter = new SpinnerItemAdapter(this, R.layout.spinner_single_item_singer, singerArrayList);
        listSpinner.setAdapter(adapter);
        listSpinner.setSelection(position);
        GridLayoutManager layoutManager = new GridLayoutManager(VideoList.this, 3, LinearLayoutManager.VERTICAL, false);
        videoListRecyclerView.setLayoutManager(layoutManager);

        listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String id = artistIdList.get(position).toString();
                APIConstant.IS_SHARED_PREF_SAVED = false;
                videoBannerList.clear();
                videoTittleList.clear();
                videoURLList.clear();
                descriptionList.clear();
                contentIdList.clear();
                callAPIOnSpinnerChange(id);

                //getVideoData();
//                VideoListAdapter adapter = new VideoListAdapter(VideoList.this,noDataImgView, videoBannerList, videoTittleList);
//                videoListRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void callAPIOnSpinnerChange(String artistId){
        String artistParam = APIConstant.FILTER_ARTIST_VIDEO_URL_PARAM.replace("$$artist$id$$",String.valueOf(artistId));
        String filterVideoReqURL = APIConstant.SSL_SCHEME+APIConstant.BASE_URL+artistParam;
        String sharedPrefKey = SharedPrefUtil.VIDEO_BY_ARTIST_JSON_RESPONSE;
        VideoAPIRequestWithFilter videoRequest = new VideoAPIRequestWithFilter(VideoList.this,null);
        videoRequest.callVideoAPIRequest(this,progressBarLayout,filterVideoReqURL,sharedPrefKey);


    }

    public void getVideoData(){

        ArrayList<JSONObject> artistVideoArrayList = SharedPrefUtil.getFilterArtistVideoJsonResponse(VideoList.this,noDataImgView);
        if(artistVideoArrayList.size()>0 && artistVideoArrayList != null) {
            for (int i = 0; i < artistVideoArrayList.size(); i++) {
                try {
                    JSONObject obj = artistVideoArrayList.get(i);
                    if (i == 0) {
                        this.artistId = Integer.parseInt(obj.getString("artistid"));
                        selectedSingerName = obj.getString("artistname");
                    }
                    contentIdList.add(obj.getString("videoid"));
                    videoBannerList.add(obj.getString("videobannerurl"));
                    videoTittleList.add(obj.getString("videotitle"));
                    descriptionList.add(obj.getString("videodescription"));
                    videoURLList.add(obj.getString("videourl"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        VideoListAdapter adapter = new VideoListAdapter(VideoList.this,videoBannerList, videoTittleList);
        adapter.setVideoArrayList(this.artistId,selectedSingerName,descriptionList,videoURLList,contentIdList);
        videoListRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoListAdapter.tempCount = 0;
    }
}
