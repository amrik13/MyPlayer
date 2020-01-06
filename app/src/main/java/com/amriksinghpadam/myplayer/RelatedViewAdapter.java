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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class RelatedViewAdapter extends RecyclerView.Adapter<RelatedViewAdapter.SingerListAdapterVH1> {

    private Context context;
    private ArrayList singerImageArrayList = new ArrayList();
    private ArrayList singerNameArrayList = new ArrayList();
    private View view;
    private FragmentManager fm;

    public RelatedViewAdapter(
            Context context,ArrayList singerImageArrayList,
            ArrayList singerNameArrayList,FragmentManager fm) {
        this.fm = fm;
        this.context = context;
        this.singerImageArrayList.addAll(singerImageArrayList);
        this.singerNameArrayList.addAll(singerNameArrayList);

    }

    @NonNull
    @Override
    public RelatedViewAdapter.SingerListAdapterVH1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.inflate(R.layout.video_recyclerview_item,parent,false);
        SingerListAdapterVH1 holder = new SingerListAdapterVH1(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RelatedViewAdapter.SingerListAdapterVH1 holder, final int position) {

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
    public int getItemCount() { return singerImageArrayList.size(); }

    class SingerListAdapterVH1 extends RecyclerView.ViewHolder {
        ImageView singerImage;
        TextView singerName,videoCount;
        RelativeLayout layout;

        public SingerListAdapterVH1(@NonNull View itemView) {
            super(itemView);
            singerImage = itemView.findViewById(R.id.videoItemImageId);
            singerName = itemView.findViewById(R.id.singerNameId);
            videoCount = itemView.findViewById(R.id.videoCountId);
            layout = itemView.findViewById(R.id.singleItemId);
            singerName.setTextColor(context.getResources().getColor(R.color.whiteColor));
            videoCount.setText("");
        }

        public void bind(int position){
//            Intent intent = new Intent(context,VideoExoPlayer.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("title",singerNameArrayList.get(position).toString());
//            intent.putExtras(bundle);
//            context.startActivity(intent);

            ExoPlayerFragment exoFrag = new ExoPlayerFragment(context,fm);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.exo_frag_layout_id,exoFrag);
            Bundle bundle = new Bundle();
            bundle.putString("title",singerNameArrayList.get(position).toString());
            exoFrag.setArguments(bundle);
            ft.commit();
        }
    }
}
