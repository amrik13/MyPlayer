package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.amriksinghpadam.api.SongAPIRequestWithFilter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CommonGridPlayerRecyclerViewAdapter extends RecyclerView.Adapter<CommonGridPlayerRecyclerViewAdapter.GridPlayerViewHolder> {

    int tempCount = 0;
    private Context context;
    private ArrayList bannerList = new ArrayList();
    private ArrayList titleList = new ArrayList();
    private ArrayList singleIdList = new ArrayList();
    private ArrayList singerNameList = new ArrayList();
    private ArrayList descriptionList = new ArrayList();
    private ArrayList songURLList = new ArrayList();
    private int selectionCode;
    private RelativeLayout progressBarLayout;

    public CommonGridPlayerRecyclerViewAdapter(Context context, ArrayList bList, ArrayList tList,ArrayList singleIdList,
                                               ArrayList singerNameList,ArrayList descriptionList, ArrayList songURLList) {
        this.context = context;
        bannerList.addAll(bList);
        titleList.addAll(tList);
        this.singleIdList.addAll(singleIdList);
        this.singerNameList.addAll(singerNameList);
        this.descriptionList.addAll(descriptionList);
        this.songURLList.addAll(songURLList);
    }

    public void setProgressBar(RelativeLayout progressBarLayout, int selectionCode) {
        this.progressBarLayout = progressBarLayout;
        this.selectionCode = selectionCode;
    }

    @NonNull
    @Override
    public GridPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_grid_recycler_singe_item, parent, false);
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
                if (tempCount == 0) {
                    if(APIConstant.IS_FIRST_TIME_FROM_SIDENAV){
                        APIConstant.IS_FIRST_TIME_FROM_SIDENAV = false;
                        String singleId ="";
                        if(singleIdList!=null && singleIdList.size()>0)
                            singleId = singleIdList.get(position).toString();

                            Intent intent = new Intent(context, CommonPlayerGridView.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(APIConstant.TITLE, titleList.get(position).toString());
                            bundle.putString(APIConstant.TYPE, APIConstant.SONG);
                            intent.putExtras(bundle);
                            String filterSongReqURL = "";
                            String sharedPrefKey = "";
                            switch (selectionCode){
                                case APIConstant.ARTIST_CODE:
                                    APIConstant.SELECTED_SONG_SECTION = APIConstant.ARTIST_CODE;
                                    String artistParam = APIConstant.FILTER_ARTIST_SONG_URL_PARAM.replace("$$artist$id$$",singleId);
                                    filterSongReqURL = APIConstant.SSL_SCHEME+
                                            APIConstant.BASE_URL+artistParam;
                                    sharedPrefKey = SharedPrefUtil.SONG_BY_ARTIST_JSON_RESPONSE;
                                    break;
                                case APIConstant.DISCOVER_SONG_CODE:
                                    APIConstant.SELECTED_SONG_SECTION = APIConstant.DISCOVER_SONG_CODE;
                                    String discoverParam = APIConstant.FILTER_LANGUAGE_SONG_URL.replace("$$language$id$$",singleId);
                                    filterSongReqURL = APIConstant.SSL_SCHEME+
                                            APIConstant.BASE_URL+discoverParam;
                                    sharedPrefKey = SharedPrefUtil.SONG_BY_LANGUAGE_JSON_RESPONSE;
                                    break;
                                default: break;
                        }
                        SongAPIRequestWithFilter songRequest = new SongAPIRequestWithFilter(context,intent);
                        songRequest.sendSongRequestWithFilter(progressBarLayout,filterSongReqURL,sharedPrefKey);
                    }else {
                        APIConstant.IS_FIRST_TIME_FROM_SIDENAV = false;
                        Intent intent = new Intent(context, VideoExoPlayer.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(APIConstant.TITLE, titleList.size()>0 ? titleList.get(position).toString():"");
                        bundle.putString(APIConstant.SINGER_NAME, singerNameList.size()>0 ? singerNameList.get(position).toString():"");
                        bundle.putString(APIConstant.SONG_DESCRIPTION,descriptionList.size()>0 ? descriptionList.get(position).toString():"");
                        bundle.putString(APIConstant.SONG_URL,songURLList.size()>0 ? songURLList.get(position).toString():"");
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        tempCount++;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    class GridPlayerViewHolder extends RecyclerView.ViewHolder {
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
