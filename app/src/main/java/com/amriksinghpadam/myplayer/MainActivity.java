package com.amriksinghpadam.myplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amriksinghpadam.api.APIConstent;
import com.amriksinghpadam.api.NavigationItemRequest;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationBar;
    private DrawerLayout drawerLayout;
    private RelativeLayout progressBarLayout, refreshicon;
    private TextView appVersionTextView;
    int backFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));
        refreshicon = findViewById(R.id.refresh_layout_id);
        navigationBar = findViewById(R.id.mainNavBar);
        View v = navigationBar.getHeaderView(0);
        appVersionTextView = v.findViewById(R.id.app_version_id);
        drawerLayout = findViewById(R.id.drawerId);
        progressBarLayout = findViewById(R.id.progressBar_layout_id);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.opoen_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.statusbar));
        appVersionTextView.setText(BuildConfig.VERSION_NAME);
        refreshicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectivityandReloadActivity();
            }
        });
        checkConnectivityandReloadActivity();
    }

    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    public void checkConnectivityandReloadActivity() {
        APIConstent.CONNECTIVITY = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                APIConstent.CONNECTIVITY = true;
                refreshicon.setVisibility(View.GONE);
                new ContentTypeAPI().execute(APIConstent.SSL_SCHEME + APIConstent.BASE_URL + APIConstent.CONTENT_TYPE_URL_PARAM);
            }
        } else {
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            if (network != null) {
                APIConstent.CONNECTIVITY = true;
                refreshicon.setVisibility(View.GONE);
                new ContentTypeAPI().execute(APIConstent.SSL_SCHEME + APIConstent.BASE_URL + APIConstent.CONTENT_TYPE_URL_PARAM);
            }
        }
        if (APIConstent.CONNECTIVITY == false) {
            showToast(getResources().getString(R.string.internet_error_msg));
            refreshicon.setVisibility(View.VISIBLE);
        }
    }

    public void onGetContentTypeResponse(ArrayList<String> contentTypeList, ArrayList<String> contentTypeImgList) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MainFragment fragMain = new MainFragment(this);
        if (contentTypeList != null && contentTypeImgList != null &&
                contentTypeImgList.size() > 0 && contentTypeList.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putString(APIConstent.SONG_TITLE, contentTypeList.get(0));
            bundle.putString(APIConstent.VIDEO_TITLE, contentTypeList.get(1));
            bundle.putString(APIConstent.SONG_BANNER, contentTypeImgList.get(0));
            bundle.putString(APIConstent.VIDEO_BANNER, contentTypeImgList.get(1));
            fragMain.setArguments(bundle);
        }
        ft.add(R.id.floatingLayoutId, fragMain);
        ft.commit();

        navigationBar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, CommonPlayerGridView.class);
                Bundle bundle = new Bundle();
                NavigationItemRequest navRequest = new NavigationItemRequest(MainActivity.this,
                        progressBarLayout,refreshicon,intent,bundle,true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_feature_id:
                        navRequest.startNavItemActivity(getResources().getString(R.string.artist_title),
                                getResources().getString(R.string.song),APIConstent.ARTIST_URL_PARAM,
                                SharedPrefUtil.ARTIST_JSON_RESPONSE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_latest_id:
                        navRequest.startNavItemActivity(getResources().getString(R.string.latest_song),
                                getResources().getString(R.string.song),APIConstent.LATEST_URL_PARAM,
                                SharedPrefUtil.LATEST_JSON_RESPONSE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_discover_id:
                        navRequest.startNavItemActivity(getResources().getString(R.string.discover),
                                getResources().getString(R.string.song),APIConstent.DISCOVER_URL_PARAM,
                                SharedPrefUtil.DISCOVER_JSON_RESPONSE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_most_watch_id:
                        navRequest.startNavItemActivity(getResources().getString(R.string.most_watched),
                                getResources().getString(R.string.video),APIConstent.MOST_WATCHED_URL_PARAM,
                                SharedPrefUtil.MOST_WATCHED_JSON_RESPONSE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_new_video_id:
                        navRequest.startNavItemActivity(getResources().getString(R.string.new_video),
                                getResources().getString(R.string.video),APIConstent.NEW_ARIVAL_URL_PARAM,
                                SharedPrefUtil.NEW_ARIVAL_JSON_RESPONSE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_indian_video_id:
                        navRequest.startNavItemActivity(getResources().getString(R.string.hindi_and_punjabi),
                                getResources().getString(R.string.video),APIConstent.HINDI_PUNJABI_URL_PARAM,
                                SharedPrefUtil.HINDI_PUNJABI_JSON_RESPONSE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_englishi_video_id:
                        navRequest.startNavItemActivity(getResources().getString(R.string.english_video),
                                getResources().getString(R.string.video),APIConstent.ENGLISH_URL_PARAM,
                                SharedPrefUtil.ENGLISH_JSON_RESPONSE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    public class ContentTypeAPI extends AsyncTask<String, String, String> {
        private StringBuffer stringBuffer;
        ArrayList<String> contentTypeList, contentTypeImgList;

        public ContentTypeAPI() {
            contentTypeList = new ArrayList<>();
            contentTypeImgList = new ArrayList<>();
            stringBuffer = new StringBuffer();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... param) {
            String response="";
            if (param.length > 0 && param != null && !TextUtils.isEmpty(param[0]) && param[0] != null) {
               response = APIConstent.connectToServerWithURL(param[0]);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && !TextUtils.isEmpty(result)) {
                try {
                    JSONObject contentTypeObject = new JSONObject(result);
                    JSONArray contentTypeJsonArray = contentTypeObject.getJSONArray(APIConstent.CONTENT_TYPE_KEY);
                    for (int i = 0; i < contentTypeJsonArray.length(); i++) {
                        JSONObject object = contentTypeJsonArray.getJSONObject(i);
                        contentTypeList.add(object.getString(APIConstent.CONTENT_TYPE_KEY));
                        contentTypeImgList.add(object.getString(APIConstent.IMAGEURL));
                    }

                } catch (Exception e) {
                    showToast("Connection Interrupted.");
                    e.printStackTrace();
                }
                Log.d("contenttypelist", contentTypeList.get(0) + "\n" + contentTypeList.get(1));
            }
            onGetContentTypeResponse(contentTypeList, contentTypeImgList);
            progressBarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        backFlag = 0;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (backFlag == 1) {
                super.onBackPressed();
            } else {
                backFlag++;
                Toast.makeText(MainActivity.this, getString(R.string.press_back_again), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
