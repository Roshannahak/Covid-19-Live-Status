package com.android.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.covid19.model.CovidDataIndia;
import com.android.covid19.model.Statewise;
import com.android.covid19.retrofit.APIClient;
import com.google.android.material.snackbar.Snackbar;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private long backpress;
    private int TIME_INTERVAL = 5000;
    LinearLayout linearLayout;
    AnimatedPieView chart;
    TextView confirmed, active, recovered, deceased, tested, delta_confirmed, delta_active, delta_recovered, delta_deceased, delta_tested;
    String confirm_case, active_case, recover_data, deceased_data, delta_confirmed_data, delta_active_data, delta_recovered_data, delta_deceased_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializationView();
        fatchData();

    }

    private void fatchData() {
        Call<CovidDataIndia> covidDataIndiaCall = APIClient.getinterface().getCovidData();
        covidDataIndiaCall.enqueue(new Callback<CovidDataIndia>() {
            @Override
            public void onResponse(Call<CovidDataIndia> call, Response<CovidDataIndia> response) {
                if (response.isSuccessful()){
                    CovidDataIndia covidDataIndia = response.body();
                    Statewise statewise = covidDataIndia.getStatewise().get(0);
                    confirm_case = statewise.getConfirmed();
                    active_case = statewise.getActive();
                    recover_data = statewise.getRecovered();
                    deceased_data = statewise.getDeaths();
                    delta_confirmed_data = statewise.getDeltaconfirmed();
                    delta_recovered_data = statewise.getDeltarecovered();
                    delta_deceased_data = statewise.getDeltadeaths();

                    //convert data into number format
                    int confirmint = Integer.parseInt(confirm_case);
                    confirm_case = NumberFormat.getInstance().format(confirmint);
                    confirmed.setText(confirm_case);

                    int activeint = Integer.parseInt(active_case);
                    active_case = NumberFormat.getInstance().format(activeint);
                    active.setText(active_case);

                    int recoverint = Integer.parseInt(recover_data);
                    recover_data = NumberFormat.getInstance().format(recoverint);
                    recovered.setText(recover_data);

                    int deceasedint = Integer.parseInt(deceased_data);
                    deceased_data = NumberFormat.getInstance().format(deceasedint);
                    deceased.setText(deceased_data);

                    int deltaconfirmint = Integer.parseInt(delta_confirmed_data);
                    delta_confirmed_data = NumberFormat.getInstance().format(deltaconfirmint);
                    delta_confirmed.setText("+"+delta_confirmed_data);

                    int deltarecoverint = Integer.parseInt(delta_recovered_data);
                    delta_recovered_data = NumberFormat.getInstance().format(deltarecoverint);
                    delta_recovered.setText("+"+delta_recovered_data);

                    int deltadeceasedint = Integer.parseInt(delta_deceased_data);
                    delta_deceased_data = NumberFormat.getInstance().format(deltadeceasedint);
                    delta_deceased.setText("+"+delta_deceased_data);

                    //calculate delta active case
                    int deltaactiveint = deltaconfirmint - deltarecoverint + deltadeceasedint;
                    delta_active_data = NumberFormat.getInstance().format(deltaactiveint);
                    delta_active.setText("+"+delta_active_data);

                    AnimatedPieViewConfig config = new AnimatedPieViewConfig();
                    config.startAngle(-90)// Starting angle offset
                            .strokeMode(false)
                            .splitAngle(1)
                            .autoSize(true)
                            .pieRadius(150)
                            .addData(new SimplePieInfo(activeint, Color.parseColor("#149BE3"), "Active_case"))//Data (bean that implements the IPieInfo interface)
                            .addData(new SimplePieInfo(recoverint, Color.parseColor("#14E136"), "Recovered"))
                            .addData(new SimplePieInfo(deceasedint, Color.parseColor("#A0A0A0"), "Deceased"))
                            .duration(2000);// draw pie animation duration

                    // The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
                    chart.applyConfig(config);
                    chart.start();
                }
            }

            @Override
            public void onFailure(Call<CovidDataIndia> call, Throwable t) {
                ToastMassage("Failed");
            }
        });
    }

    //Toast massage method
    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initializationView() {
        linearLayout = findViewById(R.id.liniarlayout);
        chart = findViewById(R.id.animatedpieview);
        confirmed = findViewById(R.id.confirm_count_textview);
        active = findViewById(R.id.active_count_textview);
        recovered = findViewById(R.id.recovered_count_textview);
        deceased = findViewById(R.id.deceased_count_textview);
        tested = findViewById(R.id.tested_count_textview);
        delta_confirmed = findViewById(R.id.delta_confirmed_textview);
        delta_active = findViewById(R.id.delta_active_textview);
        delta_recovered = findViewById(R.id.delta_recovered_textview);
        delta_deceased = findViewById(R.id.delta_deceased_textview);
        delta_tested = findViewById(R.id.delta_tested_textview);
    }

    @Override
    public void onBackPressed() {

        if (backpress + TIME_INTERVAL > System.currentTimeMillis()){
            super.onBackPressed();
        }else {
            Snackbar.make(linearLayout, "Press back again to exit", Snackbar.LENGTH_SHORT).show();
        }
        backpress = System.currentTimeMillis();

    }
}