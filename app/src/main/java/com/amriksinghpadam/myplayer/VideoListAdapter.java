package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListHolder> {

    private Context context;
    private ArrayList imageList = new ArrayList();
    private ArrayList tittleList = new ArrayList();
    static int tempCount = 0;

    public VideoListAdapter(Context context, ArrayList iList, ArrayList tList){
        this.context = context;
        this.imageList.addAll(iList);
        this.tittleList.addAll(tList);
    }
    @NonNull
    @Override
    public VideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_list_recycler_view_single_item,parent,false);
        VideoListHolder holder = new VideoListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListHolder holder, final int position) {
        //Glide.with(context).asBitmap().load(imageList.get(position)).load(holder.videoBanner);

        holder.videoBanner.setImageDrawable((BitmapDrawable) imageList.get(position));
        holder.videoTitle.setText(tittleList.get(position).toString());
        holder.videoBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempCount==0){
                    Intent intent = new Intent(context,VideoExoPlayer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",tittleList.get(position).toString());
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
        return imageList.size();
    }

    class VideoListHolder extends RecyclerView.ViewHolder{
        ImageView videoBanner;
        TextView videoTitle;

        public VideoListHolder(@NonNull View itemView) {
            super(itemView);
            videoBanner = itemView.findViewById(R.id.video2ListBannerId);
            videoTitle = itemView.findViewById(R.id.videoListTitleId);

        }
    }

}
