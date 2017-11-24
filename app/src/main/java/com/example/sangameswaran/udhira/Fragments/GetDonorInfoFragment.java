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

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.Adapters.DonorInfoRecyclerViewAdapter;
import com.example.sangameswaran.udhira.Entities.AuthPostParamEntity;
import com.example.sangameswaran.udhira.Entities.BloodEntity;
import com.example.sangameswaran.udhira.Entities.BloodGroupApiEntity;
import com.example.sangameswaran.udhira.Entities.DonorRegistrationEntity;
import com.example.sangameswaran.udhira.Entities.GetAllDonorInfoApiEntity;
import com.example.sangameswaran.udhira.R;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class GetDonorInfoFragment extends Fragment {
    RecyclerView donorInfoRecyclerView;
    RecyclerView.LayoutManager manager;
    DonorInfoRecyclerViewAdapter adapter;
    List<DonorRegistrationEntity> donors;
    List<String> blood;
    RelativeLayout loader5;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.get_donor_info_fragment,container,false);
        donorInfoRecyclerView=(RecyclerView)v.findViewById(R.id.donorInfoRecyclerView);
        loader5=(RelativeLayout)v.findViewById(R.id.loader5);
        loader5.setVisibility(View.VISIBLE);
        manager=new LinearLayoutManager(getActivity());
        donors=new ArrayList<>();
        blood=new ArrayList<>();
        BloodGroupApiEntity apiEntity=new BloodGroupApiEntity();
        RestClientImplementation.getAllBloodGroupsApi(apiEntity, new BloodGroupApiEntity.UdhiraRestClientInterface() {
            @Override
            public void onGetBloodGroup(BloodGroupApiEntity bloodGroupApiEntity, VolleyError error) {
                if(error==null){
                    for(BloodEntity iterator : bloodGroupApiEntity.getMessage()){
                        blood.add(iterator.getBloodGroup());
                    }
                    SharedPreferences sp=getActivity().getSharedPreferences("adminCredentials", Context.MODE_PRIVATE);
                    String userName=sp.getString("userName","NA");
                    String password=sp.getString("password","NA");
                    AuthPostParamEntity authPostParamEntity=new AuthPostParamEntity(userName,password);
                    RestClientImplementation.getAllDonorInfoApi(authPostParamEntity, new GetAllDonorInfoApiEntity.UdhiraRestClientInterface() {
                        @Override
                        public void onGetAllDonorInfo(GetAllDonorInfoApiEntity apiEntity, VolleyError error) {
                            if(error==null){
                                loader5.setVisibility(View.GONE);
                                donors=apiEntity.getMessage();
                                adapter=new DonorInfoRecyclerViewAdapter(donors,blood,getContext());
                                donorInfoRecyclerView.setAdapter(adapter);
                                donorInfoRecyclerView.setLayoutManager(manager);
                                adapter.notifyDataSetChanged();
                            }else {
                                loader5.setVisibility(View.GONE);
                            }
                        }
                    },getContext());
                }
            }
        },getContext());
        return v;
    }
}
