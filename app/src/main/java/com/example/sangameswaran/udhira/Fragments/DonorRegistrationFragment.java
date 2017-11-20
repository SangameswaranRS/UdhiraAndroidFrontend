package com.example.sangameswaran.udhira.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.Entities.BloodEntity;
import com.example.sangameswaran.udhira.Entities.BloodGroupApiEntity;
import com.example.sangameswaran.udhira.Entities.DonorRegistrationEntity;
import com.example.sangameswaran.udhira.R;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class DonorRegistrationFragment extends Fragment {
    EditText etDonorEmailId,etDonorContactNumber,etDonorWeight,etDateOfBirth,etDonationComments,etAddress;
    Spinner bloodGroupSelector;
    RadioGroup genderSelector;
    String gender="";
    Button registerBtn;
    Context activityContext;
    RelativeLayout loader2;
    LinearLayout containerLinear;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.donor_registration_fragment,container,false);
        etDonorEmailId=(EditText)v.findViewById(R.id.etDonorEmailId);
        etDonorContactNumber=(EditText)v.findViewById(R.id.etDonorContactNumber);
        etDonorWeight=(EditText)v.findViewById(R.id.etDonorWeight);
        etDateOfBirth=(EditText)v.findViewById(R.id.etDateOfBirth);
        loader2=(RelativeLayout)v.findViewById(R.id.loader2);
        etAddress=(EditText)v.findViewById(R.id.etDonorAddress);
        containerLinear=(LinearLayout)v.findViewById(R.id.containerLinear);
        loader2.setVisibility(View.VISIBLE);
        containerLinear.setVisibility(View.GONE);
        activityContext=getContext();
        etDonationComments=(EditText)v.findViewById(R.id.etDonationComments);
        bloodGroupSelector=(Spinner)v.findViewById(R.id.bloodGroupSpinner);
        genderSelector=(RadioGroup)v.findViewById(R.id.genderRadioGroup);
        registerBtn=(Button)v.findViewById(R.id.registerBtn);
        BloodGroupApiEntity apiEntity=new BloodGroupApiEntity();
        RestClientImplementation.getAllBloodGroupsApi(apiEntity, new BloodGroupApiEntity.UdhiraRestClientInterface() {
            @Override
            public void onGetBloodGroup(BloodGroupApiEntity bloodGroupApiEntity, VolleyError error) {
                ArrayList<String> bloodGroups=new ArrayList<String>();
                for(BloodEntity entity:bloodGroupApiEntity.getMessage()){
                    bloodGroups.add(entity.getBloodGroup());
                }
                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(activityContext,android.R.layout.simple_spinner_item,bloodGroups);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bloodGroupSelector.setAdapter(dataAdapter);
                containerLinear.setVisibility(View.VISIBLE);
                loader2.setVisibility(View.GONE);
            }
        },getContext());

        genderSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.maleRadioBtn){
                    gender="Male";
                }else if(i==R.id.femaleRadioBtn){
                    gender="Female";
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=activityContext.getSharedPreferences("loginCredentials",Context.MODE_PRIVATE);
                String userEmailId=sp.getString("userEmailId","NA");
                String donorEmailId=etDonorEmailId.getText().toString();
                String donorContactNumber=etDonorContactNumber.getText().toString();
                String dateOfBirth=etDateOfBirth.getText().toString();
                String donorWeight=etDonorWeight.getText().toString();
                String donationComments=etDonationComments.getText().toString();
                String donorAddress=etAddress.getText().toString();
                int bloodGroupId=bloodGroupSelector.getSelectedItemPosition();
                bloodGroupId++;
                if(userEmailId.equals("")||donorEmailId.equals("")||donorContactNumber.equals("")||dateOfBirth.equals("")||donorWeight.equals("")||donationComments.equals("")||donorAddress.equals("")||gender.equals("")){
                    Toast.makeText(activityContext,"Enter All the details",Toast.LENGTH_LONG).show();
                }else {
                    DonorRegistrationEntity entity = new DonorRegistrationEntity(userEmailId, donorEmailId, donorContactNumber, gender, donorWeight, dateOfBirth, donorAddress, donationComments, bloodGroupId);
                    RestClientImplementation.postDonorInfoApi(entity, new DonorRegistrationEntity.UdhiraRestClientInterface() {
                        @Override
                        public void onSubmitDonorDetails(DonorRegistrationEntity donorRegistrationEntity, VolleyError error) {
                            if(error==null){
                                AlertDialog.Builder successResponse=new AlertDialog.Builder(activityContext);
                                successResponse.setTitle("Donor Registered").setMessage("Donor Info Successfully registered").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        DonorRegistrationFragment fragment=new DonorRegistrationFragment();
                                        getFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
                                    }
                                }).show();
                            }
                        }
                    },activityContext);
                }
            }
        });
        return v;
    }
}
