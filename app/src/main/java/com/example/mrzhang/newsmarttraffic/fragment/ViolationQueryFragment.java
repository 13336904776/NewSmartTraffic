package com.example.mrzhang.newsmarttraffic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.activity.MyVideoActivity;
import com.example.mrzhang.newsmarttraffic.activity.VideoViewActivity;
import com.example.mrzhang.newsmarttraffic.utils.MyLog;

/**
 * 违章查询
 */
public class ViolationQueryFragment extends BaseFragment {
    private View view;
    /**
     * 违章视频
     */
    private RadioButton mRb1;
    /**
     * 违章图片
     */
    private RadioButton mRb2;
    private RadioGroup mRg;
    private RecyclerView mRcv;


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_violation_query, null);
//        return view;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_violation_query, container, false);
        initView(view);
        mRb1.setChecked(true);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MyLog.showe("checkedId==>"+checkedId+"  CheckedRadioButtonId==>"+group.getCheckedRadioButtonId());
                if(checkedId == R.id.rb2){
                    Intent intent = new Intent(getActivity(), VideoViewActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        mRb1 = (RadioButton) view.findViewById(R.id.rb1);
        mRb2 = (RadioButton) view.findViewById(R.id.rb2);
        mRg = (RadioGroup) view.findViewById(R.id.rg);
        mRcv = (RecyclerView) view.findViewById(R.id.rcv);
    }
}
