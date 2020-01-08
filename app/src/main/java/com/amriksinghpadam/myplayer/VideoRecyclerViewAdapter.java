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

import java.util.ArrayList;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.SingerListAdapterVH> {

    int tempCount = 0;
    private Context context;
    private ArrayList singerImageArrayList = new ArrayList();
    private ArrayList singerNameArrayList = new ArrayList();
    private ArrayList videoCountArrayList = new ArrayList();
    private View view;

    public VideoRecyclerViewAdapter(
            Context context, ArrayList singerImageArrayList,
            ArrayList singerNameArrayList, ArrayList videoCountArrayList) {
        this.context = context;
        this.singerImageArrayList.addAll(singerImageArrayList);
        this.singerNameArrayList.addAll(singerNameArrayList);
        this.videoCountArrayList.addAll(videoCountArrayList);
    }

    @NonNull
    @Override
    public VideoRecyclerViewAdapter.SingerListAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.inflate(R.layout.video_recyclerview_item, parent, false);
        SingerListAdapterVH holder = new SingerListAdapterVH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoRecyclerViewAdapter.SingerListAdapterVH holder, final int position) {

        //Glide.with(context).asBitmap().load(singerImageArrayList.get(position)).load(holder.singerImage);
        holder.singerImage.setImageDrawable((BitmapDrawable) singerImageArrayList.get(position));
        holder.singerName.setText(singerNameArrayList.get(position).toString());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,singerNameArrayList.get(position)+"--"+videoCountArrayList.get(position),Toast.LENGTH_SHORT).show();
                holder.bind(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return singerImageArrayList.size();
    }

    class SingerListAdapterVH extends RecyclerView.ViewHolder {
        ImageView singerImage;
        TextView singerName;
        RelativeLayout layout;

        public SingerListAdapterVH(@NonNull View itemView) {
            super(itemView);
            singerImage = itemView.findViewById(R.id.contentItemImageId);
            singerName = itemView.findViewById(R.id.contentNameId);
            layout = itemView.findViewById(itemView.getId());
        }

        public void bind(int position) {
            if (tempCount == 0) {
                Intent intent = new Intent(context, VideoList.class);
                Bundle bundle = new Bundle();
                bundle.putString("sName", position + "");
                intent.putExtras(bundle);
                context.startActivity(intent);
                tempCount++;
            }

        }

    }

}
