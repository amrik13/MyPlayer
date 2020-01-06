package com.amriksinghpadam.api;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.amriksinghpadam.myplayer.R;

public class NavigationItemRequest {
    private Context mContext;
    private RelativeLayout progressBarLayout,refreshicon;
    private Intent intent;
    private Bundle bundle;
    private String pageTitle="";
    private String contentType="";
    private String sharedPrefKey ="";
    private boolean isFromSideNavbar;

    public NavigationItemRequest(Context mContext, RelativeLayout progressBarLayout, RelativeLayout refreshicon
            , Intent intent, Bundle bundle,boolean isFromSideNavbar){
        this.mContext = mContext;
        this.progressBarLayout = progressBarLayout;
        this.refreshicon = refreshicon;
        this.intent = intent;
        this.bundle = bundle;
        this.isFromSideNavbar = isFromSideNavbar;
    }

    public void startNavItemActivity(String pageTitle,String contentType,
                                     String urlParam, String sharedPrefKey){
        this.sharedPrefKey = sharedPrefKey;
        this.pageTitle = pageTitle;
        this.contentType = contentType;
        APIConstent.CONNECTIVITY = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                APIConstent.CONNECTIVITY = true;
                refreshicon.setVisibility(View.GONE);
                new NavigationItemAPICallTask().execute(
                        APIConstent.SSL_SCHEME+
                        APIConstent.BASE_URL+
                        urlParam
                );
            }
        } else {
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            if (network != null) {
                APIConstent.CONNECTIVITY = true;
                refreshicon.setVisibility(View.GONE);
                new NavigationItemAPICallTask().execute(
                        APIConstent.SSL_SCHEME+
                        APIConstent.BASE_URL+
                        APIConstent.ARTIST_URL_PARAM
                );
            }
        }
        if (!APIConstent.CONNECTIVITY) {
            showToast(mContext.getResources().getString(R.string.internet_error_msg));
            refreshicon.setVisibility(View.VISIBLE);
        }

    }

    class NavigationItemAPICallTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            APIConstent.IS_SHARED_PREF_SAVED = false;
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... param) {
            if (param[0]!=null && param!= null && !TextUtils.isEmpty(param[0])) {
                String response = APIConstent.connectToServerWithURL(param[0]);
                Log.d("SideNavResponse",response);
                return response;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(response!=null && !TextUtils.isEmpty(response)){
                SharedPrefUtil.setSideNavItemJsonResponse(mContext,response,sharedPrefKey);
                if(APIConstent.IS_SHARED_PREF_SAVED) {
                    if(isFromSideNavbar) {
                        bundle.putString(APIConstent.TITLE, pageTitle);
                        bundle.putString(APIConstent.TYPE, contentType);
                        intent.putExtras(bundle);
                        //showToast("saved and ready to go.");
                        mContext.startActivity(intent);
                    }
                }else{
                    showToast(mContext.getResources().getString(R.string.empty_shared_pref_error_msg));
                }
            }
            progressBarLayout.setVisibility(View.GONE);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
