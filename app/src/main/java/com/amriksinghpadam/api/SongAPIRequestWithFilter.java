package com.amriksinghpadam.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.amriksinghpadam.myplayer.PlayerLayoutAdapter;
import com.amriksinghpadam.myplayer.R;

public class SongAPIRequestWithFilter {

    private Context context;
    private String filterId;

    public SongAPIRequestWithFilter(Context context,String filterId){
        this.context = context;
        this.filterId = filterId;
    }


    public void sendSongRequestWithFilter(){
        APIConstent.CONNECTIVITY = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                APIConstent.CONNECTIVITY = true;
                PlayerLayoutAdapter.tempCount=1;

            }
        } else {
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            if (network != null) {
                PlayerLayoutAdapter.tempCount=1;
                APIConstent.CONNECTIVITY = true;


            }
        }
        if (!APIConstent.CONNECTIVITY) {
            PlayerLayoutAdapter.tempCount=0;
            showToast(context.getResources().getString(R.string.internet_error_msg));

        }
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
