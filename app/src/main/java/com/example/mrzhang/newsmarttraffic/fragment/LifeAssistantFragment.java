package com.example.mrzhang.newsmarttraffic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.enent.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LifeAssistantFragment extends BaseFragment {
    private TextView tv_light;
    private TextView tv_light_hint;
    private TextView tv_ganmao;
    private TextView tv_ganmao_hint;
    private TextView tv_clother;
    private TextView tv_clother_hint;
    private TextView tv_sport;
    private TextView tv_sport_hint;
    private TextView tv_air;
    private TextView tv_air_hint;
    private LinearLayout ll_light;
    private LinearLayout ll_ganmao;
    private LinearLayout ll_clother;
    private LinearLayout ll_sport;
    private LinearLayout ll_air;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_life_assistant, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        tv_light = (TextView) inflate.findViewById(R.id.tv_light);
        tv_light_hint = (TextView) inflate.findViewById(R.id.tv_light_hint);
        tv_ganmao = (TextView) inflate.findViewById(R.id.tv_ganmao);
        tv_ganmao_hint = (TextView) inflate.findViewById(R.id.tv_ganmao_hint);
        tv_clother = (TextView) inflate.findViewById(R.id.tv_clother);
        tv_clother_hint = (TextView) inflate.findViewById(R.id.tv_clother_hint);
        tv_sport = (TextView) inflate.findViewById(R.id.tv_sport);
        tv_sport_hint = (TextView) inflate.findViewById(R.id.tv_sport_hint);
        tv_air = (TextView) inflate.findViewById(R.id.tv_air);
        tv_air_hint = (TextView) inflate.findViewById(R.id.tv_air_hint);
        ll_light = (LinearLayout) inflate.findViewById(R.id.ll_light);
        ll_ganmao = (LinearLayout) inflate.findViewById(R.id.ll_ganmao);
        ll_clother = (LinearLayout) inflate.findViewById(R.id.ll_clother);
        ll_sport = (LinearLayout) inflate.findViewById(R.id.ll_sport);
        ll_air = (LinearLayout) inflate.findViewById(R.id.ll_air);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        String from = event.getFrom();
        int value = event.getValue();
        if ("temperature".equals(from)) {
            if (value < 12) {
                ll_clother.setBackgroundResource(R.color.colorBlue);
                tv_clother.setText("冷(" + value + ")");
                tv_clother_hint.setText("建议穿长袖衬衫、单裤等服装");
            } else if (value >= 12 && value <= 21) {
                ll_clother.setBackgroundResource(R.color.colorBlue);
                tv_clother.setText("舒适(" + value + ")");
                tv_clother_hint.setText("建议穿短袖衬衫、单裤等服装");
            } else {
                tv_clother.setText("热(" + value + ")");
                tv_clother_hint.setText("建议穿T桖、短裤外滩等夏季服装");
                ll_clother.setBackgroundResource(R.color.colorRed);
            }
        } else if ("pm2.5".equals(from)) {
            if (value < 30) {
                tv_air.setText("优(" + value + ")");
                tv_air_hint.setText("空气质量非常好，非常适合户外活动");
                ll_air.setBackgroundResource(R.color.colorBlue);
            } else if (value >= 30 && value <= 100) {
                ll_air.setBackgroundResource(R.color.colorBlue);
                tv_air.setText("良(" + value + ")");
                tv_air_hint.setText("易感冒人群应适当避免活动");
            } else {
                ll_clother.setBackgroundResource(R.color.colorRed);
                tv_air.setText("污染(" + value + ")");
                tv_air_hint.setText("空气质量差，不适合外出");
            }
        } else if ("co2".equals(from)) {
            if (value < 300) {
                ll_sport.setBackgroundResource(R.color.colorBlue);
                tv_sport.setText("适宜(" + value + ")");
                tv_sport_hint.setText("气候适宜，推荐您进行户外活动");
            } else if (value >= 300 && value <= 6000) {
                ll_sport.setBackgroundResource(R.color.colorBlue);
                tv_sport.setText("中(" + value + ")");
                tv_sport_hint.setText("易感人群应适当避免活动");
            } else {
                ll_sport.setBackgroundResource(R.color.colorRed);
                tv_sport.setText("较不宜(" + value + ")");
                tv_sport_hint.setText("空气氧气含量低，请在室内活动");
            }
        } else if ("lightIntensity".equals(from)) {
            if (value < 1000) {
                ll_light.setBackgroundResource(R.color.colorBlue);
                tv_light.setText("弱(" + value + ")");
                tv_light_hint.setText("空气质量非常好，非常适合户外活动");
            } else if (value >= 1000 && value <= 3000) {
                ll_light.setBackgroundResource(R.color.colorBlue);
                tv_light.setText("中等(" + value + ")");
                tv_light_hint.setText("易感冒人群应适当避免活动");
            } else {
                ll_light.setBackgroundResource(R.color.colorRed);
                tv_light.setText("强(" + value + ")");
                tv_light_hint.setText("空气质量差，不适合外出");
            }
        } else if ("humidity".equals(from)) {
            if (value < 8) {
                ll_ganmao.setBackgroundResource(R.color.colorBlue);
                tv_ganmao.setText("较易发(" + value + ")");
                tv_ganmao_hint.setText("空气质量非常好，非常适合户外活动");
            } else {
                ll_ganmao.setBackgroundResource(R.color.colorRed);
                tv_ganmao.setText("少发(" + value + ")");
                tv_ganmao_hint.setText("空气质量差，不适合外出");
            }
        }
    }


    //Activity
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
