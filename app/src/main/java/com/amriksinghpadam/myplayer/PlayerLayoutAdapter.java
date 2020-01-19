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
import androidx.recyclerview.widget.RecyclerView;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.amriksinghpadam.api.SongAPIRequestWithFilter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlayerLayoutAdapter extends RecyclerView.Adapter<PlayerLayoutAdapter.PlayerViewHolder> {

    public static int tempCount = 0;
    private Context context;
    private ArrayList imageList = new ArrayList();
    private ArrayList titleList = new ArrayList();
    private ArrayList singleIdList = new ArrayList();
    private ArrayList singerNameList = new ArrayList();
    private ArrayList descriptionList = new ArrayList();
    private ArrayList songURLList = new ArrayList();
    private ArrayList artistIdList = new ArrayList();
    private boolean isAllowToPlayer;
    private RelativeLayout progressBarLayout;
    private int selectionCode;
    private ArrayList contentIdList = new ArrayList();
    private String type;

    PlayerLayoutAdapter(String type,Context context, ArrayList imageList, ArrayList titleList, ArrayList singleIdList,
                        ArrayList artistIdList,ArrayList contentIdList,boolean isAllowToPlayer, ArrayList singerNameList, ArrayList descriptionList, ArrayList songURLList) {
        this.context = context;
        this.type = type;
        this.imageList.addAll(imageList);
        this.titleList.addAll(titleList);
        this.isAllowToPlayer = isAllowToPlayer;
        if(singleIdList!=null) this.singleIdList.addAll(singleIdList);
        if(singerNameList!=null) this.singerNameList.addAll(singerNameList);
        if(descriptionList!=null) this.descriptionList.addAll(descriptionList);
        if(songURLList!=null) this.songURLList.addAll(songURLList);
        if(artistIdList!=null) this.artistIdList.addAll(artistIdList);
        if(contentIdList.size()>0) this.contentIdList.addAll(contentIdList);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item_list, parent, false);
        PlayerViewHolder holder = new PlayerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setProgressBar(RelativeLayout progressBarLayout, int selectionCode) {
        this.progressBarLayout = progressBarLayout;
        this.selectionCode = selectionCode;
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageRecyclerViewId);
            textView = itemView.findViewById(R.id.textRecyclerViewId);
        }

        public void bindView(final int position) {
            //Glide.with(context).asBitmap().load(imageList.get(position)).load(imageView);
            Glide.with(context).load(imageList.get(position)).into(imageView);
            textView.setText(titleList.get(position).toString());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tempCount == 0) {
                        if (isAllowToPlayer) {
                            APIConstant.SELECTED_SONG_SECTION = APIConstant.LATEST_SONG_CODE;
                            Intent intent = new Intent(context, VideoExoPlayer.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(APIConstant.TYPE, type);
                            bundle.putString(APIConstant.CONTENT_ID, contentIdList.size()>0 ? contentIdList.get(position).toString():"");
                            bundle.putString(APIConstant.ARTIST_ID,
                                    artistIdList.size()>0 ? artistIdList.get(position).toString():
                                            singleIdList.size()>0 ? singleIdList.get(position).toString():"");
                            bundle.putString(APIConstant.TITLE, titleList.size()>0 ? titleList.get(position).toString():"");
                            bundle.putString(APIConstant.SINGER_NAME, singerNameList.size()>0 ? singerNameList.get(position).toString():"");
                            bundle.putString(APIConstant.SONG_DESCRIPTION,descriptionList.size()>0 ? descriptionList.get(position).toString():"");
                            bundle.putString(APIConstant.SONG_URL,songURLList.size()>0 ? songURLList.get(position).toString():"");
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            String singleId ="";
                            if(singleIdList!=null && singleIdList.size()>0)
                                singleId = singleIdList.get(position).toString();
                            APIConstant.IS_FIRST_TIME_FROM_SIDENAV=false;
                            Intent intent = new Intent(context, CommonPlayerGridView.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(APIConstant.TITLE, titleList.get(position).toString());
                            bundle.putString(APIConstant.TYPE, type);
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

                        }

                    }
                }
            });
        }

    }

}
