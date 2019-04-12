package com.example.mrzhang.newsmarttraffic.fragment.realtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.bean.SenseBean;
import com.example.mrzhang.newsmarttraffic.db.OrmDBHelper;
import com.example.mrzhang.newsmarttraffic.enent.MessageEvent;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends RealTimeBaseFragment {

    private TextView tv_title;
    private BarChart mChart;
    private int index;
    private RequestQueue requestQueue;
    private Dao<SenseBean, Integer> senseDao;
    private List<SenseBean> senseBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.item_real_time2, container, false);
        initView(inflate);
        tv_title.setText("湿度--BarChart");
        initChart();

        return inflate;
    }

    private void initView(View inflate) {
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        mChart = (BarChart) inflate.findViewById(R.id.chart1);
    }

    private void initChart() {

        mChart.setTouchEnabled(false);
        //设置图表的描述文字，会显示在图表的右下角
        mChart.setDescription("");
        //4种动画
//        mChart.animateX(3000);
        mChart.animateY(3000);
//        mChart.animateXY(3000, 3000);
//        mChart.animateY(3000, Easing.EasingOption.EaseInElastic );
//        mChart.setVisibleXRangeMinimum(20);
        //X轴
        XAxis xAxis = mChart.getXAxis();
        //设置XAxis出现的位置。
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
//        xAxis.setDrawAxisLine(true);
        //如果启用，chart 绘图区后面的背景矩形(网格线)将绘制。
        xAxis.setDrawGridLines(false);
        //设置为true，则绘制该行旁边的轴线（既X轴线 又叫axis-line）。默认为true
//        xAxis.setDrawAxisLine(true);
        ///设置为true，则绘制轴的标签,这里就是下边的时间，默认为true
//        xAxis.setDrawLabels(true);
        //设置标签字符间的空隙，默认characters间隔是4
        xAxis.setSpaceBetweenLabels(1);

        //得到左侧的轴
        YAxis axisLeft = mChart.getAxisLeft();
        //设置左侧文字显示的位置  OUTSIDE_CHART显示在表格外边（左边）,INSIDE_CHART相反
        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //设置左侧轴是否可用 true可用
        axisLeft.setDrawLabels(true);

        //得到右侧的轴
        YAxis axisRight = mChart.getAxisRight();
        axisRight.setDrawLabels(false);

        //默认在左下角的标签，这里指折线图左下角默认的linedata文字
        Legend legend = mChart.getLegend();
        legend.setEnabled(true);
        //自定义，此处只有一个dataset，所以数组中只有一个数据，分别代表颜色和文字
//        legend.setCustom(new int[]{0x33333333}, new String[]{"℃"});
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        //设置形状的大小,0为不显示
        legend.setFormSize(0);
        //设置形状
//        legend.setForm();
        legend.setTextSize(15f);


        OrmDBHelper dbHelper = OrmDBHelper.gethelp(getActivity());
        try {
            senseDao = dbHelper.getSenseDao();
            senseBeans = senseDao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //一个坐标数组
        ArrayList<BarEntry> entries = new ArrayList<>();
        //X轴数组
        ArrayList<String> Xlist = new ArrayList<>();
        if (senseBeans != null && senseBeans.size() > 0) {
            entries.clear();
            Xlist.clear();
            if (senseBeans.size() > 20) {
                for (int i = senseBeans.size() - 20, j = 0; i < senseBeans.size(); i++, j++) {
                    entries.add(new BarEntry(senseBeans.get(i).getHumidity(), j));
                    Xlist.add(senseBeans.get(i).getMs());
                }
            } else {
                for (int i = 0; i < senseBeans.size(); i++) {
                    entries.add(new BarEntry(senseBeans.get(i).getHumidity(), i));
                    Xlist.add(senseBeans.get(i).getMs());
                }
            }
        }
        //把坐标数组转换成折线，LineDataSet就代表那条折现
        BarDataSet barDataSet = new BarDataSet(entries, "bardata");

        //柱形图的颜色
//        barDataSet.setColor(0xff999999);
        // 是否在点上绘制Value，默认为true绘制
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextColor(0xff333333);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(Xlist, barDataSet);

//        lineData.addDataSet(lineDataSet);
        mChart.setData(barData);

    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if ("humidity".equals(event.getFrom())) {
            //  通过eventbus得到湿度
            int humidity = event.getValue();
            String format = event.getDate();

            BarData barData = mChart.getBarData();
            if (barData != null) {
                BarDataSet dataSet = barData.getDataSetByIndex(0);
                if (dataSet != null) {
                    //dataset里边Entry（数据）的数量
                    int entryCount = dataSet.getEntryCount();

                    //X轴添加一个最新的数据
                    barData.addXValue(format);

                    barData.addEntry(new BarEntry(humidity, entryCount), 0);
                    //设置最大最小可见绘制的 chart count 的数量。只在dataSet的 setDrawValues() 设置为 true 时有效。
                    mChart.setVisibleXRange(20, 20);
                    //让chart知道它依赖的基础数据已经改变，并执行所有必要的重新计算
                    // （比如偏移量，legend，最大值，最小值 …）。在动态添加数据时需要用到。
                    mChart.notifyDataSetChanged();
                    //在chart中调用会使其刷新重绘,刷新图表之前 必须调用 notifyDataSetChanged()
                    mChart.invalidate();
                    if (barData.getXValCount() > 20) {
                        //moveViewTo(...) 方法会自动调用 invalidate()
                        mChart.moveViewToX(barData.getXValCount() - 20);
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
