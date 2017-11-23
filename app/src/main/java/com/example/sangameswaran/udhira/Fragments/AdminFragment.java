package com.example.sangameswaran.udhira.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.AdminFunctionsActivity;
import com.example.sangameswaran.udhira.Entities.AuthPostParamEntity;
import com.example.sangameswaran.udhira.R;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class AdminFragment  extends Fragment{
    Button adminLoginButton;
    ProgressBar requestLoginProgressBar;
    EditText etadminUserName;
    EditText etadminPassword;
    Context activityContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.admin_fragment,container,false);
        adminLoginButton=(Button)v.findViewById(R.id.adminLoginBtn);
        requestLoginProgressBar=(ProgressBar)v.findViewById(R.id.requestLoginProgressBar);
        etadminUserName=(EditText)v.findViewById(R.id.etadminUserName);
        etadminPassword=(EditText)v.findViewById(R.id.etadminPassword);
        activityContext=getContext();
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLoginProgressBar.setVisibility(View.VISIBLE);
                final String userName=etadminUserName.getText().toString();
                final String password=etadminPassword.getText().toString();
                if(userName.equals("")||password.equals("")){
                    Toast.makeText(activityContext,"Username or password cannot be empty",Toast.LENGTH_LONG).show();
                    requestLoginProgressBar.setVisibility(View.GONE);
                }else {
                AuthPostParamEntity postParamEntity=new AuthPostParamEntity(userName,password);
                RestClientImplementation.postAuthEntityForVerification(postParamEntity, new AuthPostParamEntity.UdhiraRestClientInterface() {
                    @Override
                    public void postAuthParams(AuthPostParamEntity authPostParamEntity, VolleyError error) {
                        if(error==null){
                            requestLoginProgressBar.setVisibility(View.GONE);
                            SharedPreferences sp=getActivity().getSharedPreferences("adminCredentials",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("userName",userName);
                            editor.putString("password",password);
                            editor.commit();
                            Intent intent=new Intent(getActivity(), AdminFunctionsActivity.class);
                            startActivity(intent);
                        }else {
                            requestLoginProgressBar.setVisibility(View.GONE);
                            }
                        }
                    },activityContext);
                }
            }
        });
        return v;
    }
}
