package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SongAPIRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainFragment extends Fragment {
    private View layout1,layout2;
    private Context context;
    public static int tempCount=0;
    private TextView songTitleView,videoTitleView;
    private View songBannerView,videoBannerView;
    private String songBannerUrl,videoBannerUrl;
    private RelativeLayout refreshIconLayout,progressBarLayout;

    public MainFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        layout1 = view.findViewById(R.id.linearLayout1);
        layout2 = view.findViewById(R.id.linearLayout2);
        songTitleView = view.findViewById(R.id.songTitleId);
        videoTitleView = view.findViewById(R.id.videoTitleId);
        songBannerView = view.findViewById(R.id.songImageView);
        videoBannerView = view.findViewById(R.id.videoImageView);
        refreshIconLayout = view.findViewById(R.id.refresh_layout_id);
        progressBarLayout = view.findViewById(R.id.progressBar_layout_id);
        refreshIconLayout.setVisibility(View.GONE);
        progressBarLayout.setVisibility(View.GONE);


        Bundle bundle = getArguments();
        if (bundle != null) {
            String songTitle = bundle.getString(APIConstant.SONG_TITLE);
            String videoTitle = bundle.getString(APIConstant.VIDEO_TITLE);
            songBannerUrl = bundle.getString(APIConstant.SONG_BANNER);
            videoBannerUrl = bundle.getString(APIConstant.VIDEO_BANNER);
            songTitleView.setText(songTitle.toUpperCase());
            videoTitleView.setText(videoTitle.toUpperCase());
            loadBackgroundImage();
        }
        final Intent intent = new Intent(context, PlayerActivity.class);
        final Bundle bundle2 = new Bundle();
        final SongAPIRequest songAPIRequest = new SongAPIRequest(context, intent, bundle2, progressBarLayout, refreshIconLayout);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempCount == 0) {
                    String topImgapiURL = APIConstant.SSL_SCHEME + APIConstant.BASE_URL + APIConstant.TOP_IMAGE_URL_PARAM;
                    songAPIRequest.callMediaAPIRequest(1, topImgapiURL);
                }
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempCount == 0) {
                    //songAPIRequest.callMediaAPIRequest(2);
                }
            }
        });
        refreshIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIConstant.CONNECTIVITY = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Network network = connectivityManager.getActiveNetwork();
                    if (network != null) {
                        refreshIconLayout.setVisibility(View.GONE);
                    }
                } else {
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null) {
                        refreshIconLayout.setVisibility(View.GONE);
                    }
                }
                if (!APIConstant.CONNECTIVITY) {
                    MainFragment.tempCount = 0;
                    refreshIconLayout.setVisibility(View.VISIBLE);
                    showToast(context.getResources().getString(R.string.internet_error_msg));
                }
            }
        });

        return view;
    }


    public void loadBackgroundImage() {
        Glide.with(context).load(songBannerUrl).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                songBannerView.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
        Glide.with(context).load(videoBannerUrl).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                videoBannerView.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        tempCount = 0;
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
