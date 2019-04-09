package com.example.mrzhang.newsmarttraffic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mrzhang.newsmarttraffic.R;
import com.example.mrzhang.newsmarttraffic.activity.QrActivity;

public class QrFragment extends BaseFragment implements View.OnClickListener {
    private Spinner sp_carid;
    private EditText edt_money;
    private EditText edt_time;
    private Button btn_generate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        sp_carid = (Spinner) view.findViewById(R.id.sp_carid);
        edt_money = (EditText) view.findViewById(R.id.edt_money);
        edt_time = (EditText) view.findViewById(R.id.edt_time);
        btn_generate = (Button) view.findViewById(R.id.btn_generate);
        btn_generate.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String money = edt_money.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(getContext(), "money不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String time = edt_time.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(getContext(), "time不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String carId = sp_carid.getSelectedItem().toString();
        Intent intent = new Intent(getActivity(), QrActivity.class);
        intent.putExtra("money", money);
        intent.putExtra("time", time);
        intent.putExtra("carId", carId);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_generate:
                submit();
                break;
        }
    }
}
