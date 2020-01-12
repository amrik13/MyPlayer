package com.amriksinghpadam.api;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amriksinghpadam.myplayer.MainFragment;
import com.amriksinghpadam.myplayer.R;
import com.amriksinghpadam.myplayer.VideoList;
import com.amriksinghpadam.myplayer.VideoRecyclerViewAdapter;

public class VideoAPIRequestWithFilter {
    private Context context;
    private Intent intent;
    private RelativeLayout progressBarLayout;
    private String sharedPrefKey;
    VideoList v;

    public VideoAPIRequestWithFilter(Context context, Intent intent){
        this.context = context;
        this.intent = intent;
    }

    public void callVideoAPIRequest(VideoList v,RelativeLayout progressBarLayout,String filterVideoReqURL,String sharedPrefKey){
        if(v!=null) this.v = v;
        this.progressBarLayout = progressBarLayout;
        this.sharedPrefKey = sharedPrefKey;
        APIConstant.CONNECTIVITY = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                VideoRecyclerViewAdapter.tempCount = 1;
                APIConstant.CONNECTIVITY = true;
                new VideoAPIRequestTest().execute(filterVideoReqURL);
            }
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                VideoRecyclerViewAdapter.tempCount = 1;
                APIConstant.CONNECTIVITY = true;
                new VideoAPIRequestTest().execute(filterVideoReqURL);
            }
        }
        if (!APIConstant.CONNECTIVITY) {
            VideoRecyclerViewAdapter.tempCount=0;
            showToast(context.getResources().getString(R.string.internet_error_msg));
        }
    }

    class VideoAPIRequestTest extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
            APIConstant.IS_SHARED_PREF_SAVED = false;

        }

        @Override
        protected String doInBackground(String... param) {
            if (param != null && param[0] != null && !TextUtils.isEmpty(param[0])) {
                String artistVideoJsonResponse = APIConstant.connectToServerWithURL(param[0]);
                return artistVideoJsonResponse;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String videoResponse) {
            super.onPostExecute(videoResponse);
            if (videoResponse != null && !TextUtils.isEmpty(videoResponse)) {
                SharedPrefUtil.setArtistVideoJsonResponse(context, videoResponse,sharedPrefKey);
                if(v!=null) v.getVideoData();
                if(intent!=null) context.startActivity(intent);
            } else {
                VideoRecyclerViewAdapter.tempCount = 0;
                showToast(context.getResources().getString(R.string.empty_api_response));
            }
            progressBarLayout.setVisibility(View.GONE);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
