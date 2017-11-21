package com.example.sangameswaran.udhira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.Entities.BloodEntity;
import com.example.sangameswaran.udhira.Entities.BloodGroupApiEntity;
import com.example.sangameswaran.udhira.Entities.SignupEntity;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 21-11-2017.
 */

public class SignupActivity extends AppCompatActivity{
    EditText etUserEmailId,etUserName,etContactNumber,etPassword;
    Button signupBtn;
    Spinner signupBloodSelectionSpinner;
    BloodGroupApiEntity bloodGroupApiEntity;
    List<String> bloodGroups;
    LinearLayout signUpContainerLinearLayout;
    RelativeLayout loader4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bloodGroupApiEntity=new BloodGroupApiEntity();
        bloodGroups=new ArrayList<>();
        etUserEmailId=(EditText)findViewById(R.id.etUserEmailId);
        signUpContainerLinearLayout=(LinearLayout)findViewById(R.id.signUpContainerLinearLayout);
        loader4=(RelativeLayout)findViewById(R.id.loader4);
        signUpContainerLinearLayout.setVisibility(View.GONE);
        loader4.setVisibility(View.VISIBLE);
        etUserName=(EditText)findViewById(R.id.etUserName);
        etContactNumber=(EditText)findViewById(R.id.etUserContactNumber);
        etPassword=(EditText)findViewById(R.id.etSignUpPassword);
        signupBtn=(Button)findViewById(R.id.signUpBtn);
        signupBloodSelectionSpinner=(Spinner)findViewById(R.id.bloogGroupSignupSpinner);
        RestClientImplementation.getAllBloodGroupsApi(bloodGroupApiEntity, new BloodGroupApiEntity.UdhiraRestClientInterface() {
            @Override
            public void onGetBloodGroup(BloodGroupApiEntity bloodGroupApiEntity, VolleyError error) {
                if(error==null){

                    loader4.setVisibility(View.GONE);
                    signUpContainerLinearLayout.setVisibility(View.VISIBLE);
                    for(BloodEntity iterator : bloodGroupApiEntity.getMessage()){
                        bloodGroups.add(iterator.getBloodGroup());
                    }
                    ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(SignupActivity.this,android.R.layout.simple_spinner_item,bloodGroups);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    signupBloodSelectionSpinner.setAdapter(dataAdapter);
                }else {
                    finish();
                }
            }
        },this);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName,userEmailid,userContactNumber,password,bloodGroup,imeiIndex,canRaiseRequestFlag,loginFlag;
                userName=etUserName.getText().toString();
                userEmailid=etUserEmailId.getText().toString();
                userContactNumber=etContactNumber.getText().toString();
                password=etPassword.getText().toString();
                bloodGroup= (String) signupBloodSelectionSpinner.getSelectedItem();
                TelephonyManager tm= (TelephonyManager) SignupActivity.this.getSystemService(TELEPHONY_SERVICE);
                imeiIndex=tm.getDeviceId();
                canRaiseRequestFlag="0";
                loginFlag="1";
                SignupEntity signupEntity=new SignupEntity(userEmailid,userName,userContactNumber,bloodGroup,imeiIndex,password,loginFlag,canRaiseRequestFlag);
                RestClientImplementation.signUpApi(signupEntity, new SignupEntity.UdhiraRestClientInterface() {
                    @Override
                    public void onGettingResponse(final SignupEntity entity, VolleyError error) {
                        if(error==null){
                            AlertDialog.Builder successMessage=new AlertDialog.Builder(SignupActivity.this);
                            successMessage.setCancelable(false).setTitle("Signup Successful").setMessage("Note that you will be authorized to raise request only after a week of activity").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sharedPreferences=getSharedPreferences("loginCredentials",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("userEmailId",entity.getUserEmailId());
                                    editor.putString("password",entity.getPassword());
                                    editor.commit();
                                    dialogInterface.cancel();
                                    Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                        }else {
                            finish();
                        }
                    }
                },SignupActivity.this);

            }
        });

    }
}
