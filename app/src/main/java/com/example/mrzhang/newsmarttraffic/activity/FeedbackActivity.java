package com.example.mrzhang.newsmarttraffic.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrzhang.newsmarttraffic.R;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    // TODO: 2019/3/20 缺接口，我的意见页面和接口
    private ImageView mTitleLeftIv;
    /**
     * 标题
     */
    private TextView mTitleTv;
    /**
     * 副标题
     */
    private TextView mTitleRightTv;
    /**
     * 标题
     */
    private EditText mEdtTitle;
    /**
     * 请输入您的意见
     */
    private EditText mEdtOpinion;
    /**
     * 手机号码
     */
    private EditText mEdtPhone;
    /**
     * 提交
     */
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        mTitleLeftIv.setBackgroundResource(R.mipmap.ic_back);
        mTitleTv.setText("意见反馈");
        mTitleRightTv.setText("我的意见");

    }

    private void initView() {
        mTitleLeftIv = (ImageView) findViewById(R.id.title_left_iv);
        mTitleLeftIv.setOnClickListener(this);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mTitleRightTv.setOnClickListener(this);
        mEdtTitle = (EditText) findViewById(R.id.edt_title);
        mEdtOpinion = (EditText) findViewById(R.id.edt_opinion);
        mEdtPhone = (EditText) findViewById(R.id.edt_phone);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.title_left_iv:
                finish();
                break;
            case R.id.title_right_tv:
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
