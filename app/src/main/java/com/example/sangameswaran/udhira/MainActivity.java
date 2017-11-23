package com.example.sangameswaran.udhira;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.sangameswaran.udhira.Entities.DonationCentreAPIEntity;
import com.example.sangameswaran.udhira.Entities.DonationCentreEntity;
import com.example.sangameswaran.udhira.Fragments.AdminFragment;
import com.example.sangameswaran.udhira.Fragments.BloodRequestFragment;
import com.example.sangameswaran.udhira.Fragments.DonorRegistrationFragment;
import com.example.sangameswaran.udhira.restAPICalls.RestClientImplementation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    LinearLayout mapContainerLL;
    RelativeLayout contentMain;
    List<DonationCentreEntity> donationCentreEntities;
    public GoogleMap map;
    public List<LatLng> bounds;
    FloatingActionButton fab;

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
                    fab.setVisibility(View.GONE);
                    mapContainerLL.setVisibility(View.GONE);
                    contentMain.setVisibility(View.VISIBLE);
                    DonorRegistrationFragment fragment=new DonorRegistrationFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fab.setVisibility(View.VISIBLE);
                    mapContainerLL.setVisibility(View.VISIBLE);
                    contentMain.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    fab.setVisibility(View.GONE);
                    mapContainerLL.setVisibility(View.GONE);
                    contentMain.setVisibility(View.VISIBLE);
                    BloodRequestFragment fragment1=new BloodRequestFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment1).commit();
                    return true;
                case R.id.admin:
                    fab.setVisibility(View.GONE);
                    mapContainerLL.setVisibility(View.GONE);
                    contentMain.setVisibility(View.VISIBLE);
                    AdminFragment fragment2=new AdminFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment2).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        contentMain=(RelativeLayout)findViewById(R.id.content_main);
        donationCentreEntities=new ArrayList<>();
        bounds=new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mapContainerLL=(LinearLayout)findViewById(R.id.mapContinerLL);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        DonationCentreAPIEntity donationCentreAPIEntity=new DonationCentreAPIEntity();
        RestClientImplementation.getDonationCentresApi(donationCentreAPIEntity, new DonationCentreAPIEntity.UdhiraRestClientInterface() {
            @Override
            public void onGetCentreDetails(DonationCentreAPIEntity donationCentreAPIEntity, VolleyError error) {
                if(error==null){
                    if(donationCentreAPIEntity.getMessage().size()>0){
                        donationCentreEntities=donationCentreAPIEntity.getMessage();
                        plotCentres();
                    }else {
                        Toast.makeText(getApplicationContext(),"No Donation Centres found on database",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeZoom(bounds);
            }
        });
    }

    private void plotCentres() {
        if(map!=null && donationCentreEntities.size()>0){
            for(DonationCentreEntity iterator :donationCentreEntities){
                map.addMarker(new MarkerOptions().position(new LatLng(iterator.getLattitude(),iterator.getLongitude())).title(iterator.getCentreName()));
                bounds.add(new LatLng(iterator.getLattitude(),iterator.getLongitude()));
            }
            relativeZoom(bounds);
        }
    }

    private void relativeZoom(List<LatLng> boundse){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(LatLng iterator: boundse){
            builder.include(iterator);
            LatLngBounds bounds = builder.build();
            int padding = 200;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map.moveCamera(cu);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        plotCentres();
    }
}
