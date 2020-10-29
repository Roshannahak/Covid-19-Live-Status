package com.android.covid19.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.covid19.R;
import com.android.covid19.model.CountryInfo;
import com.android.covid19.model.CovidDataWorld;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CountrywiseDataAdapter extends RecyclerView.Adapter<CountrywiseDataAdapter.CountrywiseViewHolder> implements Filterable {

    Context context;
    List<CovidDataWorld> covidDataWorldList;
    List<CovidDataWorld> covidDataWorldListcopy;

    String country_name;
    String total_case;

    public interface onItemClick{
        void clickedItem(int position);
    }

    onItemClick itemClick;

    public void setItemClickListener(onItemClick itemClick){
        this.itemClick = itemClick;
    }
    public CountrywiseDataAdapter(Context context, List<CovidDataWorld> covidDataWorldList){
        this.context = context;
        this.covidDataWorldList = covidDataWorldList;
        covidDataWorldListcopy = new ArrayList<>(covidDataWorldList);
    }
    @NonNull
    @Override
    public CountrywiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.countryview, parent, false);
        return new CountrywiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountrywiseViewHolder holder, int position) {
        CovidDataWorld covidDataWorld = covidDataWorldList.get(position);
        CountryInfo countryInfo = covidDataWorld.getCountryInfo();

        country_name = covidDataWorld.getCountry();
        total_case = covidDataWorld.getCases();
        int totalcaseint = Integer.parseInt(total_case);
        total_case = NumberFormat.getInstance().format(totalcaseint);
        String uri = countryInfo.getFlag();

        holder.countryName.setText(country_name);
        holder.totalcase.setText(total_case);

        Glide.with(context).load(uri).into(holder.flagImage);
    }

    @Override
    public int getItemCount() {
        return covidDataWorldList.size();
    }

    public class CountrywiseViewHolder extends RecyclerView.ViewHolder{

        ImageView flagImage;
        TextView countryName, totalcase;

        public CountrywiseViewHolder(@NonNull View itemView) {
            super(itemView);

            flagImage = itemView.findViewById(R.id.flagImageview);
            countryName = itemView.findViewById(R.id.countryNameTextview);
            totalcase = itemView.findViewById(R.id.totalCountrycaseTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.clickedItem(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return filterObject;
    }
    private Filter filterObject = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CovidDataWorld> filterlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterlist.addAll(covidDataWorldListcopy);
            }else {
                String filterpattern = constraint.toString().toLowerCase().trim();

                for (CovidDataWorld covidDataWorld : covidDataWorldListcopy){
                    if (covidDataWorld.getCountry().toLowerCase().contains(filterpattern)){
                        filterlist.add(covidDataWorld);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            covidDataWorldList.clear();
            covidDataWorldList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
