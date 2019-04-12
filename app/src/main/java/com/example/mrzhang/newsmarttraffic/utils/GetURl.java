package com.example.mrzhang.newsmarttraffic.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GetURl {
    public static String getUrl(Context context){
        SharedPreferences setting = context.getSharedPreferences("setting", 0);
        String ip = setting.getString("ip", "");
        return ip;
    }
}
