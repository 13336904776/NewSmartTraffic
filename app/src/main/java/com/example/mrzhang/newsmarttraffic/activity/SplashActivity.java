package com.example.mrzhang.newsmarttraffic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.utils.Constant;
import com.example.mrzhang.newsmarttraffic.utils.SpUtil;

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
                    if(SpUtil.getB(SplashActivity.this,Constant.SP_ISSELFLOGIN)) {
                        String userName = SpUtil.getS(SplashActivity.this, Constant.SP_USERNNME);
                        String pwd = SpUtil.getS(SplashActivity.this, Constant.SP_PASSWORD);
                        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)){
                            toLogin();
                        }else {
                            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        toLogin();
                    }
                }else {
                    Intent intent = new Intent(SplashActivity.this,WelcomeActivity.class);
                    startActivity(intent);
                    SpUtil.putB(SplashActivity.this,Constant.SP_ISFRAIST,true);
                    finish();
                }

            }
        }.start();
    }

    private void toLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mTimeTv = (TextView) findViewById(R.id.time_tv);
    }
}
