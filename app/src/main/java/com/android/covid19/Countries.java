package com.android.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.android.covid19.adapter.CountrywiseDataAdapter;
import com.android.covid19.model.CountryInfo;
import com.android.covid19.model.CovidDataWorld;
import com.android.covid19.retrofit.APIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Countries extends AppCompatActivity implements CountrywiseDataAdapter.onItemClick{

    public static final String COUNTRY_NAME = "countryname";
    public static final String CONFIRMED = "confirm";
    public static final String ACTIVE = "active";
    public static final String RECOVER = "recover";
    public static final String DECEASED = "deseased";
    public static final String TODAY_CONFIRM = "tconfirm";
    public static final String TODAY_RECOVER = "trecover";
    public static final String TODAY_DECEASED = "tdeceased";
    public static final String POPULATION = "population";
    public static final String TEST = "test";
    public static final String UPDATE = "update";

    RecyclerView recyclerView;
    List<CovidDataWorld> covidDataWorldList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        getSupportActionBar().setTitle("Countries");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.countrywishRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchdataFromServer();
    }

    private void fetchdataFromServer() {
        Call<List<CovidDataWorld>> listCall = APIClient.getinterface().getCovidDataWorld("https://corona.lmao.ninja/v2/countries");
        listCall.enqueue(new Callback<List<CovidDataWorld>>() {
            @Override
            public void onResponse(Call<List<CovidDataWorld>> call, Response<List<CovidDataWorld>> response) {
                if (response.isSuccessful()) {
                    covidDataWorldList = response.body();
                    CountrywiseDataAdapter adapter = new CountrywiseDataAdapter(getApplicationContext(), covidDataWorldList);
                    adapter.setItemClickListener(Countries.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<CovidDataWorld>> call, Throwable t) {
                ToastMassage("failed");
                Log.d("server", String.valueOf(t));
            }
        });
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //action bar search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void clickedItem(int position) {
        CovidDataWorld covidDataWorld = covidDataWorldList.get(position);

        Intent intent = new Intent(Countries.this, PerCountry.class);
        intent.putExtra(COUNTRY_NAME, covidDataWorld.getCountry());
        intent.putExtra(CONFIRMED, covidDataWorld.getCases());
        intent.putExtra(ACTIVE, covidDataWorld.getActive());
        intent.putExtra(RECOVER, covidDataWorld.getRecovered());
        intent.putExtra(DECEASED, covidDataWorld.getDeaths());
        intent.putExtra(TODAY_CONFIRM, covidDataWorld.getTodayCases());
        intent.putExtra(TODAY_RECOVER, covidDataWorld.getTodayRecovered());
        intent.putExtra(TODAY_DECEASED, covidDataWorld.getTodayDeaths());
        intent.putExtra(POPULATION, covidDataWorld.getPopulation());
        intent.putExtra(TEST, covidDataWorld.getTests());
        intent.putExtra(UPDATE, covidDataWorld.getUpdated());

        startActivity(intent);
    }
}