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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.amriksinghpadam.api.SongAPIRequestWithFilter;
import com.amriksinghpadam.api.VideoAPIRequestWithFilter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.SingerListAdapterVH> {

    public static int tempCount = 0;
    private Context context;
    private ArrayList singerImageArrayList = new ArrayList();
    private ArrayList singerNameArrayList = new ArrayList();
    private ArrayList artistIdList = new ArrayList();
    private RelativeLayout progressBarLayout;
    private View view;

    public VideoRecyclerViewAdapter(Context context, ArrayList singerImageArrayList,
            ArrayList singerNameArrayList, ArrayList artistIdList, RelativeLayout progressBarLayout) {
        this.progressBarLayout = progressBarLayout;
        this.context = context;
        this.singerImageArrayList.addAll(singerImageArrayList);
        this.singerNameArrayList.addAll(singerNameArrayList);
        this.artistIdList.addAll(artistIdList);
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
        Glide.with(context).load(singerImageArrayList.get(position)).into(holder.singerImage);
        //holder.singerImage.setImageBitmap((Bitmap) singerImageArrayList.get(position));
        holder.singerName.setText(singerNameArrayList.get(position).toString());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            Intent intent = new Intent(context, VideoList.class);
            if (tempCount == 0) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(APIConstant.ARTIST_ARRAY_LIST, singerNameArrayList != null ? singerNameArrayList : null);
                bundle.putStringArrayList(APIConstant.ARTIST_ID_LIST, artistIdList != null ? artistIdList : null);
                bundle.putString(APIConstant.SINGER_NAME,singerNameArrayList.size()>0?singerNameArrayList.get(position).toString():"");
                bundle.putString(APIConstant.ARTIST_ID,artistIdList.size()>0?artistIdList.get(position).toString():"");
                intent.putExtras(bundle);
                String singleId ="";
                if(artistIdList!=null && artistIdList.size()>0)
                    singleId = artistIdList.get(position).toString();
                String artistParam = APIConstant.FILTER_ARTIST_VIDEO_URL_PARAM.replace("$$artist$id$$",singleId);
                String filterVideoReqURL = APIConstant.SSL_SCHEME+APIConstant.BASE_URL+artistParam;
                String sharedPrefKey = SharedPrefUtil.VIDEO_BY_ARTIST_JSON_RESPONSE;
                VideoAPIRequestWithFilter videoRequest = new VideoAPIRequestWithFilter(context,intent);
                videoRequest.callVideoAPIRequest(null,progressBarLayout,filterVideoReqURL,sharedPrefKey);

            }

        }

    }

}

