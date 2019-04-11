package com.example.mrzhang.newsmarttraffic.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mrzhang.newsmarttraffic.R;

public class ScaleImgActivity extends BaseActivity {

    private ImageView iv;
    private RelativeLayout rllayout;
    private ScaleGestureDetector scaleGestureDetector;

    private Matrix matrix;
    private float preScale;//之前的伸缩值
    private float curScale;//当前的伸缩值

    private Bitmap sourceBitmap;
    private int[] screenSize;//屏幕尺寸信息
    private float translateX;//平移到屏幕中心的X轴距离
    private float translateY;//平移到屏幕中心的Y轴距离
    private boolean isChangeScaleType;//是否转换为Matrix模式
    private int imgpath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_img);
        initView();

        initData();
    }

    private void initData() {

        if (getIntent().hasExtra("imgpath")) {
            imgpath = getIntent().getIntExtra("imgpath", 0);
            if(imgpath == 0){
                Toast.makeText(this,"没有获取到图片地址，请检查代码",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        scaleGestureDetector = new ScaleGestureDetector(this, new MyScaleGestureDetectorlistener());

        curScale = 1.0f;

        //得到图片
        sourceBitmap = BitmapFactory.decodeResource(getResources(), imgpath);
        screenSize = getScreenSize();

        //使图片显示在中心
//        translateX = screenSize[0] / 2;
        translateX = screenSize[0] / 2 - sourceBitmap.getWidth() / 2;
//        translateY =  - screenSize[1] / 2;
        translateY = sourceBitmap.getHeight() / 2 - screenSize[1] / 2;

        matrix = new Matrix();
        preScale = screenSize[0] * 1.0f / sourceBitmap.getWidth();//图片完全显示的伸缩值
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv.setImageBitmap(sourceBitmap);
        isChangeScaleType = true;


        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return scaleGestureDetector.onTouchEvent(event);
            }
        });
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        rllayout = (RelativeLayout) findViewById(R.id.rllayout);
    }


    class MyScaleGestureDetectorlistener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (isChangeScaleType) {
                iv.setScaleType(ImageView.ScaleType.MATRIX);
                isChangeScaleType = false;
            }
            curScale = detector.getScaleFactor() * preScale;//当前的伸缩值*之前的伸缩值 保持连续性

            if (curScale > 5 || curScale < 0.1) {//当放大倍数大于5或者缩小倍数小于0.1倍 就不伸缩图片 返回true取消处理伸缩手势事件
                preScale = curScale;
                return true;
            }

            matrix.setScale(curScale, curScale, iv.getWidth() / 2, iv.getHeight() / 2);//在屏幕中心伸缩
            matrix.preTranslate(translateX, translateY);//使图片平移到屏幕中心显示

            iv.setImageMatrix(matrix);//改变矩阵值显示图片
            preScale = curScale;//保存上一次的伸缩值

            return false;
        }
    }

    //获取屏幕尺寸
    private int[] getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int[] screenSize = new int[2];
        screenSize[0] = displayMetrics.widthPixels;
        screenSize[1] = displayMetrics.heightPixels;
        return screenSize;
    }

}
