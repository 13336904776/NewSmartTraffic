package com.example.mrzhang.smarttraffic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrzhang.smarttraffic.bean.MenuBean;

import java.util.List;

/**
 *
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>{

    Context mContext;
    List<MenuBean> mlist;

    public MenuAdapter(Context mContext, List<MenuBean> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_menu,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView.setBackgroundResource(mlist.get(position).getIv());
        holder.textView.setText(mlist.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView ;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_iv);
            textView = itemView.findViewById(R.id.menu_tv);
        }
    }

}
