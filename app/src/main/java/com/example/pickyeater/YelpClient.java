package com.example.pickyeater;

import android.content.Context;

import androidx.annotation.Nullable;

import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.oauth.OAuthAsyncHttpClient;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.httpclient.HttpClient;

public class YelpClient extends OAuthBaseClient {

    public static final BaseApi REST_API_INSTANCE = YelpApi.instance(); // Change this
    public static final String REST_URL = "https://api.yelp.com/v3"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "Tpc4as6QiW4h0QLRd8zHvA";
    public static final String REST_CONSUMER_SECRET = "SBsaWidRTfoXuVxS04YZE74ExQNX_cmIgNZTz2CH01W_BonLGCy1B0QTCTbkI_6aqvOWk4rG1hEpn2fbPWTHK3JrzleQAkQxcjni5HH48VaGAsga3LZuDkJ4rrMMX3Yx";

    // Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
    public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

    // See https://developer.chrome.com/multidevice/android/intents
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public YelpClient(Context context) {
        super(context, REST_API_INSTANCE,
                REST_URL,
                REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET,
                "OAuth2",  // OAuth2 scope, null for OAuth1
                String.format(REST_CALLBACK_URL_TEMPLATE, "cprest",
                        "oauth", context.getPackageName(), FALLBACK_URL));
        ;
    }

    public void searchBusiness(JsonHttpResponseHandler handler, String location){
        String apiUrl = getApiUrl("/businesses/search");
        RequestParams params = new RequestParams();
        params.put("location", location);
        params.put("categories", "restaurant");
        client.get(apiUrl, params, handler);


    }


}
