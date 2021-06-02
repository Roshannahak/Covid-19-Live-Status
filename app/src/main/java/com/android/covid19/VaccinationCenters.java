package com.android.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.covid19.adapter.VaccineCenterAdapter;
import com.android.covid19.model.vaccination.District;
import com.android.covid19.model.vaccination.DistrictVaccineCenters;
import com.android.covid19.model.vaccination.Session;
import com.android.covid19.model.vaccination.State;
import com.android.covid19.model.vaccination.VaccinationDistricts;
import com.android.covid19.model.vaccination.VaccinationState;
import com.android.covid19.retrofit.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.covid19.States.STATE_NAME;

public class VaccinationCenters extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Toolbar toolbar;
    String statename;
    VaccinationState vaccinationState;
    VaccinationDistricts vaccinationDistricts;
    DistrictVaccineCenters districtVaccineCenters;
    RecyclerView vaccineCardRecyclerView;
    List<Session> sessionList;

    List<District> districtList;

    AutoCompleteTextView districtDropdown;
    List<String> distname;
    ArrayAdapter<String> districtArrayAdapter;

    VaccineCenterAdapter vaccineCenterAdapter;

    String currentState;
    int stateid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkActionBarTheme);
        } else setTheme(R.style.LightActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_centers);

        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Vaccination Centers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        statename = intent.getStringExtra(STATE_NAME);

        getStateDataFromApiCompare();
    }

    private void getDistrictDataFromApi() {
        Call<VaccinationDistricts> vaccinationDistrictsCall = APIClient.getinterface().getDistrictForVaccine(stateid);
        vaccinationDistrictsCall.enqueue(new Callback<VaccinationDistricts>() {
            @Override
            public void onResponse(Call<VaccinationDistricts> call, Response<VaccinationDistricts> response) {
                if (response.isSuccessful()) {
                    vaccinationDistricts = response.body();

                    distname = new ArrayList<>();
                    districtList = new ArrayList<>();
                    for (District district : vaccinationDistricts.getDistricts()) {
                        distname.add(district.getDistrictName());
                        districtList.add(district);
                    }

                    setDistrictNameInMenu();
                } else {
                    massage("district data fetching error");
                }
            }

            @Override
            public void onFailure(Call<VaccinationDistricts> call, Throwable t) {

            }
        });
    }

    private void setDistrictNameInMenu() {
        districtArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item_view, distname);
        districtDropdown.setAdapter(districtArrayAdapter);
        if (distname.size() >= 10)
            districtDropdown.setDropDownHeight(500);
        districtDropdown.setOnItemClickListener(this);
    }


    private void getStateDataFromApiCompare() {
        Call<VaccinationState> vaccinationStateCall = APIClient.getinterface().getStatesForVaccine("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        vaccinationStateCall.enqueue(new Callback<VaccinationState>() {
            @Override
            public void onResponse(Call<VaccinationState> call, Response<VaccinationState> response) {
                if (response.isSuccessful()) {
                    vaccinationState = response.body();

                    for (State state : vaccinationState.getStates()) {
                        String currState = state.getStateName();
                        int id = state.getStateId();
                        if (statename.toLowerCase().contains(currState.toLowerCase()) || currState.toLowerCase().contains(statename.toLowerCase())) {
                            currentState = currState;
                            stateid = id;
                        }
                    }
                    getDistrictDataFromApi();

                } else {
                    massage("response error : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<VaccinationState> call, Throwable t) {
                massage("can't reach to the server try again!!");
            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.vaccine_page_toolbar);
        districtDropdown = findViewById(R.id.districtDropdownItem);

        vaccineCardRecyclerView = findViewById(R.id.vaccine_center_recycler_view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                final Intent intent = NavUtils.getParentActivityIntent(VaccinationCenters.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(VaccinationCenters.this, intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void massage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //for dropdown list item select listener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int distId = districtList.get(position).getDistrictId();

        findCentersByDistID(distId);
    }

    private void findCentersByDistID(int distId) {
        Call<DistrictVaccineCenters> districtVaccineCentersCall = APIClient.getinterface().findVaccineCenters(distId, "02-06-2021");
        districtVaccineCentersCall.enqueue(new Callback<DistrictVaccineCenters>() {
            @Override
            public void onResponse(Call<DistrictVaccineCenters> call, Response<DistrictVaccineCenters> response) {
                if (response.isSuccessful()){
                    districtVaccineCenters = response.body();

                    sessionList = new ArrayList<>();
                    sessionList.clear();
                    for (Session session : districtVaccineCenters.getSessions()){
                        sessionList.add(session);
                    }
                    vaccineCenterAdapter = new VaccineCenterAdapter(getApplicationContext(), sessionList);
                    vaccineCardRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    vaccineCardRecyclerView.setHasFixedSize(false);
                    vaccineCardRecyclerView.setAdapter(vaccineCenterAdapter);
                    vaccineCenterAdapter.notifyDataSetChanged();
                }else {
                    massage("can't able to fetch response");
                }
            }

            @Override
            public void onFailure(Call<DistrictVaccineCenters> call, Throwable t) {

            }
        });
    }
}