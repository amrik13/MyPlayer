package com.amriksinghpadam.api;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.amriksinghpadam.myplayer.MainFragment;
import com.amriksinghpadam.myplayer.R;

public class SongAPIRequest {
    private Context mContext;
    private Intent intent;
    private Bundle bundle;
    private int section;
    private RelativeLayout progressBarLayout, refreshIconLayout;

    public SongAPIRequest(Context mContext, Intent intent, Bundle bundle, RelativeLayout progressBarLayout, RelativeLayout refreshIconLayout) {
        this.mContext = mContext;
        this.bundle = bundle;
        this.intent = intent;
        this.progressBarLayout = progressBarLayout;
        this.refreshIconLayout = refreshIconLayout;

    }

    public void callMediaAPIRequest(int section, String topImageAPIURl) {
        this.section = section;
        APIConstant.CONNECTIVITY = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                MainFragment.tempCount = 1;
                APIConstant.CONNECTIVITY = true;
                refreshIconLayout.setVisibility(View.GONE);
                new MediaAPIAsyncTask().execute(topImageAPIURl);

                NavigationItemRequest navRequest1 = new NavigationItemRequest(mContext,
                        progressBarLayout, refreshIconLayout, null, null, false);
                navRequest1.startNavItemActivity(mContext.getResources().getString(R.string.artist_title),
                        mContext.getResources().getString(R.string.song), APIConstant.ARTIST_URL_PARAM,
                        SharedPrefUtil.ARTIST_JSON_RESPONSE);
                NavigationItemRequest navRequest2 = new NavigationItemRequest(mContext,
                        progressBarLayout, refreshIconLayout, null, null, false);
                navRequest2.startNavItemActivity(mContext.getResources().getString(R.string.latest_song),
                        mContext.getResources().getString(R.string.song), APIConstant.LATEST_URL_PARAM,
                        SharedPrefUtil.LATEST_JSON_RESPONSE);
                NavigationItemRequest navRequest3 = new NavigationItemRequest(mContext,
                        progressBarLayout, refreshIconLayout, null, null, false);
                navRequest3.startNavItemActivity(mContext.getResources().getString(R.string.discover),
                        mContext.getResources().getString(R.string.song), APIConstant.DISCOVER_URL_PARAM,
                        SharedPrefUtil.DISCOVER_JSON_RESPONSE);

            }
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                MainFragment.tempCount = 1;
                APIConstant.CONNECTIVITY = true;
                refreshIconLayout.setVisibility(View.GONE);

            }
        }
        if (!APIConstant.CONNECTIVITY) {
            MainFragment.tempCount = 0;
            refreshIconLayout.setVisibility(View.VISIBLE);
            showToast(mContext.getResources().getString(R.string.internet_error_msg));
        }
    }

    // TOP Image Block API Request
    class MediaAPIAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... param) {
            if (param != null && param[0] != null && !TextUtils.isEmpty(param[0])) {
                String topImgJsonResponse = APIConstant.connectToServerWithURL(param[0]);
                return topImgJsonResponse;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String topImgResponse) {
            if (topImgResponse != null && !TextUtils.isEmpty(topImgResponse)) {
                super.onPostExecute(topImgResponse);
                SharedPrefUtil.setTopImageJsonResponse(mContext, topImgResponse, SharedPrefUtil.TOP_IMAGE_JSONRESPONSE);
//                bundle.putInt(APIConstant.SECTION, section);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//                progressBarLayout.setVisibility(View.GONE);
                new TopCarouselAPIAsyncTask().execute(APIConstant.SSL_SCHEME + APIConstant.BASE_URL + APIConstant.TOP_AUTO_CAROUSEL_BANNER_URL_PARAM);
            } else {
                MainFragment.tempCount = 0;
                progressBarLayout.setVisibility(View.GONE);
                showToast(mContext.getResources().getString(R.string.empty_shared_pref_error_msg));
            }
        }
    }

    // Top Auto Carousel API Request
    class TopCarouselAPIAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... param) {
            if (param != null && param[0] != null && !TextUtils.isEmpty(param[0])) {
                String autoCarouselBannerJsonResponse = APIConstant.connectToServerWithURL(param[0]);
                return autoCarouselBannerJsonResponse;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String autoCarouselBannerResponse) {
            if (autoCarouselBannerResponse != null && !TextUtils.isEmpty(autoCarouselBannerResponse)) {
                super.onPostExecute(autoCarouselBannerResponse);
                SharedPrefUtil.setTopAutoCarouselJsonResponse(mContext, autoCarouselBannerResponse, SharedPrefUtil.TOP_AUTO_CAROUSEL_JSON_RESPONSE);
                bundle.putInt(APIConstant.SECTION, section);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                progressBarLayout.setVisibility(View.GONE);
            } else {
                MainFragment.tempCount = 0;
                progressBarLayout.setVisibility(View.GONE);
                showToast(mContext.getResources().getString(R.string.empty_shared_pref_error_msg));
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

}
