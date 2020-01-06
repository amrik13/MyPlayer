package com.amriksinghpadam.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APIConstent {
    public static boolean CONNECTIVITY;
    public static boolean IS_SHARED_PREF_SAVED;
    final public static String BASE_URL = "amriksinghpadam.com/MyplayerAPI/";
    final public static String SCHEME = "http://";
    final public static String SSL_SCHEME = "https://";
//content type params
    final public static String CONTENT_TYPE_URL_PARAM = "player_api/call_api_request.php?content_type_data=CONTENT+TYPE";
    final public static String CONTENT_TYPE_KEY = "contenttype";
    final public static String IMAGEURL = "imageurl";
    final public static String SONG_TITLE = "songtitle";
    final public static String VIDEO_TITLE = "videotitle";
    final public static String SONG_BANNER = "songbanner";
    final public static String VIDEO_BANNER = "videobanner";
//side navigation bar items params
    final public static String TITLE = "title";
    final public static String TYPE = "type";
    final public static String SONG = "song";
    final public static String VIDEO = "video";
    final public static String ARTIST_URL_PARAM = "player_api/call_api_request.php?artist_data=ARTIST";
    final public static String LATEST_URL_PARAM = "player_api/call_api_request.php?latest_song=LATEST+SONG";
    final public static String DISCOVER_URL_PARAM = "player_api/call_api_request.php?language_data=LANGUAGE";
    final public static String MOST_WATCHED_URL_PARAM = "player_api/call_api_request.php?most_played_video=MOST-PLAYED-VIDEO";
    final public static String NEW_ARIVAL_URL_PARAM = "player_api/call_api_request.php?latest_video=LATEST+VIDEO";
    final public static String HINDI_PUNJABI_URL_PARAM = "player_api/call_api_request.php?discover_video=4&discover_video_content=DICSCOVER+VIDEO";
    final public static String ENGLISH_URL_PARAM = "player_api/call_api_request.php?discover_video=6&discover_video_content=DICSCOVER+VIDEO";
// Song API url param
    final public static String TOP_IMAGE_URL_PARAM = "player_api/call_api_request.php?top_image_data=TOP+IMAGE";
    final public static String TOP_AUTO_CAROUSEL_BANNER_URL_PARAM = "player_api/call_api_request.php?all_carousel_data=CAROUSEL";
    final public static String TOP_IMAGE = "topimage";
    final public static String CAROUSEL = "carousel";

    //Media Pager View Selectiong Key
    final public static String SECTION = "section";

// API server Connection with string response
    public static String connectToServerWithURL(String uri){
        String inlineResponse = "";
        StringBuffer stringBuffer = new StringBuffer();
        HttpsURLConnection connection = null;
        if ( uri!= null && !TextUtils.isEmpty(uri)) {
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            try {
                URL contentTypeReqURL = new URL(uri);
                connection = (HttpsURLConnection) contentTypeReqURL.openConnection();
                if (connection != null) {
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(15000);
                    connection.connect();
                    inputStreamReader = new InputStreamReader(connection.getInputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);

                    while ((inlineResponse = bufferedReader.readLine()) != null) {
                        if (inlineResponse != null) stringBuffer.append(inlineResponse);
                    }
                    Log.d("Response", stringBuffer.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        inputStreamReader.close();
                        bufferedReader.close();
                    }
                } catch (Exception e) {
                    //Toast.makeText(context, "Connection Interrupted.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                if(stringBuffer!=null && !TextUtils.isEmpty(stringBuffer))
                    return stringBuffer.toString();
            }
        }
        return "";
    }

}
