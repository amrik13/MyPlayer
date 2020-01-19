package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListHolder> {

    static int tempCount = 0;
    private Context context;
    private String selectedSingerName;
    private ArrayList videoBannerList = new ArrayList();
    private ArrayList videoTittleList = new ArrayList();
    private ArrayList videoURLList = new ArrayList();
    private ArrayList descriptionList = new ArrayList();
    private ArrayList contentIdList = new ArrayList();
    private int artistId;

    public VideoListAdapter(Context context, ArrayList videoBannerList, ArrayList videoTittleList) {
        this.context = context;
        this.videoBannerList.addAll(videoBannerList);
        this.videoTittleList.addAll(videoTittleList);
    }

    @NonNull
    @Override
    public VideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_list_recycler_view_single_item, parent, false);
        VideoListHolder holder = new VideoListHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull VideoListHolder holder, final int position) {

        Glide.with(context).load(videoBannerList.get(position)).into(holder.videoBanner);
        //holder.videoBanner.setImageDrawable((BitmapDrawable) videoBannerList.get(position));
        holder.videoTitle.setText(videoTittleList.get(position).toString());
        holder.videoBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempCount == 0) {
                    Intent intent = new Intent(context, VideoExoPlayer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(APIConstant.TITLE, videoTittleList.get(position).toString());
                    bundle.putString(APIConstant.CONTENT_ID, contentIdList.get(position).toString());
                    bundle.putString(APIConstant.SINGER_NAME, selectedSingerName);
                    bundle.putString(APIConstant.TYPE,APIConstant.VIDEO);
                    bundle.putString(APIConstant.ARTIST_ID,String.valueOf(artistId));
                    bundle.putString(APIConstant.VIDEO_DESCRIPTION,descriptionList.get(position).toString());
                    bundle.putString(APIConstant.VIDEO_URL,videoURLList.get(position).toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    tempCount++;
                }

                // Toast.makeText(context,"Player Starting",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoBannerList.size();
    }

    public void setVideoArrayList(int artistId, String selectedSingerName, ArrayList descriptionList,
                                  ArrayList videoURLList,ArrayList contentIdList ) {
        this.artistId = artistId;
        this.contentIdList.addAll(contentIdList);
        this.selectedSingerName = selectedSingerName;
        this.descriptionList.addAll(descriptionList);
        this.videoURLList.addAll(videoURLList);

    }

    class VideoListHolder extends RecyclerView.ViewHolder {
        ImageView videoBanner;
        TextView videoTitle;

        public VideoListHolder(@NonNull View itemView) {
            super(itemView);
            videoBanner = itemView.findViewById(R.id.video2ListBannerId);
            videoTitle = itemView.findViewById(R.id.videoListTitleId);

        }
    }

}
