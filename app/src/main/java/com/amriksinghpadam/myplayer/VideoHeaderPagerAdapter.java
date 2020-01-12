package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.NavigationItemRequest;
import com.amriksinghpadam.api.SharedPrefUtil;

import java.util.ArrayList;

public class VideoHeaderPagerAdapter extends PagerAdapter {
    ArrayList<VideoHeaderModel> models = new ArrayList<>();
    Context context;
    int tempCount = 0;
    private LayoutInflater inflater;
    private RelativeLayout refreshicon;
    private RelativeLayout progressBarLayout;

    public VideoHeaderPagerAdapter(ArrayList<VideoHeaderModel> models, Context context, RelativeLayout refreshicon, RelativeLayout progressBarLayout) {
        this.models.addAll(models);
        this.context = context;
        this.refreshicon = refreshicon;
        this.progressBarLayout = progressBarLayout;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_view_viewpager_item, container, false);
        ImageView image;
        image = view.findViewById(R.id.videoBannerImageId);
        image.setImageResource(models.get(position).getImage());
        container.addView(view, 0);

        final Intent intent = new Intent(context, CommonPlayerGridView.class);
        final Bundle bundle = new Bundle();
        final NavigationItemRequest navRequest = new NavigationItemRequest(context,
                progressBarLayout,refreshicon,intent,bundle,true);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tempCount == 0) {
                    switch (position){
                        case 0:
                            navRequest.startNavItemActivity(context.getResources().getString(R.string.most_watched),
                                    context.getResources().getString(R.string.video), APIConstant.MOST_WATCHED_URL_PARAM,
                                    SharedPrefUtil.MOST_WATCHED_JSON_RESPONSE);
                            break;
                        case 1:
                            navRequest.startNavItemActivity(context.getResources().getString(R.string.new_video),
                                    context.getResources().getString(R.string.video), APIConstant.NEW_ARIVAL_URL_PARAM,
                                    SharedPrefUtil.NEW_ARIVAL_JSON_RESPONSE);
                            break;
                        case 2:
                            navRequest.startNavItemActivity(context.getResources().getString(R.string.hindi_and_punjabi),
                                    context.getResources().getString(R.string.video), APIConstant.HINDI_PUNJABI_URL_PARAM,
                                    SharedPrefUtil.HINDI_PUNJABI_JSON_RESPONSE);
                            break;
                        case 3:
                            navRequest.startNavItemActivity(context.getResources().getString(R.string.english_video),
                                    context.getResources().getString(R.string.video), APIConstant.ENGLISH_URL_PARAM,
                                    SharedPrefUtil.ENGLISH_JSON_RESPONSE);
                            break;
                        default:
                            break;

                    }
                }

            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            super.destroyItem(container, position, object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
