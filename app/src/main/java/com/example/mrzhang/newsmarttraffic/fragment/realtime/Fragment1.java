package com.example.mrzhang.newsmarttraffic.fragment.realtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrzhang.newsmarttraffic.BaseUrl;
import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.bean.SenseBean;
import com.example.mrzhang.newsmarttraffic.db.OrmDBHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Fragment1 extends RealTimeBaseFragment implements View.OnClickListener {

    private TextView tv_title;
    private LineChart mChart;
    private int index;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.item_real_time, container, false);
        initView(inflate);
        tv_title.setText("温度");

        initChart();

//        setData(20, 30);

        requestQueue = Volley.newRequestQueue(getActivity());


        return inflate;
    }

    private void initView(View inflate) {
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        mChart = (LineChart) inflate.findViewById(R.id.chart1);
    }

    private void initChart() {
        mChart.setMaxVisibleValueCount(20);
//        mChart.setVisibleXRangeMinimum(20);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
//        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);


        YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeft.setDrawLabels(true);

        YAxis axisRight = mChart.getAxisRight();
        axisRight.setDrawLabels(false);

        ArrayList<Entry> entries = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            entries.add(new Entry((int) (1 + Math.random() * (10 - 1 + 1)), i));
//        }
        LineDataSet lineDataSet = new LineDataSet(entries, "linedata");
        ArrayList<LineDataSet> arrayList = new ArrayList<LineDataSet>();
        arrayList.add(lineDataSet);

        ArrayList<String> arrayList1 = new ArrayList<String>();

        LineData lineData = new LineData(arrayList1, lineDataSet);
        lineData.addDataSet(lineDataSet);
//        lineData.addDataSet(lineDataSet);
        mChart.setData(lineData);
    }

    private ArrayList<String> getXAxisShowLable() {
        ArrayList<String> m = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            m.add(i + 1 + "");
        }
        return m;
    }

    @Override
    public void onClick(View v) {
        JSONObject object = new JSONObject();
        long l = System.currentTimeMillis();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yy$MM&dd HH:mm:ss",Locale.CHINA);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
        final String format = dateFormat.format(l);
        Log.e("zz", "当前时间==》" + format);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BaseUrl.ALLURL + "get_all_sense", object.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        SenseBean senseBean = gson.fromJson(jsonObject.toString(), SenseBean.class);
                        if ("S".equals(senseBean.getRESULT())) {
                            int temperature = senseBean.getTemperature();
                            LineData lineData = mChart.getLineData();
//                            OrmDBHelper help = OrmDBHelper.gethelp(getActivity());
//                            try {
//                                help.getSenseDao().delete();
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
                            if(lineData != null){
                                LineDataSet dataSet = lineData.getDataSetByIndex(0);
                                if(dataSet == null){
//                                    dataSet = new LineDataSet();
                                }else {
                                    lineData.addXValue(format);
                                    int entryCount = dataSet.getEntryCount();
                                    lineData.addEntry(new Entry(temperature,entryCount),0);
                                    mChart.setVisibleXRange(20,20);
                                    mChart.notifyDataSetChanged();
                                    mChart.invalidate();
                                    mChart.moveViewToX(lineData.getXValCount()-20);
                                }
                            }
//                            LineDataSet dataSet = lineData.getDataSetByIndex(0);
//                            dataSet.addEntry(new Entry(temperature, index));
//                            List<String> xVals = lineData.getXVals();
//                            xVals.add(format);
//
//                            mChart.notifyDataSetChanged();
//                            mChart.invalidate();
                        } else {
                            Toast.makeText(getActivity(), senseBean.getERRMSG(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, null);
        requestQueue.add(request);
    }

}
