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

import com.amriksinghpadam.api.APIConstant;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class RelatedViewAdapter extends RecyclerView.Adapter<RelatedViewAdapter.SingerListAdapterVH1> {

    private Context context;
    private ArrayList conentImgList = new ArrayList();
    private ArrayList contentNameList = new ArrayList();
    private ArrayList contentURLList = new ArrayList();
    private ArrayList descriptionList = new ArrayList();
    private View view;
    private FragmentManager fm;
    private String type;

    public RelatedViewAdapter(
            Context context,ArrayList conentImgList,
            ArrayList contentNameList,ArrayList descriptionList, ArrayList contentURLList,FragmentManager fm, String type) {
        this.fm = fm;
        this.type = type;
        this.context = context;
        this.conentImgList.addAll(conentImgList);
        this.contentNameList.addAll(contentNameList);
        this.contentURLList.addAll(contentURLList);
        this.descriptionList.addAll(descriptionList);
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

        Glide.with(context).load(conentImgList.get(position)).into(holder.contentImgView);
        //holder.singerImage.setImageDrawable((BitmapDrawable) singerImageArrayList.get(position));
        holder.contentNameView.setText(contentNameList.get(position).toString());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,singerNameArrayList.get(position)+"--"+videoCountArrayList.get(position),Toast.LENGTH_SHORT).show();
                holder.bind(position);
            }
        });
    }
    @Override
    public int getItemCount() { return conentImgList.size(); }

    class SingerListAdapterVH1 extends RecyclerView.ViewHolder {
        ImageView contentImgView;
        TextView contentNameView;
        RelativeLayout layout;

        public SingerListAdapterVH1(@NonNull View itemView) {
            super(itemView);
            contentImgView = itemView.findViewById(R.id.contentItemImageId);
            contentNameView = itemView.findViewById(R.id.contentNameId);
            layout = itemView.findViewById(R.id.singleItemId);
            contentNameView.setTextColor(context.getResources().getColor(R.color.whiteColor));
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
            bundle.putString(APIConstant.TITLE,contentNameList.get(position).toString());
            bundle.putString(APIConstant.TYPE,type);
            bundle.putString(APIConstant.URL,contentURLList.get(position).toString());
            bundle.putString(APIConstant.DESCRIPTION,descriptionList.get(position).toString());
            exoFrag.setArguments(bundle);
            ft.commit();
        }
    }
}
