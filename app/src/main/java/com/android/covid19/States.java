package com.android.covid19;

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

import com.android.covid19.adapter.StatewiseDataAdapter;
import com.android.covid19.library.ProgressBarLoader;
import com.android.covid19.model.CovidDataIndia;
import com.android.covid19.model.Statewise;
import com.android.covid19.retrofit.APIClient;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    StatewiseDataAdapter adapter;

    SwipeRefreshLayout swipelayout;

    SearchView searchView;
    String s;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkActionBarTheme);
        } else setTheme(R.style.LightActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        toolbar = findViewById(R.id.state_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("States");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializationView();
        ProgressBarLoader.showDialog(States.this);
        fetchRecyclerData();

        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRecyclerData();
                swipelayout.setRefreshing(false);
                ToastMassage("refreshed");
            }
        });
    }

    private void fetchRecyclerData() {
        Call<CovidDataIndia> covidDataIndiaCall = APIClient.getinterface().getCovidData("https://api.covid19india.org/data.json");
        covidDataIndiaCall.enqueue(new Callback<CovidDataIndia>() {
            @Override
            public void onResponse(Call<CovidDataIndia> call, Response<CovidDataIndia> response) {
                if (response.isSuccessful()) {
                    CovidDataIndia covidDataIndia = response.body();
                    newstatewiseslist = new ArrayList<>();
                    List<Statewise> statewiselist = covidDataIndia.getStatewise();
                    for (Statewise statewise : statewiselist.subList(1, statewiselist.size())) {
                        newstatewiseslist.add(statewise);
                    }
                    adapter = new StatewiseDataAdapter(getApplicationContext(), newstatewiseslist);
                    adapter.setOnItemClickListener(States.this);
                    stateRecycler.setAdapter(adapter);
                    ProgressBarLoader.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<CovidDataIndia> call, Throwable t) {
                ProgressBarLoader.dismissDialog();
                ToastMassage("Can't reach to the server");
            }
        });
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initializationView() {
        swipelayout = findViewById(R.id.stateSwipeRefresh);
        stateRecycler = findViewById(R.id.statewishRecyclerView);
        stateRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    //action bar search icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_action, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search State..");

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
        switch (item.getItemId()) {
            case R.id.voice_search:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak state name...");
                startActivityForResult(intent, 1);
                break;

            case android.R.id.home:
                // todo: goto back activity from here
                final Intent intentd = NavUtils.getParentActivityIntent(States.this);
                intentd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(States.this, intentd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
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