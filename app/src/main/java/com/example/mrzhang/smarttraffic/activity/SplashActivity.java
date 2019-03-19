package com.example.mrzhang.smarttraffic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.utils.Constant;
import com.example.mrzhang.smarttraffic.utils.SpUtil;

public class SplashActivity extends BaseActivity {

    private TextView mTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        CountDownTimer countDownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeTv.setText(millisUntilFinished/1000 + "S");
            }

            @Override
            public void onFinish() {
                if(getSharedPreferences("setting", 0).contains(Constant.SP_ISFRAIST)){
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this,WelcomeActivity.class);
                    startActivity(intent);
                    SpUtil.putB(SplashActivity.this,Constant.SP_ISFRAIST,true);
                    finish();
                }

            }
        }.start();
    }

    private void initView() {
        mTimeTv = (TextView) findViewById(R.id.time_tv);
    }
}
