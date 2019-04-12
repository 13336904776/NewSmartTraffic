package com.example.mrzhang.newsmarttraffic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.activity.MyVideoActivity;
import com.example.mrzhang.newsmarttraffic.activity.ScaleImgActivity;
import com.example.mrzhang.newsmarttraffic.bean.VideoBean;
import com.example.mrzhang.newsmarttraffic.utils.MyLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<VideoBean> mLeftVideoList;
    private HashMap<String, Object> hashMap;
    private List<Map<String, Object>> mleftdata;
    private List<Map<String, Object>> mRightDataMap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_violation_query, container, false);
        initView(view);

        mLeftVideoList = new ArrayList<VideoBean>();
        mLeftVideoList.add(new VideoBean("/0003/paomo.mp4", R.mipmap.violation, "泡沫"));
        mLeftVideoList.add(new VideoBean("/0003/冰雨.mp4", R.mipmap.violation, "冰雨"));
        mLeftVideoList.add(new VideoBean("/0003/进阶.mlv", R.mipmap.violation, "进阶"));
        mLeftVideoList.add(new VideoBean("/0003/paomo.mp4", R.mipmap.violation, "泡沫"));
        mLeftVideoList.add(new VideoBean("/0003/冰雨.mp4", R.mipmap.violation, "冰雨"));
        mLeftVideoList.add(new VideoBean("/0003/进阶.mlv", R.mipmap.violation, "进阶"));

        int[] rightData = {R.mipmap.one,R.mipmap.two,R.mipmap.three,R.mipmap.four,R.mipmap.one};
        mRightDataMap = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < rightData.length; i++) {
            HashMap<String, Object> HashMap = new HashMap<>();
            HashMap.put("iv", rightData[i]);
            mRightDataMap.add(HashMap);
        }

        mleftdata = getleftData();

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MyLog.showe("checkedId==>" + checkedId + "  CheckedRadioButtonId==>" + group.getCheckedRadioButtonId());
                switch (checkedId) {
                    case R.id.rb1:
                        SimpleAdapter leftAdapter = new SimpleAdapter(getActivity(), mleftdata, R.layout.item_violation_left, new String[]{"img", "tv"}, new int[]{R.id.img, R.id.tv});
                        mGridview.setAdapter(leftAdapter);
                        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //获得文件的路径
                                Map<String, Object> map = mleftdata.get(position);
                                String path = (String) map.get("path");
                                String videopath = Environment.getExternalStorageDirectory().getPath() + path;
                                //判断文件是否存在
                                File file = new File(videopath);
                                if (!file.exists()) {//不存在
                                    Toast.makeText(getActivity(), "视频文件路径错误", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(getActivity(), MyVideoActivity.class);
                                    intent.putExtra("videopath", videopath);
                                    startActivity(intent);
                                }
                            }
                        });
                        break;
                    case R.id.rb2:
                        SimpleAdapter rightAdapter = new SimpleAdapter(getActivity(), mRightDataMap, R.layout.item_violation_right, new String[]{"iv"}, new int[]{R.id.img});
                        mGridview.setAdapter(rightAdapter);
                        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> stringObjectMap = mRightDataMap.get(position);
                                int ivId = (int) stringObjectMap.get("iv");
                                Intent intent = new Intent(getActivity(), ScaleImgActivity.class);
                                intent.putExtra("imgpath", ivId);
                                startActivity(intent);
                            }
                        });

                        break;
                }

            }
        });
        mRb1.setChecked(true);
        return view;
    }

    private List<Map<String, Object>> getleftData() {
        List<Map<String, Object>> mLeftData = new ArrayList<Map<String, Object>>();
        hashMap = new HashMap<>();
        hashMap.put("img", R.mipmap.violation);
        hashMap.put("tv", "泡沫");
        hashMap.put("path", "/0003/paomo.mp4");
        mLeftData.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("img", R.mipmap.violation);
        hashMap.put("tv", "冰雨");
        hashMap.put("path", "/0003/冰雨.mp4");
        mLeftData.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("img", R.mipmap.violation);
        hashMap.put("tv", "进阶");
        hashMap.put("path", "/0003/进阶.mlv");
        mLeftData.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("img", R.mipmap.violation);
        hashMap.put("tv", "泡沫");
        hashMap.put("path", "/0003/paomo.mp4");
        mLeftData.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("img", R.mipmap.violation);
        hashMap.put("tv", "冰雨");
        hashMap.put("path", "/0003/冰雨.mp4");
        mLeftData.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("img", R.mipmap.violation);
        hashMap.put("tv", "进阶");
        hashMap.put("path", "/0003/进阶.mlv");
        mLeftData.add(hashMap);
        return mLeftData;
    }

    private void initView(View view) {
        mRb1 = (RadioButton) view.findViewById(R.id.rb1);
        mRb2 = (RadioButton) view.findViewById(R.id.rb2);
        mRg = (RadioGroup) view.findViewById(R.id.rg);
        mBtn1 = (Button) view.findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) view.findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);

        mGridview = (GridView) view.findViewById(R.id.gridview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn1:
                File filesDir = getActivity().getFilesDir();
                File externalFilesDir = getActivity().getExternalFilesDir(null);
                File externalCacheDir = getActivity().getExternalCacheDir();
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                MyLog.showe("filesDir==>" + filesDir + "  filesDir.path==>" + filesDir.getPath());
                MyLog.showe("externalFilesDir==>" + externalFilesDir + "  externalFilesDir.path==>" + externalFilesDir.getPath());
                MyLog.showe("externalCacheDir==>" + externalCacheDir + "  externalCacheDir.path==>" + externalCacheDir.getPath());
                MyLog.showe("externalStorageDirectory==>" + externalStorageDirectory + "  externalStorageDirectory.path==>" + externalStorageDirectory.getPath());

                break;
            case R.id.btn2:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // SD卡可用
                    MyLog.showe("sd可用");
                } else {
                    Toast.makeText(getActivity(), "SD卡不可用，请检查SD卡", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }




}
