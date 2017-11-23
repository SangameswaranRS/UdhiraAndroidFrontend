package com.example.sangameswaran.udhira.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sangameswaran.udhira.R;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class GetBloodRequestsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.get_blood_request_fragment,container,false);
        return v;
    }
}
