package com.amriksinghpadam.myplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.amriksinghpadam.api.APIConstent;
import com.google.android.material.tabs.TabLayout;

import myviewpager.CustomPager;

public class PlayerActivity extends AppCompatActivity {

    private CustomPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TabLayout tabLayout;
    private int section;
    private RelativeLayout refreshIconLayout, progressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().hide();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayoutId);
        refreshIconLayout = findViewById(R.id.refresh_layout_id);
        progressBarLayout = findViewById(R.id.progressBar_layout_id);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
        refreshIconLayout.setVisibility(View.GONE);
        progressBarLayout.setVisibility(View.GONE);

        if (getIntent() != null) {
            section = getIntent().getExtras().getInt(APIConstent.SECTION);
        }
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), section);
        viewPager.setAdapter(myViewPagerAdapter);

    }


}
