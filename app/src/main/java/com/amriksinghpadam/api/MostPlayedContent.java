package com.amriksinghpadam.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.amriksinghpadam.myplayer.R;

public class MostPlayedContent {
    private Context context;
    private String type,url;
    public MostPlayedContent(String type,Context context){
        this.type = type;
        this.context = context;
    }

    public void updateSongPlayedCounter(String contentId){
        APIConstant.CONNECTIVITY = false;
        if(type.equals(APIConstant.SONG)){
            String urlParam = APIConstant.MOST_PLAYED_SONG_COUNTER_UPDATE_URL_PARAM.replace("$$contentid$$",contentId);
            url = APIConstant.SSL_SCHEME+APIConstant.BASE_URL+urlParam;
        }
        if (type.equals(APIConstant.VIDEO)){
            String urlParam = APIConstant.MOST_PLAYED_VIDEO_COUNTER_UPDATE_URL_PARAM.replace("$$contentid$$",contentId);
            url = APIConstant.SSL_SCHEME+APIConstant.BASE_URL+urlParam;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                APIConstant.CONNECTIVITY = true;
                new UpdateCounterTask().execute(url);
            }
        } else {
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            if (network != null) {
                APIConstant.CONNECTIVITY = true;
                new UpdateCounterTask().execute(url);
            }
        }
    }

    class UpdateCounterTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... param) {
            if(param!=null && param[0] !=null && !TextUtils.isEmpty(param[0])) {
                APIConstant.connectToServerWithURL(param[0]);
            }
            return "";
        }
    }



}
