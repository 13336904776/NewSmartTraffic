package com.example.mrzhang.newsmarttraffic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.adapter.MyRealTimeShowAdapter;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment1;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment2;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment3;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment4;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment5;
import com.example.mrzhang.newsmarttraffic.fragment.realtime.Fragment6;

import java.util.ArrayList;
import java.util.List;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_time_show, container, false);
        initView(view);
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
        return view;
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
