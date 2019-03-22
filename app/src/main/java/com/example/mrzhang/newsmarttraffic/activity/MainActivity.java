package com.example.mrzhang.newsmarttraffic.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.adapter.MenuAdapter;
import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.bean.MenuBean;
import com.example.mrzhang.newsmarttraffic.fragment.AccountManageFragment;
import com.example.mrzhang.newsmarttraffic.fragment.BaseFragment;
import com.example.mrzhang.newsmarttraffic.fragment.BusInquiryFragment;
import com.example.mrzhang.newsmarttraffic.fragment.DataAnalysisFragment;
import com.example.mrzhang.newsmarttraffic.fragment.InnovativeFragment;
import com.example.mrzhang.newsmarttraffic.fragment.LifeAssistantFragment;
import com.example.mrzhang.newsmarttraffic.fragment.PersonalCenterFragment;
import com.example.mrzhang.newsmarttraffic.fragment.RoadConditionFragment;
import com.example.mrzhang.newsmarttraffic.fragment.TrafficLightManageFragment;
import com.example.mrzhang.newsmarttraffic.fragment.ViolationQueryFragment;
import com.example.mrzhang.newsmarttraffic.utils.MyLog;
import com.example.mrzhang.newsmarttraffic.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private RecyclerView mLeftRcv;
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
    private List<BaseFragment> fragments;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout mDrawLayout;
    private LinearLayout mLlLeftHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    public void initView() {

        mLeftRcv = (RecyclerView) findViewById(R.id.left_rcv);
        mTitleLeftIv = (ImageView) findViewById(R.id.title_left_iv);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mFgRl = (RelativeLayout) findViewById(R.id.fg_rl);
        mHomeLl = (LinearLayout) findViewById(R.id.home_ll);
        mDrawLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        mTitleLeftIv.setOnClickListener(this);
        mLlLeftHome = (LinearLayout) findViewById(R.id.ll_left_home);
    }

    public void initListener() {

    }

    public void initData() {
        mTitleLeftIv.setBackgroundResource(R.mipmap.ic_menu);
        mLeftRcv.setLayoutManager(new LinearLayoutManager(this));

        List<MenuBean> mDataList = new ArrayList<MenuBean>();
        mDataList.add(new MenuBean(R.mipmap.menu_star, "我的账户"));
        mDataList.add(new MenuBean(R.mipmap.menu_book, "公交查询"));
        mDataList.add(new MenuBean(R.mipmap.menu_slideshow, "红绿灯管理"));
        mDataList.add(new MenuBean(R.mipmap.menu_target, "违章查询"));
        mDataList.add(new MenuBean(R.mipmap.menu_download, "道路状况"));
        mDataList.add(new MenuBean(R.mipmap.menu_star, "生活助手"));
        mDataList.add(new MenuBean(R.mipmap.menu_book, "数据分析"));//data analysis
        mDataList.add(new MenuBean(R.mipmap.menu_slideshow, "个人中心"));
        mDataList.add(new MenuBean(R.mipmap.menu_target, "创新题"));
        mDataList.add(new MenuBean(R.mipmap.menu_target, "意见反馈"));
        mDataList.add(new MenuBean(R.mipmap.menu_download, "退出"));

        fragments = new ArrayList<BaseFragment>();
        fragments.add(new AccountManageFragment());
        fragments.add(new BusInquiryFragment());
        fragments.add(new TrafficLightManageFragment());
        fragments.add(new ViolationQueryFragment());
        fragments.add(new RoadConditionFragment());
        fragments.add(new LifeAssistantFragment());
        fragments.add(new DataAnalysisFragment());
        fragments.add(new PersonalCenterFragment());
        fragments.add(new InnovativeFragment());

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        changeFragment(0);

        MenuAdapter menuAdapter = new MenuAdapter(this, mDataList);
        mLeftRcv.setAdapter(menuAdapter);
        mLeftRcv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        menuAdapter.setMenuClickListen(new MenuAdapter.MenuClickListen() {
            @Override
            public void Click(int postion,String title) {
                MyLog.showe("zzz" + postion);
                mTitleTv.setText(title);
                if (postion < 9) {
                    changeFragment(postion);
                }
                switch (postion) {
                    case 0://我的账户

                        break;
                    case 1://公交查询
                        break;
                    case 2://红绿灯管理
                        break;
                    case 3://违章查询
                        break;
                    case 4://道路状况
                        break;
                    case 5://生活助手
                        break;
                    case 6://数据分析
                        break;
                    case 7://个人中心
                        break;
                    case 8://创新题
                        break;
                    case 9://意见反馈
                        Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                        startActivity(intent);
                        break;
                    case 10://退出
                        MyToast.show(MainActivity.this, "点击了退出");
                        break;
                    default:
                        break;

                }
                mDrawLayout.closeDrawer(mLlLeftHome);
            }
        });

    }

    private void changeFragment(int position) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fg_rl, fragments.get(position)).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.title_left_iv:
                mDrawLayout.openDrawer(mLlLeftHome);
                break;
        }
    }
}
