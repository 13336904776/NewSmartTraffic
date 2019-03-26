package com.example.mrzhang.newsmarttraffic.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.adapter.BillManageAdapter;
import com.example.mrzhang.newsmarttraffic.bean.BillManageBean;
import com.example.mrzhang.newsmarttraffic.db.Mydb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 第三题账单管理 充值历史记录页面
 */
public class BillManageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTitleLeftIv;
    /**
     * 标题
     */
    private TextView mTitleTv;
    /**  */
    private TextView mTitleRightTv;
    private Spinner mSpinner;
    /**
     * 查询
     */
    private Button mBtnQurey;
    private RecyclerView mRcv;
    private BillManageAdapter billManageAdapter;
    /**
     * 暂无历史记录
     */
    private TextView mTvTies;
    private LinearLayout mLlItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_manage);
        initView();
        initData();

    }

    private void initData() {
        mTitleTv.setText("账单管理");
        mTitleLeftIv.setBackgroundResource(R.mipmap.ic_menu);
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData() {
        Mydb mydb = new Mydb(this);
        SQLiteDatabase writableDatabase = mydb.getWritableDatabase();
        Cursor cursor = writableDatabase.query("recharge", null, null, null, null, null, null);
        List<BillManageBean> mlist = new ArrayList<BillManageBean>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String operator = cursor.getString(cursor.getColumnIndex("operator"));
                int carId = cursor.getInt(cursor.getColumnIndex("carId"));
                int rechargeAmount = cursor.getInt(cursor.getColumnIndex("rechargeAmount"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                mlist.add(new BillManageBean(operator, carId, rechargeAmount, date));
            }
        }
        cursor.close();
        writableDatabase.close();
        if (mlist != null && mlist.size() > 0) {
            mLlItem.setVisibility(View.VISIBLE);
            mTvTies.setVisibility(View.GONE);
            Collections.sort(mlist, new Comparator<BillManageBean>() {
                @Override
                public int compare(BillManageBean t1, BillManageBean t2) {
                    long l1 = Long.parseLong(t1.getDate());
                    long l2 = Long.parseLong(t2.getDate());
                    long l = l1 - l2;
                    if(mSpinner.getSelectedItem().equals("时间升序")){
                        return (int)l;
                    }else {
                        return -(int) l;
                    }
                }
            });
            billManageAdapter = new BillManageAdapter(this, mlist);
            mRcv.setAdapter(billManageAdapter);
        } else {
            mLlItem.setVisibility(View.GONE);
            mTvTies.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        mTitleLeftIv = (ImageView) findViewById(R.id.title_left_iv);
        mTitleLeftIv.setOnClickListener(this);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mBtnQurey = (Button) findViewById(R.id.btn_qurey);
        mBtnQurey.setOnClickListener(this);
        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mTvTies = (TextView) findViewById(R.id.tv_ties);
        mLlItem = (LinearLayout) findViewById(R.id.ll_item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.title_left_iv:
                finish();
                break;
            case R.id.btn_qurey:
                getData();
                break;
        }
    }
}
