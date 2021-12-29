package com.colive.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.colive.covid19.R;
import com.colive.covid19.adapter.CountrywiseDataAdapter;
import com.colive.covid19.library.ProgressBarLoader;
import com.colive.covid19.model.CovidDataWorld;
import com.colive.covid19.retrofit.APIClient;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    CountrywiseDataAdapter adapter;

    SwipeRefreshLayout swipelayout;

    SearchView searchView;
    String s;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkActionBarTheme);
        }else setTheme(R.style.LightActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        toolbar = findViewById(R.id.country_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Countries");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipelayout = findViewById(R.id.countrySwipeRefresh);
        recyclerView = findViewById(R.id.countrywishRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProgressBarLoader.showDialog(Countries.this);
        fetchdataFromServer();

        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchdataFromServer();
                swipelayout.setRefreshing(false);
                ToastMassage("refreshed");
            }
        });
    }

    private void fetchdataFromServer() {
        Call<List<CovidDataWorld>> listCall = APIClient.getinterface().getCovidDataWorld("https://corona.lmao.ninja/v2/countries");
        listCall.enqueue(new Callback<List<CovidDataWorld>>() {
            @Override
            public void onResponse(Call<List<CovidDataWorld>> call, Response<List<CovidDataWorld>> response) {
                if (response.isSuccessful()) {
                    covidDataWorldList = response.body();
                    adapter = new CountrywiseDataAdapter(getApplicationContext(), covidDataWorldList);
                    adapter.setItemClickListener(Countries.this);
                    recyclerView.setAdapter(adapter);
                    ProgressBarLoader.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<List<CovidDataWorld>> call, Throwable t) {
                ProgressBarLoader.dismissDialog();
                ToastMassage("Can't reach to the server");
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

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Country..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.voice_search:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speck country name...");
                startActivityForResult(intent, 1);
                break;

            case android.R.id.home:
                // todo: goto back activity from here
                final Intent intentd = NavUtils.getParentActivityIntent(Countries.this);
                intentd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(Countries.this, intentd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            s = String.valueOf(result.get(0));
            String withoutAccent = Normalizer.normalize(s, Normalizer.Form.NFD);
            String output = withoutAccent.replaceAll("[^a-zA-Z ]", "");
            adapter.getFilter().filter(output);
            searchView.onActionViewExpanded();
            searchView.setQuery(output, false);
            searchView.setFocusable(true);
            Log.d("voice", output);
        }
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