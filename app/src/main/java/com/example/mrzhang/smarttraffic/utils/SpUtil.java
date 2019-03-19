package com.example.mrzhang.smarttraffic.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public class SpUtil {
    public static final String spname = "setting";

    /**
     * 保存String类型的
     * @param mContext 上下文对象
     * @param key 键
     * @param s 值
     */
    public static void putS(Context mContext, String key, String s){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        sharedPreferences.edit().putString(key,s).commit();
    }

    public static void putB(Context mContext, String key, boolean b){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        sharedPreferences.edit().putBoolean(key,b).commit();
    }

    public static String getS(Context mContext, String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        return sharedPreferences.getString(key,"");
    }

    public static boolean getB(Context mContext, String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        return sharedPreferences.getBoolean(key,false);
    }
}
