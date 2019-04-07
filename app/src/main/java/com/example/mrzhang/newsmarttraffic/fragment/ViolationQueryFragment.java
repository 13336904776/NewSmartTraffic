package com.example.mrzhang.newsmarttraffic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.activity.MyVideoActivity;
import com.example.mrzhang.newsmarttraffic.activity.VideoViewActivity;
import com.example.mrzhang.newsmarttraffic.bean.VideoBean;
import com.example.mrzhang.newsmarttraffic.utils.MyLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 违章查询
 */
public class ViolationQueryFragment extends BaseFragment implements View.OnClickListener {
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
    /**
     * 11
     */
    private Button mBtn1;
    /**
     * 22
     */
    private Button mBtn2;
    /**
     * 33
     */
    private Button mBtn3;
    /**
     * 4
     */
    private Button mBtn4;
    /**
     * 5
     */
    private Button mBtn5;
    /**
     * 6
     */
    private Button mBtn6;
    /**
     * 7
     */
    private Button mBtn7;
    private GridView mGridview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_violation_query, container, false);
        initView(view);
        mRb1.setChecked(true);

        List<VideoBean> videoList = new ArrayList<VideoBean>();
        videoList.add(new VideoBean("/0003/paomo.mp4",R.mipmap.violation,"泡沫"));
        videoList.add(new VideoBean("/0003/冰雨.mp4",R.mipmap.violation,"冰雨"));
        videoList.add(new VideoBean("/0003/进阶.mlv",R.mipmap.violation,"进阶"));
        videoList.add(new VideoBean("/0003/paomo.mp4",R.mipmap.violation,"泡沫"));
        videoList.add(new VideoBean("/0003/冰雨.mp4",R.mipmap.violation,"冰雨"));
        videoList.add(new VideoBean("/0003/进阶.mlv",R.mipmap.violation,"进阶"));


        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MyLog.showe("checkedId==>" + checkedId + "  CheckedRadioButtonId==>" + group.getCheckedRadioButtonId());
                if (checkedId == R.id.rb1) {


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
        mBtn1 = (Button) view.findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) view.findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mBtn3 = (Button) view.findViewById(R.id.btn3);
        mBtn3.setOnClickListener(this);
        mBtn4 = (Button) view.findViewById(R.id.btn4);
        mBtn4.setOnClickListener(this);
        mBtn5 = (Button) view.findViewById(R.id.btn5);
        mBtn5.setOnClickListener(this);
        mBtn6 = (Button) view.findViewById(R.id.btn6);
        mBtn6.setOnClickListener(this);
        mBtn7 = (Button) view.findViewById(R.id.btn7);
        mBtn7.setOnClickListener(this);
        mGridview = (GridView) view.findViewById(R.id.gridview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn1:
                Intent intent = new Intent(getActivity(), MyVideoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                Intent intent1 = new Intent(getActivity(), VideoViewActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn3:
                File filesDir = getActivity().getFilesDir();
                File externalFilesDir = getActivity().getExternalFilesDir(null);
                File externalCacheDir = getActivity().getExternalCacheDir();
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                MyLog.showe("filesDir==>" + filesDir + "  filesDir.path==>" + filesDir.getPath());
                MyLog.showe("externalFilesDir==>" + externalFilesDir + "  externalFilesDir.path==>" + externalFilesDir.getPath());
                MyLog.showe("externalCacheDir==>" + externalCacheDir + "  externalCacheDir.path==>" + externalCacheDir.getPath());
                MyLog.showe("externalStorageDirectory==>" + externalStorageDirectory + "  externalStorageDirectory.path==>" + externalStorageDirectory.getPath());
                break;
            case R.id.btn4:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // SD卡可用
                    MyLog.showe("sd可用");
                } else {
                    Toast.makeText(getActivity(), "SD卡不可用，请检查SD卡", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                break;
            case R.id.btn7:
                break;
        }
    }
}
