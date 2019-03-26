package com.example.mrzhang.newsmarttraffic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mrzhang.newsmarttraffic.BaseUrl;
import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.adapter.TrafficLightAdapter;
import com.example.mrzhang.newsmarttraffic.application.MyApp;
import com.example.mrzhang.newsmarttraffic.bean.TrafficLightBean;
import com.example.mrzhang.newsmarttraffic.utils.Constant;
import com.example.mrzhang.newsmarttraffic.utils.MyToast;
import com.example.mrzhang.newsmarttraffic.utils.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 红绿灯管理
 */
public class TrafficLightManageFragment extends BaseFragment implements View.OnClickListener {


    private View view;
    private Spinner mSpinner;
    /**
     * 查询
     */
    private Button mBtnQuertLight;
    private RecyclerView mRcv;
    private List mlist;
    private String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_traffic_light_manage, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        userName = SpUtil.getS(getContext(), Constant.SP_USERNNME);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRcv.setLayoutManager(linearLayoutManager);
//        mRcv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        mlist = new ArrayList<TrafficLightBean>();

        getData();
    }

    private void getData() {
        mlist.clear();
        for (int i = 1; i <= 5; i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("TrafficLightId",i);
                object.put("UserName",userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final int finalI = i;
            JsonObjectRequest objectRequest = new JsonObjectRequest(BaseUrl.ALLURL + "get_trafficlight_config",
                    object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            if (jsonObject.optString("RESULT").equals("S")){

                                mlist.add(new TrafficLightBean(finalI,jsonObject.optInt("RedTime"),jsonObject.optInt("YellowTime"),jsonObject.optInt("GreenTime")));
                                if (mlist.size()==5){
                                    String s = mSpinner.getSelectedItem().toString();
                                    if("路口升序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o1.getRoadId() - o2.getRoadId();
                                            }
                                        });
                                    }else if("路口降序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o2.getRoadId() - o1.getRoadId();
                                            }
                                        });
                                    }else if("红灯升序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o1.getRed() - o2.getRed();
                                            }
                                        });
                                    }else if("红灯降序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o2.getRed() - o1.getRed();
                                            }
                                        });
                                    }else if("绿灯升序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o1.getGreen() - o2.getGreen();
                                            }
                                        });
                                    }else if("绿灯降序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o2.getGreen() - o1.getGreen();
                                            }
                                        });
                                    }else if("黄灯升序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o1.getYellow() - o2.getYellow();
                                            }
                                        });
                                    }else if("黄灯降序".equals(s)){
                                        Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                            @Override
                                            public int compare(TrafficLightBean o1, TrafficLightBean o2) {
                                                return o2.getYellow() - o1.getYellow();
                                            }
                                        });
                                    }
                                    TrafficLightAdapter lightAdapter = new TrafficLightAdapter(getContext(),mlist);
                                    mRcv.setAdapter(lightAdapter);
                                }
                            }else {
                                MyToast.show(getContext(),jsonObject.optString("ERRMSG"));

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            MyToast.show(getContext(),volleyError.getMessage());
                        }
                    });
            MyApp.getRequestQueue().add(objectRequest);
        }
    }

    private void initView(View inflate) {
        mSpinner = (Spinner) inflate.findViewById(R.id.spinner);
        mBtnQuertLight = (Button) inflate.findViewById(R.id.btn_quert_light);
        mBtnQuertLight.setOnClickListener(this);
        mRcv = (RecyclerView) inflate.findViewById(R.id.rcv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_quert_light:
                getData();
                break;
        }
    }
}
