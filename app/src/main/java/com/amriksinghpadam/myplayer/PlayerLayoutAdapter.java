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
    private ArrayList textList = new ArrayList();
    private ArrayList singleIdList = new ArrayList();
    private boolean isAllowToPlayer;
    private RelativeLayout progressBarLayout;
    private int selectionCode;

    PlayerLayoutAdapter(Context context, ArrayList imageList, ArrayList textList, ArrayList singleIdList, boolean isAllowToPlayer) {
        this.context = context;
        this.imageList.addAll(imageList);
        this.textList.addAll(textList);
        this.isAllowToPlayer = isAllowToPlayer;
        if(singleIdList!=null) this.singleIdList.addAll(singleIdList);
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
            textView.setText(textList.get(position).toString());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tempCount == 0) {
                        if (isAllowToPlayer) {
                            APIConstant.SELECTED_SONG_SECTION = APIConstant.LATEST_SONG_CODE;
                            Intent intent = new Intent(context, VideoExoPlayer.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(APIConstant.TITLE, textList.get(position).toString());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            String singleId ="";
                            if(singleIdList!=null && singleIdList.size()>0)
                                singleId = singleIdList.get(position).toString();
                            APIConstant.IS_FIRST_TIME_FROM_SIDENAV=false;
                            Intent intent = new Intent(context, CommonPlayerGridView.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(APIConstant.TITLE, textList.get(position).toString());
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

                        }

                    }
                }
            });
        }

    }

}
