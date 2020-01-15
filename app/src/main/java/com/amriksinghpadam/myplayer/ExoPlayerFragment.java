package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amriksinghpadam.api.APIConstant;
import com.amriksinghpadam.api.SharedPrefUtil;
import com.amriksinghpadam.api.SongAPIRequestWithFilter;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class ExoPlayerFragment extends Fragment {
    private Context context;
    private PlayerView exoplayerView;
    private SimpleExoPlayer player;
    private ImageView shareBtnImg;
    private ProgressBar progressBar;
    private TextView videoTitle,metaDescription,singerNameTextView,relatedContentTextView,typeTextView;
    private String vTitle,singerName,description,contentURL,type,artistId;
    private RecyclerView relatedContentRecyclerView;
    private ArrayList imageArrayList = new ArrayList();
    private ArrayList contentnameArrayList = new ArrayList();
    private ArrayList contentURLList = new ArrayList();
    private ArrayList descriptionList = new ArrayList();
    private FragmentManager fm;

    public ExoPlayerFragment(Context context,FragmentManager fm) {
        this.context = context;
        this.fm = fm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exo_player, container, false);

        exoplayerView = view.findViewById(R.id.exo_player_id);
        shareBtnImg = view.findViewById(R.id.exoShareBtn);
        videoTitle = view.findViewById(R.id.exoVideoTitle);
        metaDescription = view.findViewById(R.id.metadataDescription);
        singerNameTextView = view.findViewById(R.id.metadataSingerNameId);
        relatedContentRecyclerView = view.findViewById(R.id.related_content_recyclerview_id);
        relatedContentTextView = view.findViewById(R.id.related_content_text_id);
        typeTextView = view.findViewById(R.id.type_textview_id);
        progressBar = view.findViewById(R.id.progressBar_id);
        progressBar.setVisibility(View.GONE);
        initializePlayer();
        initRelatedRecyclerView();
        shareBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "https://www.amriksinghpadam.com/MyPlayer";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Your Video");
                intent.putExtra(Intent.EXTRA_TEXT,"Hello, I am watching \""+vTitle+"\" by \""+singerName+"\" on " +
                        "Myplayer App and " +"sharing to you. Please click on link below to " +
                        "watch on Myplayer app..\n"+uri);
                Intent.createChooser(intent,"Share To");
                startActivity(intent);
//                ShareCompat.IntentBuilder.from(VideoExoPlayer.this)
//                        .setType("text/plain")
//                        .setChooserTitle("Share To")
//                        .setText(uri)
//                        .startChooser();
            }
        });
        return view;
    }

    public void initializePlayer(){
        vTitle = getActivity().getIntent().getExtras().getString(APIConstant.TITLE);
        type = getActivity().getIntent().getExtras().getString(APIConstant.TYPE);
        artistId = getActivity().getIntent().getExtras().getString(APIConstant.ARTIST_ID);
        singerName = getActivity().getIntent().getExtras().getString(APIConstant.SINGER_NAME);
        relatedContentTextView.setText("Related " +type.substring(0, 1).toUpperCase()+type.substring(1)
                +"s By "+singerName.substring(0, 1).toUpperCase()+singerName.substring(1)+":");
        typeTextView.setText("Type: "+type.toUpperCase());
        if(type.equals(APIConstant.SONG)) {
            description = getActivity().getIntent().getExtras().getString(APIConstant.SONG_DESCRIPTION);
            contentURL = getActivity().getIntent().getExtras().getString(APIConstant.SONG_URL);
            if(getArguments()!=null){
                vTitle = getArguments().getString(APIConstant.TITLE);
                contentURL = getArguments().getString(APIConstant.URL);
                description = getArguments().getString(APIConstant.DESCRIPTION);
            }
            videoTitle.setText(TextUtils.isEmpty(vTitle) || vTitle == null ? "Loading..." : vTitle);
            metaDescription.setText(TextUtils.isEmpty(description) || description == null ? "Description: " + "Loading..." : "Description: " + description);
            singerNameTextView.setText(TextUtils.isEmpty(singerName) || singerName == null ? "Singer: " + "Loading..." : "Singer: " + singerName);

//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//            ExtractorsFactory factory = new DefaultExtractorsFactory();
//            TrackSelection.Factory trackSelection = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//            String userAgent = Util.getUserAgent(context,context.getPackageName());
//            DataSource.Factory dataSource = new DefaultDataSourceFactory(context,userAgent);
//            Uri uri = Uri.parse(TextUtils.isEmpty(contentURL) ? "www.musicper.com/tere-bin.mp" : contentURL);
//            MediaSource mediaSource = new ExtractorMediaSource(uri,dataSource,factory,null,null);
//            player = ExoPlayerFactory.newSimpleInstance(context,new DefaultTrackSelector(trackSelection));
//            player.prepare(mediaSource);
//            player.setPlayWhenReady(true);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            final ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DataSource.Factory dateSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getPackageName()), (TransferListener<? super DataSource>) bandwidthMeter);
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(
                    TextUtils.isEmpty(contentURL)?" ":contentURL
            ), dateSourceFactory, extractorsFactory,null,null);
            Log.d("SongURL",contentURL);
            player = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector(trackSelectionFactory));
            exoplayerView.setPlayer(player);
            player.prepare(mediaSource);

        }
        if (type.equals(APIConstant.VIDEO)) {
            description = getActivity().getIntent().getExtras().getString(APIConstant.VIDEO_DESCRIPTION);
            contentURL = getActivity().getIntent().getExtras().getString(APIConstant.VIDEO_URL);
            if(getArguments()!=null){
                vTitle = getArguments().getString(APIConstant.TITLE);
                contentURL = getArguments().getString(APIConstant.URL);
                description = getArguments().getString(APIConstant.DESCRIPTION);
            }
            videoTitle.setText(TextUtils.isEmpty(vTitle) || vTitle == null ? "Loading..." : vTitle);
            metaDescription.setText(TextUtils.isEmpty(description) || description == null ? "Description: " + "Loading..." : "Description: " + description);
            singerNameTextView.setText(TextUtils.isEmpty(singerName) || singerName == null ? "Singer: " + "Loading..." : "Singer: " + singerName);

            BandwidthMeter meter =new DefaultBandwidthMeter();
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(meter);
            TrackSelector trackSelector = new DefaultTrackSelector(factory);
            player = ExoPlayerFactory.newSimpleInstance(context,trackSelector);
            //PlaybackControlView controlView = new PlaybackControlView(this);
            DataSource.Factory datasourcefactory = new DefaultDataSourceFactory(
                    context, Util.getUserAgent(context,"CloudinaryExoplaye"));
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            Uri videoURL = Uri.parse(TextUtils.isEmpty(contentURL) || contentURL==null ? " " : contentURL);
            Log.d("videoURL",contentURL);
            MediaSource mediaSource = new ExtractorMediaSource(
                    videoURL, datasourcefactory, extractorsFactory, null,null);
            exoplayerView.setPlayer(player);
            player.prepare(mediaSource);

        }
        player.setPlayWhenReady(true);

    }

    public void initRelatedRecyclerView(){
        APIConstant.CONNECTIVITY = false;
        String sharedPrefKey = "";
        if(type.equals(APIConstant.SONG)) {
            sharedPrefKey = SharedPrefUtil.RELATED_SONG_JSON_RESPONSE;
        }
        if(type.equals(APIConstant.VIDEO)) {
            sharedPrefKey = SharedPrefUtil.RELATED_VIDEO_JSON_RESPONSE;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                APIConstant.CONNECTIVITY = true;
                String artistParam = "";
                if(type.equals(APIConstant.SONG)) {
                    artistParam = APIConstant.FILTER_ARTIST_SONG_URL_PARAM.replace("$$artist$id$$",artistId);
                }
                if(type.equals(APIConstant.VIDEO)) {
                    artistParam = APIConstant.FILTER_ARTIST_VIDEO_URL_PARAM.replace("$$artist$id$$",artistId);
                }
                new CallRelatedContentAPI(sharedPrefKey).execute(APIConstant.SSL_SCHEME+APIConstant.BASE_URL+
                        artistParam);
            }
        } else {
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            if (network != null) {
                APIConstant.CONNECTIVITY = true;
                String artistParam = "";
                if(type.equals(APIConstant.SONG)) {
                    artistParam = APIConstant.FILTER_ARTIST_SONG_URL_PARAM.replace("$$artist$id$$",artistId);
                }
                if(type.equals(APIConstant.VIDEO)) {
                    artistParam = APIConstant.FILTER_ARTIST_VIDEO_URL_PARAM.replace("$$artist$id$$",artistId);
                }
                new CallRelatedContentAPI(sharedPrefKey).execute(APIConstant.SSL_SCHEME+APIConstant.BASE_URL+
                        artistParam);
            }
        }
        if (!APIConstant.CONNECTIVITY) {
            showToast(context.getResources().getString(R.string.internet_error_msg));
        }

    }

    public void bindRelatedContentView(){
        if(type.equals(APIConstant.SONG)) {
            ArrayList<JSONObject> artistArrayList = SharedPrefUtil.getRelatedArtistSongJsonResponse(context, null);
            for (int i = 0; i < artistArrayList.size(); i++) {
                try {
                    JSONObject obj = artistArrayList.get(i);
                    imageArrayList.add(obj.getString("songbannerurl"));
                    contentnameArrayList.add(obj.getString("songtitle"));
                    contentURLList.add(obj.getString("songurl"));
                    descriptionList.add(obj.getString("songdescription"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(type.equals(APIConstant.VIDEO)) {
            ArrayList<JSONObject> artistArrayList = SharedPrefUtil.getRelatedArtistVideoJsonResponse(context, null);
            for (int i = 0; i < artistArrayList.size(); i++) {
                try {
                    JSONObject obj = artistArrayList.get(i);
                    imageArrayList.add(obj.getString("videobannerurl"));
                    contentnameArrayList.add(obj.getString("videotitle"));
                    contentURLList.add(obj.getString("videourl"));
                    descriptionList.add(obj.getString("videodescription"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        relatedContentRecyclerView.setLayoutManager(layoutManager);
        RelatedViewAdapter videoRecyclerViewAdapter = new RelatedViewAdapter(
                context, imageArrayList, contentnameArrayList,descriptionList,contentURLList,fm,type);
        relatedContentRecyclerView.setAdapter(videoRecyclerViewAdapter);

    }

    class CallRelatedContentAPI extends AsyncTask<String,String,String>{
        private String sharedPrefKey;
        CallRelatedContentAPI(String sharedPrefKey){
            this.sharedPrefKey = sharedPrefKey;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
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
                if(type.equals(APIConstant.SONG)) {
                    SharedPrefUtil.setFilterSongJsonResponse(context,response,sharedPrefKey);
                }
                if(type.equals(APIConstant.VIDEO)) {
                    SharedPrefUtil.setArtistVideoJsonResponse(context,response,sharedPrefKey);
                }
                bindRelatedContentView();
                progressBar.setVisibility(View.GONE);
            }else {
                progressBar.setVisibility(View.GONE);
                showToast(context.getResources().getString(R.string.empty_shared_pref_error_msg));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(type.equals(APIConstant.VIDEO)) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
