package com.example.mrzhang.newsmarttraffic.fragment.realtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends RealTimeBaseFragment {

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
        tv_title.setText("温度");
        initChart();

        return inflate;
    }

    private void initView(View inflate) {
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        mChart = (LineChart) inflate.findViewById(R.id.chart1);
    }

    private void initChart() {
        //设置最大最小可见绘制的 chart count 的数量。 文档显示只在 setDrawValues() 设置为 true 时有效。
        mChart.setVisibleXRange(20, 20);
        mChart.setTouchEnabled(false);
        //设置图表的描述文字，会显示在图表的右下角
        mChart.setDescription("");
//        mChart.setVisibleXRangeMinimum(20);
        //X轴
        XAxis xAxis = mChart.getXAxis();
        //设置XAxis出现的位置。
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
//        xAxis.setDrawAxisLine(true);
        //如果启用，chart 绘图区后面的背景矩形(网格线)将绘制。
        xAxis.setDrawGridLines(false);
        //设置为true，则绘制该行旁边的轴线（既X轴线 又叫axis-line）。默认为true，所以可以不写
//        xAxis.setDrawAxisLine(true);
        ///设置为true，则绘制轴的标签,这里就是下边的时间，默认为true，所以可以不写
//        xAxis.setDrawLabels(true);
        //setSpaceBetweenLabels(int characters) : 设置标签字符间的空隙，默认characters间隔是4
//        xAxis.setSpaceBetweenLabels(1);
        

        //得到左侧的轴
        YAxis axisLeft = mChart.getAxisLeft();
        //设置左侧文字显示的位置  OUTSIDE_CHART显示在表格外边（左边）,INSIDE_CHART相反
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
        if ("temperature".equals(event.getFrom())) {
//            Toast.makeText(getActivity(), event.getDate(), Toast.LENGTH_SHORT).show();
            int temperature = event.getValue();
            String format = event.getDate();

            LineData lineData = mChart.getLineData();
            if (lineData != null) {
                LineDataSet dataSet = lineData.getDataSetByIndex(0);
                if (dataSet != null) {
                    lineData.addXValue(format);
                    int entryCount = dataSet.getEntryCount();
                    lineData.addEntry(new Entry(temperature, entryCount), 0);
                    mChart.setVisibleXRange(20, 20);
                    //让chart知道它依赖的基础数据已经改变，并执行所有必要的重新计算
                    // （比如偏移量，legend，最大值，最小值 …）。在动态添加数据时需要用到。
                    mChart.notifyDataSetChanged();
                    //在chart中调用会使其刷新重绘
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
