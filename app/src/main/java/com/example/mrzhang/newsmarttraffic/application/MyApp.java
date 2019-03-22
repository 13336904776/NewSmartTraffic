package com.example.mrzhang.newsmarttraffic.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 *
 */
public class MyApp extends Application {
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);

    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
