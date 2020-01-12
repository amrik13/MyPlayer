package com.amriksinghpadam.api;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SharedPrefUtil {
    final public static String MYPLAYER_SHARED_PREF = "myplayershredpref";
    final public static String ARTIST_JSON_RESPONSE = "artistjsonresponse";
    final public static String LATEST_JSON_RESPONSE = "latestjsonresponse";
    final public static String DISCOVER_JSON_RESPONSE = "discoverjsonresponse";
    final public static String MOST_WATCHED_JSON_RESPONSE = "mostwatchjsonresponse";
    final public static String NEW_ARIVAL_JSON_RESPONSE = "newarivaljsonresponse";
    final public static String HINDI_PUNJABI_JSON_RESPONSE = "hindipunjabijsonresponse";
    final public static String ENGLISH_JSON_RESPONSE = "englishjsonresponse";
    final public static String ARTIST = "artist";
    final public static String LATEST = "latestsong";
    final public static String DISCOVER = "language";
    final public static String NEWARIVAL = "latestvideo";
    final public static String TOP_IMAGE_JSONRESPONSE = "topimagejsonresponse";
    final public static String TOP_AUTO_CAROUSEL_JSON_RESPONSE = "topautocarouseljsonresponse";
    final public static String SONG_BY_ARTIST_JSON_RESPONSE =  "songbyartistjsonresponse";
    final public static String VIDEO_BY_ARTIST_JSON_RESPONSE =  "videobyartistjsonresponse";
    final public static String SONG_BY_LANGUAGE_JSON_RESPONSE =  "songbylanguagejsonresponse";
    final public static String RELATED_SONG_JSON_RESPONSE =  "relatedSongjsonresponse";
    final public static String RELATED_VIDEO_JSON_RESPONSE =  "relatedSongjsonresponse";

    public static void setSideNavItemJsonResponse(Context mContext, String responseJson,String sharedPrefKey){
        APIConstant.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstant.IS_SHARED_PREF_SAVED = true;
    }
    public static ArrayList<JSONObject> getSideNavArtistJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> artistResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String artistJsonResponse = sharedPref.getString(ARTIST_JSON_RESPONSE,"");
        if(artistJsonResponse!=null && !TextUtils.isEmpty(artistJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(artistJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(ARTIST);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        artistResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    if(nodataImageLayout!=null) {
                        nodataImageLayout.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                if(nodataImageLayout!=null) {
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
                e.printStackTrace();
            }
        }
        return artistResponseList;
    }
    public static ArrayList<JSONObject> getSideNavLatestJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> latestResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String latestJsonResponse = sharedPref.getString(LATEST_JSON_RESPONSE,"");
        if(latestJsonResponse!=null && !TextUtils.isEmpty(latestJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(latestJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(LATEST);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        latestResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return latestResponseList;
    }
    public static ArrayList<JSONObject> getSideNavDiscoverJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> discoverResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String discoverJsonResponse = sharedPref.getString(DISCOVER_JSON_RESPONSE,"");
        if(discoverJsonResponse!=null && !TextUtils.isEmpty(discoverJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(discoverJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(DISCOVER);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        discoverResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return discoverResponseList;
    }
    public static ArrayList<JSONObject> getSideNavNewArivalJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> newArivalResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String newArivalJsonResponse = sharedPref.getString(NEW_ARIVAL_JSON_RESPONSE,"");
        if(newArivalJsonResponse!=null && !TextUtils.isEmpty(newArivalJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(newArivalJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(NEWARIVAL);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        newArivalResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return newArivalResponseList;
    }
    public static ArrayList<JSONObject> getSideNavHindiPunjabiJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> discoverResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String discoverJsonResponse = sharedPref.getString(HINDI_PUNJABI_JSON_RESPONSE,"");
        if(discoverJsonResponse!=null && !TextUtils.isEmpty(discoverJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(discoverJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.VIDEO);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        discoverResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return discoverResponseList;
    }
    public static ArrayList<JSONObject> getSideNavEnglishJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> discoverResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String discoverJsonResponse = sharedPref.getString(ENGLISH_JSON_RESPONSE,"");
        if(discoverJsonResponse!=null && !TextUtils.isEmpty(discoverJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(discoverJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.VIDEO);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        discoverResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return discoverResponseList;
    }
//top image response pref
    public static void setTopImageJsonResponse(Context mContext, String responseJson, String sharedPrefKey){
        APIConstant.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstant.IS_SHARED_PREF_SAVED = true;
    }
    public static ArrayList<JSONObject> getTopImageJsonResponse(Context mContext){
        ArrayList<JSONObject> topImgResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String topimgJsonResponse = sharedPref.getString(TOP_IMAGE_JSONRESPONSE,"");
        if(topimgJsonResponse!=null && !TextUtils.isEmpty(topimgJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(topimgJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.TOP_IMAGE);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        topImgResponseList.add(jsonArray.getJSONObject(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return topImgResponseList;
    }
//top auto Carousel response pref
    public static void setTopAutoCarouselJsonResponse(Context mContext, String responseJson, String sharedPrefKey){
        APIConstant.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstant.IS_SHARED_PREF_SAVED = true;
    }
    public static ArrayList<JSONObject> getTopAutoCarouselJsonResponse(Context mContext){
        ArrayList<JSONObject> autoCarouselResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String autoCarouselJsonResponse = sharedPref.getString(TOP_AUTO_CAROUSEL_JSON_RESPONSE,"");
        if(autoCarouselJsonResponse!=null && !TextUtils.isEmpty(autoCarouselJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(autoCarouselJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.CAROUSEL);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        autoCarouselResponseList.add(jsonArray.getJSONObject(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return autoCarouselResponseList;
    }
//Filtered Song Artist response pref
    public static void setFilterSongJsonResponse(Context mContext, String responseJson, String sharedPrefKey){
        APIConstant.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstant.IS_SHARED_PREF_SAVED = true;
    }
    public static ArrayList<JSONObject> getArtistFilterSongJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> artistFilterSongResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String artistFilterSongJsonResponse = sharedPref.getString(SONG_BY_ARTIST_JSON_RESPONSE,"");
        if(artistFilterSongJsonResponse!=null && !TextUtils.isEmpty(artistFilterSongJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(artistFilterSongJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.ARTIST_SONG);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        artistFilterSongResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return artistFilterSongResponseList;
    }
    public static ArrayList<JSONObject> getDiscoverFilterSongJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> discoverFilterSongResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String discoverFilterSongJsonResponse = sharedPref.getString(SONG_BY_LANGUAGE_JSON_RESPONSE,"");
        if(discoverFilterSongJsonResponse!=null && !TextUtils.isEmpty(discoverFilterSongJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(discoverFilterSongJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.DISCOVER_SONG);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        discoverFilterSongResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return discoverFilterSongResponseList;
    }
// Get Related Song list for exoplayer view
    public static ArrayList<JSONObject> getRelatedArtistSongJsonResponse(Context mContext,RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> artistFilterSongResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String artistFilterSongJsonResponse = sharedPref.getString(RELATED_SONG_JSON_RESPONSE,"");
        if(artistFilterSongJsonResponse!=null && !TextUtils.isEmpty(artistFilterSongJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(artistFilterSongJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.ARTIST_SONG);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        artistFilterSongResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return artistFilterSongResponseList;
    }
//Artist Video Response Pref
    public static void setArtistVideoJsonResponse(Context mContext, String responseJson, String sharedPrefKey){
        APIConstant.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstant.IS_SHARED_PREF_SAVED = true;
    }
// Get Video list for Video Grid view
    public static ArrayList<JSONObject> getFilterArtistVideoJsonResponse(Context mContext, RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> artistFilterVideoResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String artistFilterVideoJsonResponse = sharedPref.getString(VIDEO_BY_ARTIST_JSON_RESPONSE,"");
        if(artistFilterVideoJsonResponse!=null && !TextUtils.isEmpty(artistFilterVideoJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(artistFilterVideoJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.ARTIST_VIDEO);
                if(jsonArray!=null && jsonArray.length()>0){
                    if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.GONE);
                    for(int i=0;i<jsonArray.length();i++){
                        artistFilterVideoResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return artistFilterVideoResponseList;
    }
// Get Video list for exoplayer related view
    public static ArrayList<JSONObject> getRelatedArtistVideoJsonResponse(Context mContext, RelativeLayout nodataImageLayout){
        ArrayList<JSONObject> artistFilterVideoResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String artistFilterVideoJsonResponse = sharedPref.getString(RELATED_VIDEO_JSON_RESPONSE,"");
        if(artistFilterVideoJsonResponse!=null && !TextUtils.isEmpty(artistFilterVideoJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(artistFilterVideoJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstant.ARTIST_VIDEO);
                if(jsonArray!=null && jsonArray.length()>0){
                    if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.GONE);
                    for(int i=0;i<jsonArray.length();i++){
                        artistFilterVideoResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                if(nodataImageLayout!=null) nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return artistFilterVideoResponseList;
    }


}
