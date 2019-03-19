package com.example.mrzhang.smarttraffic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrzhang.smarttraffic.BaseUrl;
import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.utils.Constant;
import com.example.mrzhang.smarttraffic.utils.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入账号
     */
    private EditText mAccountEdt;
    /**
     * 清输入密码
     */
    private EditText mPwdEdt;
    /**
     * 记住密码
     */
    private CheckBox mRememberPwdCb;
    /**
     * 自动登录
     */
    private CheckBox mSelfLoginCb;
    /**
     * 登录
     */
    private Button mLoginBtn;
    /**
     * 注册
     */
    private Button mRegistBtn;
    private SharedPreferences setting;
    private SharedPreferences.Editor edit;
    private String mAccount;
    private String mPsw;
    private ImageView mTitleIv;
    /**
     * 标题
     */
    private TextView mTitleTv;
    /**
     * 副标题
     */
    private TextView mTitleRightTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        setting = getSharedPreferences("setting", 0);
        edit = setting.edit();

        boolean pwdIsCheck = setting.getBoolean(Constant.SP_PWDISChECK, false);
        mRememberPwdCb.setChecked(pwdIsCheck);
        if(pwdIsCheck) {
            mAccount = setting.getString(Constant.SP_USERNNME, "");
            mPsw = setting.getString(Constant.SP_PASSWORD, "");
            mAccountEdt.setText(mAccount);
            mPwdEdt.setText(mPsw);
        }

        mTitleIv.setBackgroundResource(R.mipmap.setting);
        mTitleTv.setText("登录");
        mTitleRightTv.setText("网络设置");
    }


    private void initView() {
        mAccountEdt = (EditText) findViewById(R.id.account_edt);
        mPwdEdt = (EditText) findViewById(R.id.pwd_edt);
        mRememberPwdCb = (CheckBox) findViewById(R.id.remember_pwd_cb);
        mSelfLoginCb = (CheckBox) findViewById(R.id.self_login_cb);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
        mRegistBtn = (Button) findViewById(R.id.regist_btn);
        mRegistBtn.setOnClickListener(this);
        mTitleIv = (ImageView) findViewById(R.id.title_left_iv);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mTitleRightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_btn:
                mAccount = mAccountEdt.getText().toString().trim();
                mPsw = mPwdEdt.getText().toString().trim();
                if (TextUtils.isEmpty(mAccount) || mAccount.length() < 4) {
                    Toast.makeText(this, "账号不能为空且不能少于四位", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mPsw) || mPsw.length() < 6) {
                    Toast.makeText(this, "密码不能为空且不能少于六位", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JSONObject object = new JSONObject();
                try {
                    object.put("UserName", mAccount);
                    object.put("UserPwd", mPsw);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl.ALLURL+"user_login",
                        object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Thread currentThread = Thread.currentThread();
                        Log.e("zfy", "currentThread==>" + currentThread.toString());
                        if (currentThread == Looper.getMainLooper().getThread()) {
                            Log.e("zfy", "currentThread==>是主线程");
                        }
                        String s = jsonObject.toString();
                        Log.e("zfy", "请求结果==>" + s + "===" + jsonObject);

                        //在这处理请求成功
                        Log.e("zzcg", jsonObject.toString());
                        String result = jsonObject.optString("RESULT");

                        if ("S".equals(result)) {//请求成功且登录成功
                            String userRole = jsonObject.optString("UserRole");

                            edit.putString("userRole", userRole).commit();
//                            SpUtil.putS(LoginActivity.this,"userRole",userRole);

                            boolean checked = mRememberPwdCb.isChecked();
                            edit.putBoolean(Constant.SP_PWDISChECK, checked).commit();
                            if (checked) {
                                edit.putString(Constant.SP_USERNNME, mAccount).commit();
                                edit.putString(Constant.SP_PASSWORD, mPsw).commit();
                            } else {
                                edit.remove(Constant.SP_USERNNME);
                                edit.remove(Constant.SP_PASSWORD);
                            }

                            MyLog.showe(setting.getString(Constant.SP_USERNNME,"")+"=="
                                    +setting.getString(Constant.SP_PASSWORD,"")+""
                            +setting.getBoolean(Constant.SP_PWDISChECK,false));
                            ;

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {//请求成功但是登录失败
                            String errmsg = jsonObject.optString("ERRMSG");
                            Toast.makeText(LoginActivity.this, errmsg, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Thread currentThread = Thread.currentThread();
                        Log.e("zfy", "currentThread=Err=>" + currentThread.toString());
                        if (currentThread == Looper.getMainLooper().getThread()) {
                            Log.e("zfy", "currentThread=Err=>是主线程");
                        }

                        //接口请求失败
                        Log.e("zzsb", volleyError.getMessage());
                        Toast.makeText(LoginActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                requestQueue.add(jsonObjectRequest);

                break;
            case R.id.regist_btn://注册

                break;
            case R.id.title_right_tv://设置网络

                break;

        }
    }
}

