package com.example.mrzhang.newsmarttraffic.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.Button;

/**
 *
 */
@SuppressLint("AppCompatCustomView")
public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
