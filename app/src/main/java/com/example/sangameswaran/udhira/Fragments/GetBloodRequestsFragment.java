package com.example.sangameswaran.udhira.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.Adapters.BloodRequestsRecyclerViewAdapter;
import com.example.sangameswaran.udhira.Entities.AuthPostParamEntity;
import com.example.sangameswaran.udhira.Entities.BloodEntity;
import com.example.sangameswaran.udhira.Entities.BloodGroupApiEntity;
import com.example.sangameswaran.udhira.Entities.GetAllBloodRequestsApiEntity;
import com.example.sangameswaran.udhira.Entities.GetBloodRequestEntity;
import com.example.sangameswaran.udhira.R;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class GetBloodRequestsFragment extends Fragment {
    public RecyclerView rvBloodRequest;
    RecyclerView.LayoutManager manager;
    BloodRequestsRecyclerViewAdapter adapter;
    List<String> bloodGroups;
    public RelativeLayout loader6;
    List<GetBloodRequestEntity> bloodRequests;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.get_blood_request_fragment,container,false);
        rvBloodRequest=(RecyclerView)v.findViewById(R.id.rvBloodRequest);
        manager=new LinearLayoutManager(getContext());
        loader6=(RelativeLayout)v.findViewById(R.id.loader6);
        bloodRequests=new ArrayList<>();
        bloodGroups=new ArrayList<>();
        BloodGroupApiEntity apiEntity=new BloodGroupApiEntity();
        RestClientImplementation.getAllBloodGroupsApi(apiEntity, new BloodGroupApiEntity.UdhiraRestClientInterface() {
            @Override
            public void onGetBloodGroup(BloodGroupApiEntity bloodGroupApiEntity, VolleyError error) {
                if(error==null){
                for(BloodEntity iterator : bloodGroupApiEntity.getMessage()){
                    bloodGroups.add(iterator.getBloodGroup());
                }
                SharedPreferences sp=getActivity().getSharedPreferences("adminCredentials", Context.MODE_PRIVATE);
                String userName=sp.getString("userName","NA");
                String password=sp.getString("password","NA");
                AuthPostParamEntity authPostParamEntity=new AuthPostParamEntity(userName,password);
                RestClientImplementation.getAllBloodRequestsApi(authPostParamEntity, new GetAllBloodRequestsApiEntity.UdhiraRestClientInterface() {
                    @Override
                    public void onGettingAllBloodRequests(GetAllBloodRequestsApiEntity getAllBloodRequestsApiEntity, VolleyError error) {
                        if(error==null){
                            loader6.setVisibility(View.GONE);
                            bloodRequests=getAllBloodRequestsApiEntity.getMessage();
                            adapter=new BloodRequestsRecyclerViewAdapter(bloodRequests,bloodGroups,getActivity(),GetBloodRequestsFragment.this);
                            rvBloodRequest.setLayoutManager(manager);
                            rvBloodRequest.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else {
                            loader6.setVisibility(View.GONE);
                        }
                    }
                },getContext());
                }else {
                    loader6.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Server error try again",Toast.LENGTH_LONG).show();
                }
            }

        },getContext());

        return v;
    }

    public void scrollToPosition(int position){
        rvBloodRequest.scrollToPosition(position);
    }
}
