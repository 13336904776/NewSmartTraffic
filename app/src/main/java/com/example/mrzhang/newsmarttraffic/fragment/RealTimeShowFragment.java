package com.example.mrzhang.newsmarttraffic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mrzhang.newsmarttraffic.BaseUrl;
import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.adapter.MyRealTimeShowAdapter;
import com.example.mrzhang.newsmarttraffic.application.MyApp;
import com.example.mrzhang.newsmarttraffic.bean.SenseBean;
import com.example.mrzhang.newsmarttraffic.db.OrmDBHelper;
import com.example.mrzhang.newsmarttraffic.enent.MessageEvent;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment1;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment2;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment3;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment4;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment5;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment6;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class RealTimeShowFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private ViewPager mVp;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private LinearLayout mLlDit;
    private Dao<SenseBean, Integer> senseDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_time_show, container, false);
        initView(view);
        initViewPage();

        OrmDBHelper ormHelper = OrmDBHelper.gethelp(getActivity());
        try {
            senseDao = ormHelper.getSenseDao();

            getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void getData() throws SQLException {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                JSONObject object = new JSONObject();
                final long timeMillis = System.currentTimeMillis();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yy$MM&dd HH:mm:ss",Locale.CHINA);
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
                final String format = dateFormat.format(timeMillis);
                Log.e("zz", "当前时间==》" + format);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BaseUrl.ALLURL + "get_all_sense", object.toString(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Gson gson = new Gson();
                                SenseBean senseBean = gson.fromJson(jsonObject.toString(), SenseBean.class);

                                if ("S".equals(senseBean.getRESULT())) {
                                    senseBean.setCurrentTime(timeMillis + "");
                                    senseBean.setMs(format);
                                    try {
                                        List<SenseBean> senseBeans = senseDao.queryForAll();
                                        if (senseBeans.size() >= 20) {
                                            for (int i = 0; i <= senseBeans.size() - 20; i++) {
                                                senseDao.delete(senseBeans.get(i));
                                            }
//                                          senseDao.create(senseBean);
                                            senseDao.createIfNotExists(senseBean);
                                        } else {
                                            senseDao.createIfNotExists(senseBean);
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                    int temperature = senseBean.getTemperature();
                                    //向fragment1-6发送数据，fragment1-6收到数据后更新
                                    EventBus.getDefault().post(new MessageEvent("temperature", format, temperature));
                                    EventBus.getDefault().post(new MessageEvent("pm2.5", format, senseBean.getPm25()));
                                    EventBus.getDefault().post(new MessageEvent("co2", format, senseBean.getCo2()));
                                    EventBus.getDefault().post(new MessageEvent("lightIntensity", format, senseBean.getLightIntensity()));
                                    EventBus.getDefault().post(new MessageEvent("humidity", format, senseBean.getHumidity()));

                                } else {
                                    Toast.makeText(getActivity(), senseBean.getERRMSG(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, null);
                MyApp.getRequestQueue().add(request);
            }
        }, 1000, 3000);
    }

    private void initViewPage() {
        List<Fragment> mList = new ArrayList<Fragment>();
        mList.add(new Fragment1());
        mList.add(new Fragment2());
        mList.add(new Fragment3());
        mList.add(new Fragment4());
        mList.add(new Fragment5());
        mList.add(new Fragment6());

        FragmentManager fragmentManager = getFragmentManager();
        MyRealTimeShowAdapter myRealTimeShowAdapter = new MyRealTimeShowAdapter(fragmentManager, mList);
        mVp.setAdapter(myRealTimeShowAdapter);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTv1.setBackgroundResource(R.drawable.bg_circle_white);
                mTv2.setBackgroundResource(R.drawable.bg_circle_white);
                mTv3.setBackgroundResource(R.drawable.bg_circle_white);
                mTv4.setBackgroundResource(R.drawable.bg_circle_white);
                mTv5.setBackgroundResource(R.drawable.bg_circle_white);
                mTv6.setBackgroundResource(R.drawable.bg_circle_white);
                switch (position) {
                    case 0:
                        mTv1.setBackgroundResource(R.drawable.bg_circle_gray);
                        break;
                    case 1:
                        mTv2.setBackgroundResource(R.drawable.bg_circle_gray);
                        break;
                    case 2:
                        mTv3.setBackgroundResource(R.drawable.bg_circle_gray);
                        break;
                    case 3:
                        mTv4.setBackgroundResource(R.drawable.bg_circle_gray);
                        break;
                    case 4:
                        mTv5.setBackgroundResource(R.drawable.bg_circle_gray);
                        break;
                    case 5:
                        mTv6.setBackgroundResource(R.drawable.bg_circle_gray);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView(View view) {
        mVp = (ViewPager) view.findViewById(R.id.vp);
        mTv1 = (TextView) view.findViewById(R.id.tv1);
        mTv2 = (TextView) view.findViewById(R.id.tv2);
        mTv3 = (TextView) view.findViewById(R.id.tv3);
        mTv4 = (TextView) view.findViewById(R.id.tv4);
        mTv5 = (TextView) view.findViewById(R.id.tv5);
        mTv6 = (TextView) view.findViewById(R.id.tv6);
        mLlDit = (LinearLayout) view.findViewById(R.id.ll_dit);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTv4.setOnClickListener(this);
        mTv5.setOnClickListener(this);
        mTv6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv1:
                mVp.setCurrentItem(0);
                break;
            case R.id.tv2:
                mVp.setCurrentItem(1);
                break;
            case R.id.tv3:
                mVp.setCurrentItem(2);
                break;
            case R.id.tv4:
                mVp.setCurrentItem(3);
                break;
            case R.id.tv5:
                mVp.setCurrentItem(4);
                break;
            case R.id.tv6:
                mVp.setCurrentItem(5);
                break;
        }
    }
}
