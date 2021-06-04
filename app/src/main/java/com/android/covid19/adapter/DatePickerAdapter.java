package com.android.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.covid19.R;
import com.android.covid19.VaccinationCenters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatePickerAdapter extends RecyclerView.Adapter<DatePickerAdapter.DatePickerViewHolder> {

    Context context;
    List<String> dateList;

    public interface Getdate{
        void onViewClick(int position);
    }
    Getdate getdate;

    public void setItemDateClickListener(Getdate getdate){
        this.getdate = getdate;
    }

    public DatePickerAdapter (Context context, List<String> dateList){
        this.context = context;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public DatePickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.date_view, parent, false);
        return new DatePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatePickerViewHolder holder, int position) {
        Date date = null;
        String formated;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateList.get(position));
            formated = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(date);
            holder.datetxt.setText(formated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class DatePickerViewHolder extends RecyclerView.ViewHolder{

        TextView datetxt;
        RelativeLayout relativeLayout;

        public DatePickerViewHolder(@NonNull View itemView) {
            super(itemView);
            datetxt = itemView.findViewById(R.id.datePickerTextView);
            relativeLayout = itemView.findViewById(R.id.dateviewRelativeLayout);

            relativeLayout.setOnClickListener(v -> getdate.onViewClick(getAdapterPosition()));
        }
    }
}
