package com.amriksinghpadam.myplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VideoList extends AppCompatActivity {
    private Toolbar vListToolbar;
    private Spinner listSpinner;
    private String[] singerArray = new String[8];
    private RecyclerView videoListRecyclerView;
    private ArrayList bannerList = new ArrayList();
    private ArrayList tittleList = new ArrayList();

    VideoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        videoListRecyclerView = findViewById(R.id.videoListRecyclerViewId);
        listSpinner = findViewById(R.id.spinnerSinger);
        vListToolbar = findViewById(R.id.vListToolbarId);
        setSupportActionBar(vListToolbar);

        for (int i = 0; i < 8; i++) {
            singerArray[i] = "Singer Name " + (i + 1);
        }
        int position = Integer.parseInt(getIntent().getStringExtra("sName"));

        SpinnerItemAdapter adapter = new SpinnerItemAdapter(this, R.layout.spinner_single_item_singer, singerArray);
        listSpinner.setAdapter(adapter);
        listSpinner.setSelection(position);

        GridLayoutManager layoutManager = new GridLayoutManager(VideoList.this, 3, LinearLayoutManager.VERTICAL, false);
        videoListRecyclerView.setLayoutManager(layoutManager);

        listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Toast.makeText(VideoList.this,position+"",Toast.LENGTH_SHORT).show();
                //sName.setText(singerArray[position]);
                bannerList.clear();
                tittleList.clear();
                Drawable resImage;

                switch (position) {
                    case 0:
                        resImage = getResources().getDrawable(R.drawable.d);
                        break;
                    case 1:
                        resImage = getResources().getDrawable(R.drawable.c);
                        break;
                    case 2:
                        resImage = getResources().getDrawable(R.drawable.b);
                        break;
                    case 3:
                        resImage = getResources().getDrawable(R.drawable.a);
                        break;
                    default:
                        resImage = getResources().getDrawable(R.drawable.punjabi);
                        break;
                }

                for (int i = 0; i < 16; i++) {
                    bannerList.add(resImage);
                    tittleList.add(singerArray[position]);
                }
                VideoListAdapter adapter = new VideoListAdapter(VideoList.this, bannerList, tittleList);
                videoListRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

          //ATTENTION: This was auto-generated to handle app links.
//        Intent appLinkIntent = getIntent();
//        String appLinkAction = appLinkIntent.getAction();
//        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoListAdapter.tempCount = 0;
    }
}
