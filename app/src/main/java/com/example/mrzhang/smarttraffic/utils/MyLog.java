package com.example.mrzhang.smarttraffic.utils;

import android.util.Log;

/**
 *
 */
public class MyLog {
    private static final String TGA = "smartTraffic";
    public static void showe(String msg){
        Log.e(TGA,msg);
    }
    public static void showi(String msg){
        Log.i(TGA,msg);
    }
}
