package com.amriksinghpadam.myplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CommonPlayerGridView extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList bannerList = new ArrayList();
    private ArrayList tittleList = new ArrayList();
    private ArrayList singerNameList = new ArrayList();
    private ArrayList singleIdList = new ArrayList();
    private RecyclerView commonRecyclerView;
    private CommonGridPlayerRecyclerViewAdapter adapter;
    private RelativeLayout nodataImageLayout;
    private RelativeLayout progressBarLayout;
    private int selectionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_player_grid_view);
        toolbar = findViewById(R.id.gridplayerid);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString(APIConstant.TYPE);
        String pageTitle = bundle.getString(APIConstant.TITLE);
        //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(pageTitle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));
        commonRecyclerView = findViewById(R.id.common_grid_recycler_id);
        nodataImageLayout = findViewById(R.id.nodata_layout);
        nodataImageLayout.setVisibility(View.GONE);
        progressBarLayout = findViewById(R.id.progressBar_layout_id);
        progressBarLayout.setVisibility(View.GONE);

        switch (APIConstant.SELECTED_SONG_SECTION){
            case APIConstant.ARTIST_CODE:
                //APIConstant.IS_FIRST_TIME_FROM_SIDENAV = false;
                APIConstant.SELECTED_SONG_SECTION = 0;
                ArrayList<JSONObject> artistArrayList = SharedPrefUtil.getArtistFilterSongJsonResponse(getApplicationContext(),nodataImageLayout);
                for (int i=0;i<artistArrayList.size();i++){
                    try {
                        JSONObject obj = artistArrayList.get(i);
                        bannerList.add(obj.getString("songbannerurl"));
                        tittleList.add(obj.getString("songtitle"));
                        singerNameList.add(obj.getString("artistname"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case APIConstant.DISCOVER_SONG_CODE:
                //APIConstant.IS_FIRST_TIME_FROM_SIDENAV = false;
                APIConstant.SELECTED_SONG_SECTION = 0;
                ArrayList<JSONObject> discoverArrayList = SharedPrefUtil.getDiscoverFilterSongJsonResponse(getApplicationContext(),nodataImageLayout);
                for (int i=0;i<discoverArrayList.size();i++){
                    try {
                        JSONObject obj = discoverArrayList.get(i);
                        bannerList.add(obj.getString("songbannerurl"));
                        tittleList.add(obj.getString("songtitle"));
                        singerNameList.add(obj.getString("artistname"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                APIConstant.SELECTED_SONG_SECTION = 0;
                if(pageTitle.equals(getResources().getString(R.string.artist_title))){
                    selectionCode = APIConstant.ARTIST_CODE;
                    ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavArtistJsonResponse(getApplicationContext(),nodataImageLayout);
                    for (int i=0;i<arrayList.size();i++){
                        try {
                            JSONObject obj = arrayList.get(i);
                            bannerList.add(obj.getString(APIConstant.IMAGEURL));
                            tittleList.add(obj.getString("artistname"));
                            singleIdList.add(obj.getString("artistid"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else if(pageTitle.equals(getResources().getString(R.string.latest_song))){
                    ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavLatestJsonResponse(getApplicationContext(),nodataImageLayout);
                    for (int i=0;i<arrayList.size();i++){
                        try {
                            JSONObject obj = arrayList.get(i);
                            bannerList.add(obj.getString("songbannerurl"));
                            tittleList.add(obj.getString("songtitle"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else if(pageTitle.equals(getResources().getString(R.string.discover))){
                    selectionCode = APIConstant.DISCOVER_SONG_CODE;
                    ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavDiscoverJsonResponse(getApplicationContext(),nodataImageLayout);
                    for (int i=0;i<arrayList.size();i++){
                        try {
                            JSONObject obj = arrayList.get(i);
                            bannerList.add(obj.getString(APIConstant.IMAGEURL));
                            tittleList.add(obj.getString("language").toUpperCase());
                            singleIdList.add(obj.getString("languageid"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else if(pageTitle.equals(getResources().getString(R.string.new_video))){
                    ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavNewArivalJsonResponse(getApplicationContext(),nodataImageLayout);
                    for (int i=0;i<arrayList.size();i++){
                        try {
                            JSONObject obj = arrayList.get(i);
                            bannerList.add(obj.getString("videobannerurl"));
                            tittleList.add(obj.getString("videotitle"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else if(pageTitle.equals(getResources().getString(R.string.most_watched))){

                }else if(pageTitle.equals(getResources().getString(R.string.hindi_and_punjabi))){
                    ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavHindiPunjabiJsonResponse(getApplicationContext(),nodataImageLayout);
                    for (int i=0;i<arrayList.size();i++){
                        try {
                            JSONObject obj = arrayList.get(i);
                            bannerList.add(obj.getString("videobannerurl"));
                            tittleList.add(obj.getString("videotitle"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else if(pageTitle.equals(getResources().getString(R.string.english_video))){
                    ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavEnglishJsonResponse(getApplicationContext(),nodataImageLayout);
                    for (int i=0;i<arrayList.size();i++){
                        try {
                            JSONObject obj = arrayList.get(i);
                            bannerList.add(obj.getString("videobannerurl"));
                            tittleList.add(obj.getString("videotitle"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{

                }
            break;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        commonRecyclerView.setLayoutManager(layoutManager);
        adapter = new CommonGridPlayerRecyclerViewAdapter(this,bannerList,tittleList,singleIdList);
        adapter.setProgressBar(progressBarLayout,selectionCode);
        commonRecyclerView.setAdapter(adapter);

        switch (type){
            case APIConstant.SONG:
                Toast.makeText(this,"Song Type",Toast.LENGTH_SHORT).show();
            break;
            case APIConstant.VIDEO:
                Toast.makeText(this,"Video Type",Toast.LENGTH_SHORT).show();
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.tempCount=0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        APIConstant.IS_FIRST_TIME_FROM_SIDENAV = true;
    }
}
