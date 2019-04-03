package com.example.mrzhang.newsmarttraffic.fragment.realtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;


public class Fragment4 extends RealTimeBaseFragment {

    private TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.item_real_time, container, false);
        initView(inflate);
        tv_title.setText("CO 2");

        return inflate;
    }

    private void initView(View inflate) {
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
    }
}
