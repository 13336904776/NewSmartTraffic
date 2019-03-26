package com.example.mrzhang.newsmarttraffic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.bean.BillManageBean;

import java.text.SimpleDateFormat;
import java.util.List;

public class BillManageAdapter extends RecyclerView.Adapter<BillManageAdapter.MyViewHolder> {

    Context mContext;
    List<BillManageBean> mList;

    public BillManageAdapter(Context mContext, List<BillManageBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bill_record, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_id.setText(position + 1 + "");
        holder.tv_operator.setText(mList.get(position).getOperator()+"");
        holder.tv_carid.setText(mList.get(position).getCarId()+"");
        holder.tv_money.setText(mList.get(position).getRechargeAmount()+"");
        String date = mList.get(position).getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        long l = Long.parseLong(date);
        String format = simpleDateFormat.format(l);
        holder.tv_time.setText(format);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_id;
        TextView tv_carid;
        TextView tv_money;
        TextView tv_operator;
        TextView tv_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_carid = itemView.findViewById(R.id.tv_carid);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_operator = itemView.findViewById(R.id.tv_operator);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
