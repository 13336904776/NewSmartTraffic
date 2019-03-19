package com.example.mrzhang.smarttraffic.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.mrzhang.smarttraffic.MenuAdapter;
import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

//    private RecyclerView mLeftRcv;
    private ImageView mTitleLeftIv;
    /**
     * 标题
     */
    private TextView mTitleTv;
    /**
     * 副标题
     */
    private TextView mTitleRightTv;
    private RelativeLayout mFgRl;
    private LinearLayout mHomeLl;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    public void initView() {

//        mLeftRcv = (RecyclerView) findViewById(R.id.left_rcv);
        listview =findViewById(R.id.listview);
        mTitleLeftIv = (ImageView) findViewById(R.id.title_left_iv);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mFgRl = (RelativeLayout) findViewById(R.id.fg_rl);
        mHomeLl = (LinearLayout) findViewById(R.id.home_ll);
    }

    public void initListener() {

    }

    public void initData() {
//        mLeftRcv.setLayoutManager(new LinearLayoutManager(this));
//        mLeftRcv.setNestedScrollingEnabled(false);

        List<MenuBean> mDataList = new ArrayList<MenuBean>();
        mDataList.add(new MenuBean(R.mipmap.menu_star,"账户管理"));
        mDataList.add(new MenuBean(R.mipmap.menu_book,"公交查询"));
        mDataList.add(new MenuBean(R.mipmap.menu_slideshow,"红绿灯管理"));
        mDataList.add(new MenuBean(R.mipmap.menu_target,"违章查询"));
        mDataList.add(new MenuBean(R.mipmap.menu_download,"道路状况"));
        mDataList.add(new MenuBean(R.mipmap.menu_star,"生活助手"));
        mDataList.add(new MenuBean(R.mipmap.menu_book,"数据分析"));
        mDataList.add(new MenuBean(R.mipmap.menu_slideshow,"个人中心"));
        mDataList.add(new MenuBean(R.mipmap.menu_target,"创新题"));
        mDataList.add(new MenuBean(R.mipmap.menu_download,"退出"));
        List<String> list =new ArrayList<>();
        list.add("11111111111111111");
        list.add("222222222221");
        list.add("444444444444411111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");
        list.add("33333333333333333331111111");


        Adapter_Fg3_CarPeccancy adapter =new Adapter_Fg3_CarPeccancy(this,list);
        listview.setAdapter(adapter);
//        mLeftRcv.setAdapter(new MenuAdapter(this,mDataList));
    }
}
