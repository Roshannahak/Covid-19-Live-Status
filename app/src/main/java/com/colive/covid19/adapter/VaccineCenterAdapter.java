package com.colive.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.colive.covid19.R;
import com.colive.covid19.model.vaccination.Session;

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

        RelativeLayout cardBackground;
        TextView dose1_dummy_txt, dose2_dummy_txt, fee_dummy_txt, vaccine_dummy_txt, age_dummy_txt, totaldose_dummy_txt;
        CardView cardView;

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

            cardBackground = itemView.findViewById(R.id.vaccine_card);
            dose1_dummy_txt = itemView.findViewById(R.id.dose1DummyTextview);
            dose2_dummy_txt = itemView.findViewById(R.id.dose2DummyTextview);
            fee_dummy_txt = itemView.findViewById(R.id.feeDummyTextview);
            vaccine_dummy_txt = itemView.findViewById(R.id.vaccineTypeDummyTextview);
            age_dummy_txt = itemView.findViewById(R.id.ageDummyTextview);
            totaldose_dummy_txt = itemView.findViewById(R.id.capacityDummyTextview);
            cardView = itemView.findViewById(R.id.card1);

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                cardBackground.setBackgroundResource(R.drawable.darkcard);
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.button_color));
                fee_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                fee_type_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                name_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                address_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                vaccine_type_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                age_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                total_capacity_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                dose1_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                dose2_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                fee_dummy_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                vaccine_dummy_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                age_dummy_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                totaldose_dummy_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));

                dose1_dummy_txt.setBackgroundColor(context.getResources().getColor(R.color.button_color));
                dose1_dummy_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                dose2_dummy_txt.setBackgroundColor(context.getResources().getColor(R.color.button_color));
                dose2_dummy_txt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
            }
        }
    }
}
