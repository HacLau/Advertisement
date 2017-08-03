package com.qc.advertisement.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qc.advertisement.R;


/**
 * Created by Administrator on 2017/6/20.
 */
public class FabricationFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_fabrication, container, false);
    }
}
