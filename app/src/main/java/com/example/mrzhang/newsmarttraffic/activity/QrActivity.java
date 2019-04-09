package com.example.mrzhang.newsmarttraffic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.utils.MyLog;
import com.example.mrzhang.newsmarttraffic.utils.ZXingUtils;

import java.util.Timer;
import java.util.TimerTask;

public class QrActivity extends AppCompatActivity {

    private ImageView title_left_iv;
    private TextView title_tv;
    private TextView title_right_tv;
    private TextView tv_msg;
    private ImageView iv_qr;
    private String money;
    private String time;
    private String carId;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    qrImage = ZXingUtils.createQRImage(buffer.toString(), 260, 260);
                    iv_qr.setImageBitmap(qrImage);
                    MyLog.showe("zzzz");
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private StringBuffer buffer;
    private View rootview;
    private Bitmap qrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        rootview = LayoutInflater.from(this).inflate(R.layout.activity_qr, null);
        initView();
        title_tv.setText("生成二维码");
        Intent intent = getIntent();
        if(intent.hasExtra("money")){
            money = intent.getStringExtra("money");
        }
        if(intent.hasExtra("time")){
            time = intent.getStringExtra("time");
        }
        if(intent.hasExtra("carId")){
            carId = intent.getStringExtra("carId");
        }
        buffer = new StringBuffer();
        buffer.append("车辆编号=");
        buffer.append(carId);
        buffer.append(",付费金额=");
        buffer.append(money);
        buffer.append("元");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    handler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(Integer.parseInt(time)*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                MyLog.showe("zz");
//                if(Looper.getMainLooper().getThread() == Thread.currentThread()) {
//                    Log.e("zz", "目前在主线程");
//                }else {
//                    Log.e("zz", "目前线程"+Thread.currentThread().toString());
//                }
//                handler.sendEmptyMessage(1);
//
//            }
//        }, 1000, Integer.parseInt(time) * 1000);

        iv_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zz","单机");
                View popview = LayoutInflater.from(QrActivity.this).inflate(R.layout.pop_qr_full, null);
                final PopupWindow popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                // 设置PopupWindow是否能响应外部点击事件
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);
                ImageView iv_pop = (ImageView)popview.findViewById(R.id.iv_pop);
                qrImage = ZXingUtils.createQRImage(buffer.toString(), 350, 350);
                iv_pop.setImageBitmap(qrImage);
                iv_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qrImage = ZXingUtils.createQRImage(buffer.toString(), 260, 260);
                        iv_qr.setImageBitmap(qrImage);
                        popupWindow.dismiss();
                    }
                });
            }
        });

        iv_qr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_msg.setText(buffer);
                return true;//返回false会同时调用调用setOnClickListener方法，true不会
            }
        });
    }

    private void initView() {
        title_left_iv = (ImageView) findViewById(R.id.title_left_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        title_right_tv = (TextView) findViewById(R.id.title_right_tv);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        iv_qr = (ImageView) findViewById(R.id.iv_qr);
    }
}
