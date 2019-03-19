package com.example.mrzhang.smarttraffic.utils;

import android.content.Context;
import android.widget.Toast;

/**
 *
 */
public class MyToast {

    /**
     *
     * @param context
     * @param showWhat 要显示的内容
     */
    public static void show(Context context,String showWhat){
        Toast.makeText(context, showWhat, Toast.LENGTH_SHORT).show();
    }
}
