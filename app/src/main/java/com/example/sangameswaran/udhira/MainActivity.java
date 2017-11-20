package com.example.sangameswaran.udhira;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sangameswaran.udhira.Fragments.DonorRegistrationFragment;

public class MainActivity extends AppCompatActivity {
    LinearLayout mapContainerLL;
    RelativeLayout contentMain;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mapContainerLL.setVisibility(View.GONE);
                    contentMain.setVisibility(View.VISIBLE);
                    DonorRegistrationFragment fragment=new DonorRegistrationFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    mapContainerLL.setVisibility(View.VISIBLE);
                    contentMain.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:

                    return true;
                case R.id.admin:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentMain=(RelativeLayout)findViewById(R.id.content_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mapContainerLL=(LinearLayout)findViewById(R.id.mapContinerLL);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
