package com.example.sangameswaran.udhira.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.udhira.Entities.BloodEntity;
import com.example.sangameswaran.udhira.Entities.DonorRegistrationEntity;
import com.example.sangameswaran.udhira.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class DonorInfoRecyclerViewAdapter extends RecyclerView.Adapter<DonorInfoRecyclerViewAdapter.DonorInfoRecyclerViewHolder> {
    private List<DonorRegistrationEntity> donorRegistrationEntities = new ArrayList<>();
    private List<String> bloodEntities = new ArrayList<>();
    private Context context;

    public DonorInfoRecyclerViewAdapter(List<DonorRegistrationEntity> donorRegistrationEntities, List<String> bloodEntities, Context context) {
        this.donorRegistrationEntities = donorRegistrationEntities;
        this.bloodEntities = bloodEntities;
        this.context = context;
    }

    public DonorInfoRecyclerViewAdapter() {
    }

    @Override
    public DonorInfoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_info_card_view, parent, false);
        return new DonorInfoRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DonorInfoRecyclerViewHolder holder, final int position) {
        holder.tv1.setText("Donor Name : " + donorRegistrationEntities.get(position).getDonorName());
        holder.tv2.setText("Donor Email Id : " + donorRegistrationEntities.get(position).getDonorEmailId());
        holder.tv3.setText("Donor Contact Number : " + donorRegistrationEntities.get(position).getDonorContactNumber());
        holder.tv4.setText("Donor Blood Group : " + bloodEntities.get((donorRegistrationEntities.get(position).getBloodGroupId()) - 1));
        holder.tv5.setText("Donor weight : " + donorRegistrationEntities.get(position).getDonorWeight());
        holder.tv6.setText("Donor DOB : " + donorRegistrationEntities.get(position).getDateOfBirth());
        holder.tv7.setText("Address : " + donorRegistrationEntities.get(position).getAddress());
        holder.tv8.setText("Gender : " + donorRegistrationEntities.get(position).getGender());
        holder.tv9.setText("Donation comments : " + donorRegistrationEntities.get(position).getDonationComments());
        holder.tv10.setText("");
        holder.tvCallDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Calling donor please wait",Toast.LENGTH_LONG).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + donorRegistrationEntities.get(position).getDonorContactNumber()));
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
        holder.tvMessageDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Opening messages..",Toast.LENGTH_LONG).show();
                try{
                    Intent messageIntent=new Intent(Intent.ACTION_VIEW);
                    messageIntent.setData(Uri.parse("sms:"+donorRegistrationEntities.get(position).getDonorContactNumber()));
                    context.startActivity(messageIntent);
                }catch (Exception e){
                    Toast.makeText(context,"Error in Sending message",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return donorRegistrationEntities.size();
    }

    public class DonorInfoRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
        TextView tvCallDonor,tvMessageDonor;

        public DonorInfoRecyclerViewHolder(View itemView) {
            super(itemView);
            tvCallDonor=(TextView)itemView.findViewById(R.id.tvCallDonor);
            tvMessageDonor=(TextView)itemView.findViewById(R.id.tvMessageDonor);
            tv1=(TextView)itemView.findViewById(R.id.tv1);
            tv2=(TextView)itemView.findViewById(R.id.tv2);
            tv3=(TextView)itemView.findViewById(R.id.tv3);
            tv4=(TextView)itemView.findViewById(R.id.tv4);
            tv5=(TextView)itemView.findViewById(R.id.tv5);
            tv6=(TextView)itemView.findViewById(R.id.tv6);
            tv7=(TextView)itemView.findViewById(R.id.tv7);
            tv8=(TextView)itemView.findViewById(R.id.tv8);
            tv9=(TextView)itemView.findViewById(R.id.tv9);
            tv10=(TextView)itemView.findViewById(R.id.tv10);
        }
    }
}
