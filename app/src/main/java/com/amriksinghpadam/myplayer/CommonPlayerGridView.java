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
import com.amriksinghpadam.api.APIConstent;
import com.amriksinghpadam.api.SharedPrefUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CommonPlayerGridView extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList bannerList = new ArrayList();
    private ArrayList tittleList = new ArrayList();
    private RecyclerView commonRecyclerView;
    private CommonGridPlayerRecyclerViewAdapter adapter;
    private RelativeLayout nodataImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_player_grid_view);
        toolbar = findViewById(R.id.gridplayerid);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString(APIConstent.TYPE);
        String pageTitle = bundle.getString(APIConstent.TITLE);
        //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(pageTitle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));
        commonRecyclerView = findViewById(R.id.common_grid_recycler_id);
        nodataImageLayout = findViewById(R.id.nodata_layout);
        nodataImageLayout.setVisibility(View.GONE);

        if(pageTitle.equals(getResources().getString(R.string.artist_title))){
            ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavArtistJsonResponse(getApplicationContext(),nodataImageLayout);
            for (int i=0;i<arrayList.size();i++){
                try {
                    JSONObject obj = arrayList.get(i);
                    bannerList.add(obj.getString(APIConstent.IMAGEURL));
                    tittleList.add(obj.getString("artistname"));
                } catch (JSONException e) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else if(pageTitle.equals(getResources().getString(R.string.discover))){
            ArrayList<JSONObject> arrayList = SharedPrefUtil.getSideNavDiscoverJsonResponse(getApplicationContext(),nodataImageLayout);
            for (int i=0;i<arrayList.size();i++){
                try {
                    JSONObject obj = arrayList.get(i);
                    bannerList.add(obj.getString(APIConstent.IMAGEURL));
                    tittleList.add(obj.getString("language").toUpperCase());
                } catch (JSONException e) {
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
                } catch (JSONException e) {
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
                } catch (JSONException e) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{

        }


        GridLayoutManager layoutManager = new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        commonRecyclerView.setLayoutManager(layoutManager);
        adapter = new CommonGridPlayerRecyclerViewAdapter(this,bannerList,tittleList);
        commonRecyclerView.setAdapter(adapter);

        switch (type){
            case APIConstent.SONG:
                Toast.makeText(this,"Song Type",Toast.LENGTH_SHORT).show();
            break;
            case APIConstent.VIDEO:
                Toast.makeText(this,"Video Type",Toast.LENGTH_SHORT).show();
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.tempCount=0;
    }
}
