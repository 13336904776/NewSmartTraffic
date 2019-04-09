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

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.adapter.MenuAdapter;
import com.example.mrzhang.newsmarttraffic.bean.MenuBean;
import com.example.mrzhang.newsmarttraffic.fragment.AccountManageFragment;
import com.example.mrzhang.newsmarttraffic.fragment.BaseFragment;
import com.example.mrzhang.newsmarttraffic.fragment.BusInquiryFragment;
import com.example.mrzhang.newsmarttraffic.fragment.DataAnalysisFragment;
import com.example.mrzhang.newsmarttraffic.fragment.InnovativeFragment;
import com.example.mrzhang.newsmarttraffic.fragment.LifeAssistantFragment;
import com.example.mrzhang.newsmarttraffic.fragment.PersonalCenterFragment;
import com.example.mrzhang.newsmarttraffic.fragment.QrFragment;
import com.example.mrzhang.newsmarttraffic.fragment.RealTimeShowFragment;
import com.example.mrzhang.newsmarttraffic.fragment.RoadConditionFragment;
import com.example.mrzhang.newsmarttraffic.fragment.TrafficLightManageFragment;
import com.example.mrzhang.newsmarttraffic.fragment.ViolationQueryFragment;
import com.example.mrzhang.newsmarttraffic.utils.Constant;
import com.example.mrzhang.newsmarttraffic.utils.MyLog;
import com.example.mrzhang.newsmarttraffic.utils.SpUtil;

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
    private AccountManageFragment accountManageFragment;
    private BusInquiryFragment busInquiryFragment;
    private TrafficLightManageFragment trafficLightManageFragment;
    private ViolationQueryFragment violationQueryFragment;
    private RoadConditionFragment roadConditionFragment;
    private LifeAssistantFragment lifeAssistantFragment;
    private DataAnalysisFragment dataAnalysisFragment;
    private PersonalCenterFragment personalCenterFragment;
    private InnovativeFragment innovativeFragment;
    private FragmentManager supportFragmentManager;
    private RealTimeShowFragment realTimeShowFragment;
    private QrFragment qrFragment;

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
        mLeftRcv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        List<MenuBean> mDataList = new ArrayList<MenuBean>();
        mDataList.add(new MenuBean(R.mipmap.menu_star, "我的账户"));
        mDataList.add(new MenuBean(R.mipmap.menu_book, "公交查询"));
        if (!SpUtil.getS(this, Constant.SP_USERROLE).equals("nor_user")) {
            mDataList.add(new MenuBean(R.mipmap.menu_slideshow, "红绿灯管理"));
        }
        mDataList.add(new MenuBean(R.mipmap.menu_target, "车辆违章"));
        mDataList.add(new MenuBean(R.mipmap.menu_target, "二维码支付"));
        mDataList.add(new MenuBean(R.mipmap.menu_target, "实时显示"));
        mDataList.add(new MenuBean(R.mipmap.menu_download, "道路状况"));
        mDataList.add(new MenuBean(R.mipmap.menu_star, "生活助手"));
        mDataList.add(new MenuBean(R.mipmap.menu_book, "数据分析"));//data analysis
        mDataList.add(new MenuBean(R.mipmap.menu_slideshow, "个人中心"));
        mDataList.add(new MenuBean(R.mipmap.menu_target, "创新题"));
        mDataList.add(new MenuBean(R.mipmap.menu_target, "意见反馈"));
        mDataList.add(new MenuBean(R.mipmap.menu_download, "退出"));

        accountManageFragment = new AccountManageFragment();
        busInquiryFragment = new BusInquiryFragment();
        trafficLightManageFragment = new TrafficLightManageFragment();
        violationQueryFragment = new ViolationQueryFragment();
        qrFragment = new QrFragment();
        realTimeShowFragment = new RealTimeShowFragment();
        roadConditionFragment = new RoadConditionFragment();
        lifeAssistantFragment = new LifeAssistantFragment();
        dataAnalysisFragment = new DataAnalysisFragment();
        personalCenterFragment = new PersonalCenterFragment();
        innovativeFragment = new InnovativeFragment();

        //默认显示第一个（我的账户）页面
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        mTitleTv.setText("我的账户");
        initFristPage();

        //初始化左边列表布局
        MenuAdapter menuAdapter = new MenuAdapter(this, mDataList);
        mLeftRcv.setAdapter(menuAdapter);

        menuAdapter.setMenuClickListen(new MenuAdapter.MenuClickListen() {
            @Override
            public void Click(int postion, String title) {
                MyLog.showe("zzz" + postion);
                mTitleTv.setText(title);
                mDrawLayout.closeDrawer(mLlLeftHome);
                mTitleRightTv.setText("");
                switch (title) {
                    case "我的账户"://我的账户
                        initFristPage();
                        break;
                    case "公交查询"://公交查询
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, busInquiryFragment).commit();
                        break;
                    case "红绿灯管理"://红绿灯管理
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, trafficLightManageFragment).commit();
                        break;
                    case "车辆违章"://车辆违章
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, violationQueryFragment).commit();
                        break;
                    case "二维码支付"://二维码支付
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, qrFragment).commit();
                        break;
                    case "实时显示"://实时显示
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, realTimeShowFragment).commit();
                        break;
                    case "道路状况"://道路状况
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, roadConditionFragment).commit();
                        break;
                    case "生活助手"://生活助手
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, lifeAssistantFragment).commit();
                        break;
                    case "数据分析"://数据分析
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, dataAnalysisFragment).commit();
                        break;
                    case "个人中心"://个人中心
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, personalCenterFragment).commit();
                        break;
                    case "创新题"://创新题
                        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, innovativeFragment).commit();
                        break;
                    case "意见反馈"://意见反馈
                        Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                        startActivity(intent);
                        break;
                    case "退出"://退出
                        SpUtil.putB(MainActivity.this, Constant.SP_ISSELFLOGIN, false);
                        Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    default:
                        break;

                }

            }
        });

    }

    private void initFristPage() {
        supportFragmentManager.beginTransaction().replace(R.id.fg_rl, accountManageFragment).commit();
        mTitleRightTv.setText("账单管理");
        mTitleRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BillManageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void changeFragment(int position) {


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
