package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amriksinghpadam.api.APIConstant;
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
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class ExoPlayerFragment extends Fragment {
    private Context context;
    private PlayerView exoplayerView;
    private SimpleExoPlayer player;
    private ImageView shareBtnImg;
    private TextView videoTitle,metaDescription,singerNameTextView;
    private String vTitle,singerName,description,contentURL;
    private RecyclerView relatedContentRecyclerView;
    private ArrayList imageArrayList = new ArrayList();
    private ArrayList nameArrayList = new ArrayList();
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

        initializePlayer();
        initRelatedRecyclerView();
        shareBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "https://www.amriksinghpadam.com/main";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Your Video");
                intent.putExtra(Intent.EXTRA_TEXT,"Hello, I am watching \""+vTitle+"\" Show on " +
                        "Myplayer App and " +"sharing to you. Please click on link below to " +
                        "enjoy the episode on Myplayer app..\n"+uri);
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
        description = getActivity().getIntent().getExtras().getString(APIConstant.SONG_DESCRIPTION);
        singerName = getActivity().getIntent().getExtras().getString(APIConstant.SINGER_NAME);
        contentURL = getActivity().getIntent().getExtras().getString(APIConstant.SONG_URL);

        videoTitle.setText(TextUtils.isEmpty(vTitle)||vTitle==null?"Loading...":vTitle);
        metaDescription.setText(TextUtils.isEmpty(description)||description==null?"Description: "+"Loading...":"Description: "+description);
        singerNameTextView.setText(TextUtils.isEmpty(singerName)||singerName==null?"Singer: "+"Loading...":"Singer: "+singerName);
        BandwidthMeter meter =new DefaultBandwidthMeter();
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(meter);
        TrackSelector trackSelector = new DefaultTrackSelector(factory);
        player = ExoPlayerFactory.newSimpleInstance(context,trackSelector);
        exoplayerView.setPlayer(player);
        //PlaybackControlView controlView = new PlaybackControlView(this);
        DataSource.Factory datasourcefactory = new DefaultDataSourceFactory(
                context, Util.getUserAgent(context,"CloudinaryExoplaye"));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Uri videoURL = Uri.parse(TextUtils.isEmpty(contentURL)||contentURL==null?"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
                                    : contentURL);
        MediaSource mediaSource = new ExtractorMediaSource(
                videoURL, datasourcefactory, extractorsFactory, null,null);
        player.prepare(mediaSource);
        player.setPlayWhenReady(false);
    }

    public void initRelatedRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        relatedContentRecyclerView.setLayoutManager(layoutManager);
        for (int i = 0; i < 8; i++) {
            imageArrayList.add(getResources().getDrawable(R.drawable.image));
            nameArrayList.add("Singer Name " + (i + 1));
        }
        RelatedViewAdapter videoRecyclerViewAdapter = new RelatedViewAdapter(
                context, imageArrayList, nameArrayList,fm);
        relatedContentRecyclerView.setAdapter(videoRecyclerViewAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.stop();
    }
}
