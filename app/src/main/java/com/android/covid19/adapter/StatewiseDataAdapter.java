package com.android.covid19.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.android.covid19.R;
import com.android.covid19.model.Statewise;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class StatewiseDataAdapter extends RecyclerView.Adapter<StatewiseDataAdapter.StatewiseViewHolder> implements Filterable {

    String confirmed, statename;

    Context context;
    List<Statewise> statewiseList;
    List<Statewise> statewiseListcopy;

    public interface perStateRecyclerClickInterface{
        void itemOnClick(int position);
    }
    perStateRecyclerClickInterface stateRecyclerClickInterface;

    public void setOnItemClickListener(perStateRecyclerClickInterface stateRecyclerClickInterface){
        this.stateRecyclerClickInterface = stateRecyclerClickInterface;
    }
    public StatewiseDataAdapter(Context context, List<Statewise> statewiseList){
        this.context = context;
        this.statewiseList = statewiseList;
        statewiseListcopy = new ArrayList<>(statewiseList);
    }

    @NonNull
    @Override
    public StatewiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.stateview, parent, false);
        return new StatewiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatewiseViewHolder holder, int position) {
        Statewise statewise = statewiseList.get(position);

        confirmed = statewise.getConfirmed();
        statename = statewise.getState();

        int totalcaseint = Integer.parseInt(confirmed);
        String totalcase = NumberFormat.getInstance().format(totalcaseint);
        holder.statenametxt.setText(statename);
        holder.totalcasetxt.setText(totalcase);
    }

    @Override
    public int getItemCount() {
        return statewiseList.size();
    }

    public class StatewiseViewHolder extends RecyclerView.ViewHolder{

        TextView statenametxt, totalcasetxt;
        LinearLayout container;

        public StatewiseViewHolder(@NonNull View itemView) {
            super(itemView);
            statenametxt = itemView.findViewById(R.id.stateNameTextView);
            totalcasetxt = itemView.findViewById(R.id.totalCaseTextView);
            container = itemView.findViewById(R.id.stateview_container);

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                container.setBackgroundResource(R.drawable.darkcard);
                statenametxt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
                totalcasetxt.setTextColor(context.getResources().getColor(R.color.dark_mode_text_color));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stateRecyclerClickInterface.itemOnClick(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return filtetObject;
    }
    private Filter filtetObject = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Statewise> filteredlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredlist.addAll(statewiseListcopy);
            }else {
                String filterpattern = constraint.toString().toLowerCase().trim();

                for (Statewise statewise : statewiseListcopy){
                    if (statewise.getState().toLowerCase().contains(filterpattern)){
                        filteredlist.add(statewise);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            statewiseList.clear();
            statewiseList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
