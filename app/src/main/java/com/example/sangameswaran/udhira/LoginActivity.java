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
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.Entities.LoginEntity;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class LoginActivity extends AppCompatActivity {
    LinearLayout containerll,progressLL;
    EditText etLoginUserName;
    EditText etLoginPassword;
    TextView tvLoginButton;
    TextView tvLoaderMessage;
    AlertDialog.Builder permissionChecker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        askRequiredPermissionsForApplication();
        containerll=(LinearLayout)findViewById(R.id.containerll);
        etLoginUserName=(EditText)findViewById(R.id.etLoginUserName);
        etLoginPassword=(EditText)findViewById(R.id.etLoginPassword);
        tvLoginButton=(TextView) findViewById(R.id.tvLoginButton);
        progressLL=(LinearLayout)findViewById(R.id.progressLL);
        tvLoaderMessage=(TextView)findViewById(R.id.tvLoginLoaderMessage);
        containerll.setVisibility(View.GONE);
        tvLoginButton.setVisibility(View.GONE);
        progressLL.setVisibility(View.VISIBLE);
        tvLoaderMessage.setText("Checking local storage...");
        tvLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmailId=etLoginUserName.getText().toString();
                String password=etLoginPassword.getText().toString();
                TelephonyManager tm= (TelephonyManager) LoginActivity.this.getSystemService(TELEPHONY_SERVICE);
                String actualImei=tm.getDeviceId();
                containerll.setVisibility(View.GONE);
                tvLoginButton.setVisibility(View.GONE);
                progressLL.setVisibility(View.VISIBLE);
                tvLoaderMessage.setText("Getting response from the server...");
                SharedPreferences sharedPreferences=getSharedPreferences("loginCredentials",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("userEmailId",userEmailId);
                editor.putString("password",password);
                editor.commit();
                LoginEntity loginEntity=new LoginEntity(userEmailId,password,actualImei);
                RestClientImplementation.loginApi(loginEntity, new LoginEntity.UdhiraRestClientInterface() {
                    @Override
                    public void onLoginInfoSubmit(LoginEntity loginEntity, VolleyError error) {
                        if(error==null){
                            Toast.makeText(LoginActivity.this,loginEntity.getImeiIndex(),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,loginEntity.getImeiIndex(),Toast.LENGTH_LONG).show();
                            progressLL.setVisibility(View.GONE);
                            containerll.setVisibility(View.VISIBLE);
                            tvLoginButton.setVisibility(View.VISIBLE);
                            etLoginUserName.setText(loginEntity.getUserEmailId());
                            etLoginPassword.setText(loginEntity.getPassword());
                        }
                    }
                },LoginActivity.this);

            }
        });
        if(checkSharedPreferenceForCredential()){
            loginAction();
        }else {
            progressLL.setVisibility(View.GONE);
            containerll.setVisibility(View.VISIBLE);
            tvLoginButton.setVisibility(View.VISIBLE);
        }
    }

    private void loginAction() {
        tvLoaderMessage.setText("Getting response from the server...");
        SharedPreferences sp=getSharedPreferences("loginCredentials",MODE_PRIVATE);
        String userEmailId=sp.getString("userEmailId","NA");
        String password = sp.getString("password","NA");
        TelephonyManager tm= (TelephonyManager) LoginActivity.this.getSystemService(TELEPHONY_SERVICE);
        String actualImei=tm.getDeviceId();
        LoginEntity loginEntity=new LoginEntity(userEmailId,password,actualImei);
        RestClientImplementation.loginApi(loginEntity, new LoginEntity.UdhiraRestClientInterface() {
            @Override
            public void onLoginInfoSubmit(LoginEntity loginEntity, VolleyError error) {
                if(error==null){
                    Toast.makeText(LoginActivity.this,loginEntity.getImeiIndex(),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,loginEntity.getImeiIndex(),Toast.LENGTH_LONG).show();
                    progressLL.setVisibility(View.GONE);
                    containerll.setVisibility(View.VISIBLE);
                    tvLoginButton.setVisibility(View.VISIBLE);
                    etLoginUserName.setText(loginEntity.getUserEmailId());
                    etLoginPassword.setText(loginEntity.getPassword());
                }
            }
        },LoginActivity.this);

    }

    private boolean checkSharedPreferenceForCredential() {
        SharedPreferences sp=getSharedPreferences("loginCredentials",MODE_PRIVATE);
        String userEmailId=sp.getString("userEmailId","NA");
        String password = sp.getString("password","NA");
        if(userEmailId.equals("NA")||password.equals("NA")){
            return false;
        }else {
            return true;
        }

    }

    public boolean askRequiredPermissionsForApplication()
    {
        Dexter.withActivity(this).withPermissions(android.Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.CALL_PHONE,android.Manifest.permission.SEND_SMS).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                }
                else {
                    permissionChecker=new AlertDialog.Builder(LoginActivity.this);
                    permissionChecker.setTitle("Permission check Error").setMessage("Enable All permissions to use application").setCancelable(false).setPositiveButton("CLOSE APP", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoginActivity.this.finishAffinity();
                        }
                    }).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(),"DexterError",Toast.LENGTH_LONG).show();
            }
        }).onSameThread().check();
        /*askForPermission(android.Manifest.permission.READ_PHONE_STATE,1);
        askForPermission(android.Manifest.permission.CALL_PHONE,2);
        askForPermission(android.Manifest.permission.SEND_SMS,3);
        if((ContextCompat.checkSelfPermission(LoginActivity.this,android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(getApplicationContext(),"PermissionRequired For Using this App",Toast.LENGTH_LONG).show();
            LoginActivity.this.finishAffinity();
        }
        if((ContextCompat.checkSelfPermission(LoginActivity.this,android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(getApplicationContext(),"PermissionRequired For Using this App",Toast.LENGTH_LONG).show();
            LoginActivity.this.finishAffinity();
        }
        if((ContextCompat.checkSelfPermission(LoginActivity.this,android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(getApplicationContext(),"PermissionRequired For Using this App",Toast.LENGTH_LONG).show();
            LoginActivity.this.finishAffinity();
        }
*/
        return true;
    }

}
