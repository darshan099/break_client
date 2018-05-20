package com.example.darshan.mybreakclient;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


//json handling class
public class Controller {
    public static final String TAG="TAG";
    public static final String WAURL1="https://script.google.com/macros/s/AKfycbz5hbEF2hApqJYyPtEAuqzhburBuYe7YgYPkBcHALQK0o_BHC0t/exec?";
    public static final String WAURL2=" https://script.google.com/macros/s/AKfycbwvBw-tWG4BybGn8Jib6uhUFkDN_Xs78ibB49upeyajOJ85qzg/exec?";
    private static Response response;
    public static JSONObject readData(String id)
    {
        try
        {
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(WAURL1+"id="+id)
                    .build();
            response=client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }
        catch (@NonNull IOException | JSONException e)
        {
            Log.e(TAG,e.getLocalizedMessage());
        }
        return null;
    }
    public static JSONObject updateData(String id)
    {
        try
        {
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(WAURL2+"id="+id)
                    .build();
            response=client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }
        catch (@NonNull IOException | JSONException e)
        {
            Log.e(TAG,e.getLocalizedMessage());
        }
        return null;
    }

} 