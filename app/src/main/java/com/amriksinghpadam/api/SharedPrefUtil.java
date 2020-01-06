package com.amriksinghpadam.api;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
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

    public static void setSideNavItemJsonResponse(Context mContext, String responseJson,String sharedPrefKey){
        APIConstent.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstent.IS_SHARED_PREF_SAVED = true;
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
            } catch (JSONException e) {
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
            } catch (JSONException e) {
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
            } catch (JSONException e) {
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
            } catch (JSONException e) {
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
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstent.VIDEO);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        discoverResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
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
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstent.VIDEO);
                if(jsonArray!=null && jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        discoverResponseList.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    nodataImageLayout.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                nodataImageLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
        return discoverResponseList;
    }
//top image response pref
    public static void setTopImageJsonResponse(Context mContext, String responseJson, String sharedPrefKey){
        APIConstent.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstent.IS_SHARED_PREF_SAVED = true;
    }
    public static ArrayList<JSONObject> getTopImageJsonResponse(Context mContext){
        ArrayList<JSONObject> topImgResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String topimgJsonResponse = sharedPref.getString(TOP_IMAGE_JSONRESPONSE,"");
        if(topimgJsonResponse!=null && !TextUtils.isEmpty(topimgJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(topimgJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstent.TOP_IMAGE);
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
        APIConstent.IS_SHARED_PREF_SAVED = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPrefKey,responseJson);
        if(editor.commit())
            APIConstent.IS_SHARED_PREF_SAVED = true;
    }
    public static ArrayList<JSONObject> getTopAutoCarouselJsonResponse(Context mContext){
        ArrayList<JSONObject> autoCarouselResponseList = new ArrayList<>();
        SharedPreferences sharedPref = mContext.getSharedPreferences(MYPLAYER_SHARED_PREF,Context.MODE_PRIVATE);
        String autoCarouselJsonResponse = sharedPref.getString(TOP_AUTO_CAROUSEL_JSON_RESPONSE,"");
        if(autoCarouselJsonResponse!=null && !TextUtils.isEmpty(autoCarouselJsonResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(autoCarouselJsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray(APIConstent.CAROUSEL);
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

}
