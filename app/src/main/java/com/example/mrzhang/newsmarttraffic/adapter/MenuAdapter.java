package com.example.mrzhang.newsmarttraffic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.bean.MenuBean;

import java.util.List;

/**
 *
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>{

    Context mContext;
    List<MenuBean> mlist;
    MenuClickListen menuClickListen;

    public MenuClickListen getMenuClickListen() {
        return menuClickListen;
    }

    public void setMenuClickListen(MenuClickListen menuClickListen) {
        this.menuClickListen = menuClickListen;
    }

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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.imageView.setBackgroundResource(mlist.get(position).getIv());
        holder.textView.setText(mlist.get(position).getTitle());
        holder.item_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuClickListen.Click(position,mlist.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView ;
        LinearLayout item_menu ;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_iv);
            textView = itemView.findViewById(R.id.menu_tv);
            item_menu = itemView.findViewById(R.id.item_menu);
        }
    }

    public interface MenuClickListen{
        void Click(int postion,String title);
    }

}
