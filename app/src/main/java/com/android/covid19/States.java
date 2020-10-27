package com.android.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.android.covid19.adapter.StatewiseDataAdapter;
import com.android.covid19.model.CovidDataIndia;
import com.android.covid19.model.Statewise;
import com.android.covid19.retrofit.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class States extends AppCompatActivity implements StatewiseDataAdapter.perStateRecyclerClickInterface {

    public static final String CONFIRMED = "confirmed";
    public static final String ACTIVE = "active";
    public static final String RECOVERED = "recovered";
    public static final String DECEASED = "deceased";
    public static final String DELTA_CONFIRMED = "delta_confirmed";
    public static final String DELTA_RECOVERED = "delta_recovered";
    public static final String DELTA_DECEASED = "delta_deceased";
    public static final String STATE_NAME = "statename";
    public static final String LAST_UPDATE = "lastupdatetime";

    RecyclerView stateRecycler;
    List<Statewise> newstatewiseslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        getSupportActionBar().setTitle("States");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializationView();
        fetchRecyclerData();
    }

    private void fetchRecyclerData() {
        Call<CovidDataIndia> covidDataIndiaCall = APIClient.getinterface().getCovidData();
        covidDataIndiaCall.enqueue(new Callback<CovidDataIndia>() {
            @Override
            public void onResponse(Call<CovidDataIndia> call, Response<CovidDataIndia> response) {
                if (response.isSuccessful()){
                    CovidDataIndia covidDataIndia = response.body();
                    newstatewiseslist = new ArrayList<>();
                    List<Statewise> statewiselist = covidDataIndia.getStatewise();
                    for (Statewise statewise : statewiselist.subList(1, statewiselist.size())){
                        newstatewiseslist.add(statewise);
                    }
                    StatewiseDataAdapter adapter = new StatewiseDataAdapter(getApplicationContext(), newstatewiseslist);
                    adapter.setOnItemClickListener(States.this);
                    stateRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CovidDataIndia> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initializationView() {
        stateRecycler = findViewById(R.id.statewishRecyclerView);
        stateRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    //action bar search icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void itemOnClick(int position) {
        Statewise statewise = newstatewiseslist.get(position);
        Intent intent = new Intent(States.this, PerState.class);
        intent.putExtra(CONFIRMED, statewise.getConfirmed());
        intent.putExtra(ACTIVE, statewise.getActive());
        intent.putExtra(RECOVERED, statewise.getRecovered());
        intent.putExtra(DECEASED, statewise.getDeaths());
        intent.putExtra(DELTA_CONFIRMED, statewise.getDeltaconfirmed());
        intent.putExtra(DELTA_RECOVERED, statewise.getDeltarecovered());
        intent.putExtra(DELTA_DECEASED, statewise.getDeltadeaths());
        intent.putExtra(LAST_UPDATE, statewise.getLastupdatedtime());
        intent.putExtra(STATE_NAME, statewise.getState());

        startActivity(intent);

    }
}