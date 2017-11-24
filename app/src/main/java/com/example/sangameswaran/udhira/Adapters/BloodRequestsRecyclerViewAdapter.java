package com.example.sangameswaran.udhira.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.udhira.Entities.BloodRequestEntity;
import com.example.sangameswaran.udhira.Entities.GetBloodRequestEntity;
import com.example.sangameswaran.udhira.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 24-11-2017.
 */

public class BloodRequestsRecyclerViewAdapter extends RecyclerView.Adapter<BloodRequestsRecyclerViewAdapter.BloodRequestsRecyclerViewHolder> {
    List<GetBloodRequestEntity> bloodRequests=new ArrayList<>();
    List<String> blood=new ArrayList<>();
    Context context;

    public BloodRequestsRecyclerViewAdapter(List<GetBloodRequestEntity> bloodRequests, List<String> blood, Context context) {
        this.bloodRequests = bloodRequests;
        this.blood = blood;
        this.context = context;
    }

    @Override
    public BloodRequestsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_requests_card_layout,parent,false);
        return new BloodRequestsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BloodRequestsRecyclerViewHolder holder, final int position) {
        if(bloodRequests.get(position).getIsSatisfied()==0){
            holder.StatusTv.setText("NOT SATISFIED");
            holder.StatusTv.setTextColor(Color.parseColor("#dcdc39"));
        }else if(bloodRequests.get(position).getIsSatisfied()==1){
            holder.StatusTv.setText("SATISFIED");
            holder.StatusTv.setTextColor(Color.parseColor("#60a844"));
        }
        holder.te4.setText("Blood group required : "+blood.get((bloodRequests.get(position).getBloodId())-1));
        holder.te2.setText("Patient Name : "+bloodRequests.get(position).getPatientName());
        holder.te3.setText("Hospital : "+bloodRequests.get(position).getHospital());
        holder.te7.setText("Reason : "+bloodRequests.get(position).getReason());
        holder.te8.setText("Contact : "+bloodRequests.get(position).getContactNumber());
        holder.te5.setText("Patient Age : "+bloodRequests.get(position).getAge());
        holder.te6.setText("Emergency Status : "+bloodRequests.get(position).getEmergencyStatus());
        holder.te1.setText("Request Id : "+bloodRequests.get(position).getRequestId());
        holder.te1.setTextColor(Color.BLACK);
        holder.tvCallPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Calling donor please wait",Toast.LENGTH_LONG).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bloodRequests.get(position).getContactNumber()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bloodRequests.size();
    }

    public class BloodRequestsRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView te1,te2,te3,te4,te5,te6,te7,te8;
        TextView StatusTv,tvCallPatient;
        public BloodRequestsRecyclerViewHolder(View itemView) {
            super(itemView);
            StatusTv=(TextView)itemView.findViewById(R.id.tvRequestStatus);
            te1=(TextView) itemView.findViewById(R.id.te1);
            te2=(TextView) itemView.findViewById(R.id.te2);
            te3=(TextView) itemView.findViewById(R.id.te3);
            te4=(TextView) itemView.findViewById(R.id.te4);
            te5=(TextView) itemView.findViewById(R.id.te5);
            tvCallPatient=(TextView)itemView.findViewById(R.id.tvCallPatient);
            te6=(TextView) itemView.findViewById(R.id.te6);
            te7=(TextView) itemView.findViewById(R.id.te7);
            te8=(TextView) itemView.findViewById(R.id.te8);
        }
    }
}
