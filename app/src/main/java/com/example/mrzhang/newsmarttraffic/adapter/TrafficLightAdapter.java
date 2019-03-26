package com.example.mrzhang.newsmarttraffic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.bean.TrafficLightBean;

import java.util.List;
import java.util.zip.Inflater;

/**
 *
 */
public class TrafficLightAdapter extends RecyclerView.Adapter<TrafficLightAdapter.MyViewHolder>{

    Context mContext;
    List<TrafficLightBean> mList;

    public TrafficLightAdapter(Context mContext, List<TrafficLightBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_traffic_light, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.loadId.setText(mList.get(position).getRoadId()+"");
        holder.red.setText(mList.get(position).getRed()+"");
        holder.yellow.setText(mList.get(position).getYellow() +"");
        holder.green.setText(mList.get(position).getGreen()+"");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView loadId;
        TextView red;
        TextView yellow;
        TextView green;
        public MyViewHolder(View itemView) {
            super(itemView);
            loadId = itemView.findViewById(R.id.tv_road_id);
            red = itemView.findViewById(R.id.tv_red_time);
            yellow = itemView.findViewById(R.id.tv_yellow_time);
            green = itemView.findViewById(R.id.tv_green_time);
        }

    }
}
