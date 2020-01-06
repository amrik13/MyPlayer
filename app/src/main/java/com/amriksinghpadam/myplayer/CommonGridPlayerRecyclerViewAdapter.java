package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CommonGridPlayerRecyclerViewAdapter extends RecyclerView.Adapter<CommonGridPlayerRecyclerViewAdapter.GridPlayerViewHolder> {

    private Context context;
    int tempCount = 0;
    private ArrayList bannerList= new ArrayList();
    private ArrayList titleList = new ArrayList();
    public CommonGridPlayerRecyclerViewAdapter(Context context,ArrayList bList,ArrayList tList){
        this.context = context;
        bannerList.addAll(bList);
        titleList.addAll(tList);
    }

    @NonNull
    @Override
    public GridPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_grid_recycler_singe_item,parent,false);
        GridPlayerViewHolder holder = new GridPlayerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridPlayerViewHolder holder, final int position) {
        Glide.with(context).load(bannerList.get(position)).into(holder.videoBanner);
        //holder.videoBanner.setImageDrawable((BitmapDrawable) bannerList.get(position));
        holder.videoTitle.setText(titleList.get(position).toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempCount==0){
                    Intent intent = new Intent(context,VideoExoPlayer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",titleList.get(position).toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    tempCount++;
                }

                //Toast.makeText(context,"Player Starting",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    class GridPlayerViewHolder extends RecyclerView.ViewHolder{
        ImageView videoBanner;
        CardView cardView;
        TextView videoTitle;
        public GridPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.common_grid_cardView_id);
            videoBanner = itemView.findViewById(R.id.commonGridBannerId);
            videoTitle = itemView.findViewById(R.id.commonGridTitleId);
        }
    }
}
