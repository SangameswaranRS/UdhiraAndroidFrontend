package com.example.sangameswaran.udhira;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sangameswaran.udhira.Fragments.GetDonorInfoFragment;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class AdminFunctionsActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_functions);
        navigationView= (BottomNavigationView) findViewById(R.id.navigation2);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btmNavBloodRequests:

                        return true;
                    case R.id.btmNavUserInfo:
                        GetDonorInfoFragment fragment=new GetDonorInfoFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main_two,fragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}
