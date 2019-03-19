package com.example.mrzhang.smarttraffic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrzhang.smarttraffic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mVp;
    private LinearLayout mDotLl;
    /**
     * 跳过
     */
    private Button mSkipBtn;
    /**
     * 完成
     */
    private Button mOkBtn;
    private List<View> vpViews;
    private MyPageAdapter myPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除任务栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        initView();
        initListener();
        initData();
    }

    public void initView() {

        mVp = (ViewPager) findViewById(R.id.vp);
        mDotLl = (LinearLayout) findViewById(R.id.dot_ll);
        mSkipBtn = (Button) findViewById(R.id.skip_btn);
        mSkipBtn.setOnClickListener(this);
        mOkBtn = (Button) findViewById(R.id.ok_btn);
        mOkBtn.setOnClickListener(this);
    }

    public void initListener() {

    }

    public void initData() {

        initViewPage();

        setDot(0);

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setDot(position);
                if(position == (vpViews.size()-1)){
                    mOkBtn.setVisibility(View.VISIBLE);
                    mSkipBtn.setVisibility(View.VISIBLE);
                }else {
                    mOkBtn.setVisibility(View.GONE);
                    mSkipBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setDot(int position) {
        mDotLl.removeAllViews();
        for (int i = 0; i < vpViews.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText("。");
            if(position != i) {
                textView.setTextColor(Color.WHITE);
            }else {
                textView.setTextColor(Color.RED);
            }
            mDotLl.addView(textView);
        }
    }

    private void initViewPage() {
        vpViews = new ArrayList<>();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        vpViews.add(layoutInflater.inflate(R.layout.welcome1, null ));
        vpViews.add(layoutInflater.inflate(R.layout.welcome2, null));
        vpViews.add(layoutInflater.inflate(R.layout.welcome3, null));
        vpViews.add(layoutInflater.inflate(R.layout.welcome4, null));
        myPageAdapter = new MyPageAdapter(vpViews);
        mVp.setAdapter(myPageAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.skip_btn:
//                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
                mVp.setCurrentItem(vpViews.size()-1,true);
                break;
            case R.id.ok_btn:
                Intent intent1 = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    class MyPageAdapter extends PagerAdapter{
        List<View> mlist;
        public MyPageAdapter(List<View> mlist) {
            this.mlist = mlist;
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mlist.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mlist.get(position));
        }
    }
}
