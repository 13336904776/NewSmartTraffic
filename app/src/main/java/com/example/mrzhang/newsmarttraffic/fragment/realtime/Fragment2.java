package com.example.mrzhang.newsmarttraffic.fragment.realtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.bean.SenseBean;
import com.example.mrzhang.newsmarttraffic.db.OrmDBHelper;
import com.example.mrzhang.newsmarttraffic.enent.MessageEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends RealTimeBaseFragment {

    private TextView tv_title;
    private LineChart mChart;
    private int index;
    private RequestQueue requestQueue;
    private Dao<SenseBean, Integer> senseDao;
    private List<SenseBean> senseBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.item_real_time, container, false);
        initView(inflate);
        tv_title.setText("湿度");
        initChart();

        return inflate;
    }

    private void initView(View inflate) {
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        mChart = (LineChart) inflate.findViewById(R.id.chart1);
    }

    private void initChart() {
        mChart.setVisibleXRange(20, 20);
        mChart.setTouchEnabled(false);
        mChart.setDescription("");
//        mChart.setVisibleXRangeMinimum(20);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
//        xAxis.setDrawAxisLine(true);
        //是否有网格线
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        //得到左侧的轴
        YAxis axisLeft = mChart.getAxisLeft();
        //设置左侧文字显示的位置  OUTSIDE_CHART显示在表格外边（左边）
        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //设置左侧轴是否可用 true可用
        axisLeft.setDrawLabels(true);

        //得到右侧的轴
        YAxis axisRight = mChart.getAxisRight();
        axisRight.setDrawLabels(false);

        OrmDBHelper dbHelper = OrmDBHelper.gethelp(getActivity());
        try {
            senseDao = dbHelper.getSenseDao();
            senseBeans = senseDao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (senseBeans != null && senseBeans.size() > 0) {
            for (int i = 0; i < senseBeans.size(); i++) {
                Log.e("zz", senseBeans.get(i).toString());
            }
        }

        //一个坐标数组
        ArrayList<Entry> entries = new ArrayList<>();
        //X轴数组
        ArrayList<String> Xlist = new ArrayList<>();
        if (senseBeans != null && senseBeans.size() > 0) {
            if (senseBeans.size() > 20) {
                for (int i = senseBeans.size() - 20, j = 0; i < senseBeans.size(); i++, j++) {
                    entries.add(new Entry(senseBeans.get(i).getTemperature(), j));
                    Xlist.add(senseBeans.get(i).getMs());
                }
            } else {
                for (int i = 0; i < senseBeans.size(); i++) {
                    entries.add(new Entry(senseBeans.get(i).getTemperature(), i));
                    Xlist.add(senseBeans.get(i).getMs());
                }
            }
        }
        //把坐标数组转换成表格能识别的坐标轴
        LineDataSet lineDataSet = new LineDataSet(entries, "linedata");
        lineDataSet.setCircleColor(0xff999999);
        lineDataSet.setColor(0xff999999);

//        ArrayList<LineDataSet> arrayList = new ArrayList<LineDataSet>();
//        arrayList.add(lineDataSet);

        LineData lineData = new LineData(Xlist, lineDataSet);
//        lineData.addDataSet(lineDataSet);
        mChart.setData(lineData);
    }


    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if ("humidity".equals(event.getFrom())) {
//            Toast.makeText(getActivity(), event.getDate(), Toast.LENGTH_SHORT).show();
            int humidity = event.getValue();
            String format = event.getDate();

            LineData lineData = mChart.getLineData();
            if (lineData != null) {
                LineDataSet dataSet = lineData.getDataSetByIndex(0);
                if (dataSet != null) {
                    lineData.addXValue(format);
                    int entryCount = dataSet.getEntryCount();
                    lineData.addEntry(new Entry(humidity, entryCount), 0);
                    mChart.setVisibleXRange(20, 20);
                    mChart.notifyDataSetChanged();
                    mChart.invalidate();
                    if (lineData.getXValCount() > 20) {
                        mChart.moveViewToX(lineData.getXValCount() - 20);
                    }
                }
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
