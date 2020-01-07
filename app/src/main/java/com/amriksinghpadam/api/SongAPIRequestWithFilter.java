package com.amriksinghpadam.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amriksinghpadam.myplayer.MainFragment;
import com.amriksinghpadam.myplayer.PlayerLayoutAdapter;
import com.amriksinghpadam.myplayer.R;

public class SongAPIRequestWithFilter {

    private Context context;
    private Intent intent;
    private RelativeLayout progressBarLayout;
    private String sharedPrefKey;

    public SongAPIRequestWithFilter(Context context, Intent intent){
        this.context = context;
        this.intent = intent;
    }

    public void sendSongRequestWithFilter(RelativeLayout progressBarLayout,String filterSongReqURL, String sharedPrefKey){
        this.progressBarLayout = progressBarLayout;
        this.sharedPrefKey = sharedPrefKey;
        APIConstant.CONNECTIVITY = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                APIConstant.CONNECTIVITY = true;
                PlayerLayoutAdapter.tempCount=1;
                new MediaFilterAPIAsyncTask().execute(filterSongReqURL);
            }
        } else {
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            if (network != null) {
                PlayerLayoutAdapter.tempCount=1;
                APIConstant.CONNECTIVITY = true;
                new MediaFilterAPIAsyncTask().execute(filterSongReqURL);
            }
        }
        if (!APIConstant.CONNECTIVITY) {
            APIConstant.SELECTED_SONG_SECTION =0;
            PlayerLayoutAdapter.tempCount=0;
            showToast(context.getResources().getString(R.string.internet_error_msg));
        }
    }

    class MediaFilterAPIAsyncTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... param) {
            if (param != null && param[0] != null && !TextUtils.isEmpty(param[0])) {
                String filterSongJsonResponse = APIConstant.connectToServerWithURL(param[0]);
                return filterSongJsonResponse;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(response!=null && !TextUtils.isEmpty(response)){
                SharedPrefUtil.setFilterSongJsonResponse(context,response,sharedPrefKey);
                context.startActivity(intent);
                progressBarLayout.setVisibility(View.GONE);
            }else {
                PlayerLayoutAdapter.tempCount=0;
                APIConstant.SELECTED_SONG_SECTION =0;
                progressBarLayout.setVisibility(View.GONE);
                showToast(context.getResources().getString(R.string.empty_shared_pref_error_msg));
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
