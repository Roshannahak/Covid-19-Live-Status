package com.android.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.covid19.R;
import com.android.covid19.model.vaccination.Session;

import java.util.List;

public class VaccineCenterAdapter extends RecyclerView.Adapter<VaccineCenterAdapter.VaccineCenterViewholder> {

    Context context;
    List<Session> sessionList;

    public VaccineCenterAdapter(Context context, List<Session> sessionList) {
        this.context = context;
        this.sessionList = sessionList;
    }

    @NonNull
    @Override
    public VaccineCenterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.vaccine_center_view, parent, false);
        return new VaccineCenterViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineCenterViewholder holder, int position) {
        Session session = sessionList.get(position);

        String age = session.getMinAgeLimit().toString()+"+";
        String address = session.getAddress()+", "+session.getDistrictName()+", "+session.getStateName()+", "+session.getPincode().toString();
        int total_capacity = session.getAvailableCapacity();
        int dose1 = session.getAvailableCapacityDose1();
        int dose2 = session.getAvailableCapacityDose2();

        if (total_capacity <= 0){
            holder.booked_status.setVisibility(View.VISIBLE);
        }else{
            holder.booked_status.setVisibility(View.GONE);
        }

        holder.fee_txt.setText(session.getFee());
        holder.fee_type_txt.setText(session.getFeeType());
        holder.name_txt.setText(session.getName());
        holder.address_txt.setText(address);
        holder.vaccine_type_txt.setText(session.getVaccine());
        holder.age_txt.setText(age);
        holder.total_capacity_txt.setText(String.valueOf(total_capacity));
        holder.dose1_txt.setText(String.valueOf(dose1));
        holder.dose2_txt.setText(String.valueOf(dose2));
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public class VaccineCenterViewholder extends RecyclerView.ViewHolder {
        TextView fee_txt,
                fee_type_txt,
                name_txt,
                address_txt,
                vaccine_type_txt,
                age_txt,
                total_capacity_txt,
                dose1_txt,
                dose2_txt;
        ImageView booked_status;

        public VaccineCenterViewholder(@NonNull View itemView) {
            super(itemView);
            fee_txt = itemView.findViewById(R.id.feeTextview);
            fee_type_txt = itemView.findViewById(R.id.feeTypeTextview);
            name_txt = itemView.findViewById(R.id.name_textview);
            address_txt = itemView.findViewById(R.id.address_textview);
            vaccine_type_txt = itemView.findViewById(R.id.vaccineTypeTextview);
            age_txt = itemView.findViewById(R.id.ageTextview);
            total_capacity_txt = itemView.findViewById(R.id.totalCapacityTextview);
            dose1_txt = itemView.findViewById(R.id.dose1Textview);
            dose2_txt = itemView.findViewById(R.id.dose2Textview);

            booked_status = itemView.findViewById(R.id.booked_status_imageview);
        }
    }
}
