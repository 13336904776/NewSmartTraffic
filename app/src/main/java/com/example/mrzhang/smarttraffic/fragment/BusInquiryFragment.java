package com.example.mrzhang.smarttraffic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrzhang.smarttraffic.R;

/**
 * 公交查询
 */
public class BusInquiryFragment extends BaseFragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BusInquiryFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_inquiry, container, false);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
