package com.example.mrzhang.smarttraffic.activity;

/**
 * Created by Administrator on 2017/6/2.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzhang.smarttraffic.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Fg3_CarPeccancy extends BaseAdapter {

    private List<String> objects = new ArrayList<String>();

    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter_Fg3_CarPeccancy(Context context, List<String> objects) {
        this.context = context;
        this.objects=objects;
//        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
//      convertView = layoutInflater.inflate(R.layout.item_list, null);
       TextView content = (TextView) convertView.findViewById(R.id.tv_content);

       content.setText(objects.get(position));
        return convertView;
    }

    protected class ViewHolder {
        private TextView content;


        public ViewHolder(View view) {
            content = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}

