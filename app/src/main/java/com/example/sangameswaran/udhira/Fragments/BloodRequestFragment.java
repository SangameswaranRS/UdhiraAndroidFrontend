package com.example.sangameswaran.udhira.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.Entities.BloodEntity;
import com.example.sangameswaran.udhira.Entities.BloodGroupApiEntity;
import com.example.sangameswaran.udhira.Entities.BloodRequestEntity;
import com.example.sangameswaran.udhira.R;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 22-11-2017.
 */

public class BloodRequestFragment extends Fragment {
    EditText etPatientName,etPatientAge,etPatientHospital,etReason,etPatientContactNumber,etEmergencyStatus;
    Spinner bloodRequestFragmentBloodSelector;
    Button raiseRequestBtn;
    LinearLayout bloodRequestFragmentContainer;
    RelativeLayout bloodRequestFragmentLoader;
    List<String> bloodGroups;
    Context activityContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.blood_request_fragment,container,false);
        bloodGroups=new ArrayList<>();
        activityContext=getContext();
        etPatientName=(EditText)v.findViewById(R.id.etPatientName);
        etPatientAge=(EditText)v.findViewById(R.id.etPatientAge);
        etPatientHospital=(EditText)v.findViewById(R.id.etPatientHospital);
        etReason=(EditText)v.findViewById(R.id.etReason);
        etPatientContactNumber=(EditText)v.findViewById(R.id.etPatientContactNumber);
        etEmergencyStatus=(EditText)v.findViewById(R.id.etEmergencyStatus);
        bloodRequestFragmentBloodSelector=(Spinner)v.findViewById(R.id.bloodRequestFragmentBloodSelector);
        raiseRequestBtn=(Button)v.findViewById(R.id.raiseRequestBtn);
        bloodRequestFragmentContainer=(LinearLayout)v.findViewById(R.id.bloodRequestFragmentContainer);
        bloodRequestFragmentLoader=(RelativeLayout)v.findViewById(R.id.bloodRequestFragmentLoader);
        bloodRequestFragmentContainer.setVisibility(View.GONE);
        bloodRequestFragmentLoader.setVisibility(View.VISIBLE);
        BloodGroupApiEntity apiEntity=new BloodGroupApiEntity();
        RestClientImplementation.getAllBloodGroupsApi(apiEntity, new BloodGroupApiEntity.UdhiraRestClientInterface() {
            @Override
            public void onGetBloodGroup(BloodGroupApiEntity bloodGroupApiEntity, VolleyError error) {
                for(BloodEntity iterator : bloodGroupApiEntity.getMessage()){
                    bloodGroups.add(iterator.getBloodGroup());
                }
                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(activityContext,android.R.layout.simple_spinner_item,bloodGroups);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bloodRequestFragmentBloodSelector.setAdapter(dataAdapter);
                bloodRequestFragmentContainer.setVisibility(View.VISIBLE);
                bloodRequestFragmentLoader.setVisibility(View.GONE);
            }
        },activityContext);
        raiseRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PatientName,PatientAge,PatientHospital,Reason,PatientContactNumber;
                PatientName=etPatientName.getText().toString();
                PatientAge=etPatientAge.getText().toString();
                PatientHospital=etPatientHospital.getText().toString();
                Reason=etReason.getText().toString();
                SharedPreferences sp=activityContext.getSharedPreferences("loginCredentials",Context.MODE_PRIVATE);
                String userEmailId=sp.getString("userEmailId","NA");
                PatientContactNumber=etPatientContactNumber.getText().toString();
                int bloodId=bloodRequestFragmentBloodSelector.getSelectedItemPosition()+1;
                int age,emergencyStatus,isSatisfied=0;
                try {
                    age=Integer.parseInt(etPatientAge.getText().toString());
                    emergencyStatus=Integer.parseInt(etEmergencyStatus.getText().toString());
                }catch (Exception e){
                    age=0;
                    emergencyStatus=0;
                }
                if(PatientAge.equals("")||PatientName.equals("")||PatientHospital.equals("")||Reason.equals("")||PatientContactNumber.equals("")||bloodId==0||emergencyStatus==0){
                    Toast.makeText(getActivity(),"Enter all the details to raise request",Toast.LENGTH_LONG).show();
                }else {
                    BloodRequestEntity bloodRequestEntity=new BloodRequestEntity(bloodId,age,emergencyStatus,isSatisfied,PatientName,PatientHospital,Reason,PatientContactNumber,userEmailId);
                    RestClientImplementation.postBloodRequestApi(bloodRequestEntity, new BloodRequestEntity.UdhiraRestClientInterface() {
                        @Override
                        public void onRequestBlood(BloodRequestEntity bloodRequestEntity, VolleyError error) {
                            if(error==null){
                                AlertDialog.Builder successAlert=new AlertDialog.Builder(activityContext);
                                successAlert.setCancelable(false).setTitle("Request Raised Successfully").setMessage("Your Request have been raised successfully please wait for the donors t0 contact you").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        BloodRequestFragment fragment=new BloodRequestFragment();
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
