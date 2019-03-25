package com.example.mrzhang.newsmarttraffic.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mrzhang.newsmarttraffic.BaseUrl;
import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.application.MyApp;
import com.example.mrzhang.newsmarttraffic.db.Mydb;
import com.example.mrzhang.newsmarttraffic.utils.Constant;
import com.example.mrzhang.newsmarttraffic.utils.MyToast;
import com.example.mrzhang.newsmarttraffic.utils.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 账户管理
 */
public class AccountManageFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    /**
     * 0元
     */
    private TextView mTvAccountBalance;
    private Spinner mSpCarNum;
    /**
     * 0
     */
    private EditText mEdtRechargeAmount;
    /**
     * 查询
     */
    private Button mBtnQuery;
    /**
     * 充值
     */
    private Button mBtnRecharge;
    private RequestQueue requestQueue;
    private String userName;
    private AlertDialog.Builder mDialog;
    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_management, container, false);

        initView(view);
        initData();
        return view;
    }

    private void initData() {
        requestQueue = MyApp.getRequestQueue();
        userName = SpUtil.getS(getContext(), Constant.SP_USERNNME);
        mDialog = new AlertDialog.Builder(getContext());
        mDialog.setCancelable(false);
        mDialog.setMessage("查询中");
        alertDialog = mDialog.create();

        getCarBalance();
    }

    private void initView(View view) {
        mTvAccountBalance = (TextView) view.findViewById(R.id.tv_account_balance);
        mSpCarNum = (Spinner) view.findViewById(R.id.sp_car_num);
        mEdtRechargeAmount = (EditText) view.findViewById(R.id.edt_recharge_amount);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
        mBtnRecharge = (Button) view.findViewById(R.id.btn_recharge);
        mBtnRecharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_query:
                getCarBalance();

                break;
            case R.id.btn_recharge:
                setCarBalance();
                break;
        }
    }

    private void getCarBalance() {
        JSONObject object = new JSONObject();
        String sCatId = mSpCarNum.getSelectedItem().toString();
        int nCarId = Integer.parseInt(sCatId);
        try {
            object.put("CarId", nCarId);
            object.put("UserName", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mDialog.setMessage("查询中");
        alertDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BaseUrl.ALLURL + "get_car_account_balance", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        alertDialog.dismiss();
                        String result = jsonObject.optString("RESULT");
                        if("S".equals(result)){
                            int balance = jsonObject.optInt("Balance");
                            mTvAccountBalance.setText(balance+"元");
                        }else {
                            String errmsg = jsonObject.optString("ERRMSG");
                            MyToast.show(getContext(),errmsg);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                alertDialog.dismiss();
                MyToast.show(getContext(),volleyError.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setCarBalance() {

        String mRechargeAmount = mEdtRechargeAmount.getText().toString().trim();
        int nRechrgeAmount = 0;
        if(TextUtils.isEmpty(mRechargeAmount)){
            MyToast.show(getContext(),"请输入充值金额");
            return;
        }else {
            nRechrgeAmount = Integer.parseInt(mRechargeAmount);
            if(nRechrgeAmount < 1 || nRechrgeAmount > 999){
                MyToast.show(getContext(),"请输入1-999元的充值金额");
                return;
            }
        }

        String sCatId = mSpCarNum.getSelectedItem().toString();
        final int nCarId = Integer.parseInt(sCatId);
        JSONObject object = new JSONObject();
        try {
            object.put("CarId", nCarId);
            object.put("UserName", userName);
            object.put("Money", nRechrgeAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final int finalNRechrgeAmount = nRechrgeAmount;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BaseUrl.ALLURL + "set_car_account_recharge", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        alertDialog.dismiss();
                        String result = jsonObject.optString("RESULT");
                        if("S".equals(result)){
                            MyToast.show(getContext(),"充值成功");
                            mEdtRechargeAmount.setText("");
                            getCarBalance();
                            //存到数据库中
                            Mydb mydb = new Mydb(getContext());
                            SQLiteDatabase database = mydb.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("operator",userName);
                            contentValues.put("carId",nCarId);
                            contentValues.put("rechargeAmount", finalNRechrgeAmount);
                            long currentTimeMillis = System.currentTimeMillis();
                            contentValues.put("date",currentTimeMillis);
                            database.insert("recharge",null,contentValues);
                        }else {
                            String errmsg = jsonObject.optString("ERRMSG");
                            MyToast.show(getContext(),errmsg);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                alertDialog.dismiss();
                MyToast.show(getContext(),volleyError.getMessage());
            }
        });
        mDialog.setMessage("充值中");
        alertDialog.show();
        requestQueue.add(jsonObjectRequest);
    }
}
