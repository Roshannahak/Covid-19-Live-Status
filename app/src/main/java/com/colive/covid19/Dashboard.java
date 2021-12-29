package com.colive.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colive.covid19.R;
import com.colive.covid19.library.ProgressBarLoader;
import com.colive.covid19.model.CovidDataIndia;
import com.colive.covid19.model.Statewise;
import com.colive.covid19.model.Tested;
import com.colive.covid19.retrofit.APIClient;
import com.google.android.material.snackbar.Snackbar;
import com.onesignal.OneSignal;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private static final String ONESIGNAL_APP_ID = "635d68b5-5911-4aec-ad5c-6467883286ee";

    private long backpress;
    private int TIME_INTERVAL = 3000;
    LinearLayout linearLayout;
    AnimatedPieView chart;
    TextView confirmed, active, recovered, deceased, tested, delta_confirmed, delta_recovered, delta_deceased, delta_tested, last_date, last_time;
    String confirm_case, active_case, recover_data, deceased_data, test_data, delta_confirmed_data, delta_recovered_data, delta_deceased_data, delta_test_data, last_update;
    CovidDataIndia covidDataIndia;
    String totalTest, oldtest, newsample, oldsample;
    CardView statebtn, countrybtn;
    SwipeRefreshLayout swipelayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkActionBarTheme);
        }else setTheme(R.style.LightActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializationView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Covid-19 App");

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        if (isConnected(this)) {
            ProgressBarLoader.showDialog(Dashboard.this);
            fatchData();
        }else {
            showConnectionDialog();
        }

        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fatchData();
                swipelayout.setRefreshing(false);
                ToastMassage("refreshed");
            }
        });
    }

    private void showConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("warning!!")
                .setMessage("Please connect to the Internet!!")
                .setCancelable(false)
                .setPositiveButton("connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isConnected(Dashboard dashboard) {
        ConnectivityManager connectivityManager = (ConnectivityManager) dashboard.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobiledata = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected() || (mobiledata != null && mobiledata.isConnected()))){
            return true;
        }else return false;
    }

    private void fatchData() {
        Call<CovidDataIndia> covidDataIndiaCall = APIClient.getinterface().getCovidData("https://api.covid19india.org/data.json");
        covidDataIndiaCall.enqueue(new Callback<CovidDataIndia>() {
            @Override
            public void onResponse(Call<CovidDataIndia> call, Response<CovidDataIndia> response) {
                if (response.isSuccessful()){
                    covidDataIndia = response.body();
                    Statewise statewise = covidDataIndia.getStatewise().get(0);
                    confirm_case = statewise.getConfirmed();
                    active_case = statewise.getActive();
                    recover_data = statewise.getRecovered();
                    deceased_data = statewise.getDeaths();
                    delta_confirmed_data = statewise.getDeltaconfirmed();
                    delta_recovered_data = statewise.getDeltarecovered();
                    delta_deceased_data = statewise.getDeltadeaths();
                    last_update = statewise.getLastupdatedtime();

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

                    //last updated date and time
                    //date
                    String date = formatDate(last_update, 1);
                    last_date.setText(date);

                    //time
                    String time = formatDate(last_update, 2);
                    last_time.setText(time);

                    AnimatedPieViewConfig config = new AnimatedPieViewConfig();
                    config.startAngle(-90)// Starting angle offset
                            .strokeMode(false)
                            .splitAngle(1)
                            .autoSize(true)
//                            .pieRadius(100)
                            .drawText(true)
                            .pieRadiusRatio(1)
                            .addData(new SimplePieInfo(activeint, Color.parseColor("#149BE3"), "Active_case"))//Data (bean that implements the IPieInfo interface)
                            .addData(new SimplePieInfo(recoverint, Color.parseColor("#14E136"), "Recovered"))
                            .addData(new SimplePieInfo(deceasedint, Color.parseColor("#A0A0A0"), "Deceased"))
                            .duration(1000);// draw pie animation duration

                    // The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
                    chart.applyConfig(config);
                    chart.start();

                    fetchTestData();
                    ProgressBarLoader.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<CovidDataIndia> call, Throwable t) {
                ProgressBarLoader.dismissDialog();
                ToastMassage("may be internet connection is slow!!");
            }
        });
    }

    private String formatDate(String last_update, int i) {
        Date date = null;
        String dateFormat;

        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(last_update);
            if (i == 1){
                dateFormat = new SimpleDateFormat("dd MMM", Locale.US).format(date);
                return dateFormat;
            }else if (i == 2){
                dateFormat = new SimpleDateFormat("HH:mm a", Locale.US).format(date);
                return dateFormat;
            }else return "error";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return last_update;
    }

    private void fetchTestData() {
        for (Tested tested : covidDataIndia.getTested()){
            totalTest = tested.getTotalsamplestested();
            newsample = tested.getSamplereportedtoday();
        }
        if (totalTest.isEmpty()){
            for (int i = 0; i < covidDataIndia.getTested().size() - 1; i++){
                Tested tested = covidDataIndia.getTested().get(i);
                oldtest = tested.getTotalsamplestested();
                oldsample = tested.getSamplereportedtoday();
            }

            //if recent test data is empty then fetch previous data(older data)
            test_data = oldtest;
            int oldtestint = Integer.parseInt(test_data);
            test_data = NumberFormat.getInstance().format(oldtestint);
            tested.setText(test_data);

            //daily sample old tested data
            delta_test_data = oldsample;
            int oldsampleint = Integer.parseInt(delta_test_data);
            delta_test_data = NumberFormat.getInstance().format(oldsampleint);
            delta_tested.setText("+"+delta_test_data);

        }else {

            //fetch recent tested data
            test_data = totalTest;
            int testint = Integer.parseInt(test_data);
            test_data = NumberFormat.getInstance().format(testint);
            tested.setText(test_data);

            //fetch recent tested sample data
            delta_test_data = newsample;
            int newsampleint = Integer.parseInt(delta_test_data);
            delta_test_data = NumberFormat.getInstance().format(newsampleint);
            delta_tested.setText("+"+delta_test_data);

        }
    }

    //Toast massage method
    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initializationView() {
        toolbar = findViewById(R.id.dashboard_toolbar);
        linearLayout = findViewById(R.id.liniarlayout);
        swipelayout = findViewById(R.id.dashboardSwipeRefresh);

        chart = findViewById(R.id.animatedpieview);
        confirmed = findViewById(R.id.confirm_count_textview);
        active = findViewById(R.id.active_count_textview);
        recovered = findViewById(R.id.recovered_count_textview);
        deceased = findViewById(R.id.deceased_count_textview);
        tested = findViewById(R.id.tested_count_textview);
        delta_confirmed = findViewById(R.id.delta_confirmed_textview);
        delta_recovered = findViewById(R.id.delta_recovered_textview);
        delta_deceased = findViewById(R.id.delta_deceased_textview);
        delta_tested = findViewById(R.id.delta_tested_textview);
        last_date = findViewById(R.id.dateTextview);
        last_time = findViewById(R.id.timeTextview);

        statebtn = findViewById(R.id.stateWiseData);
        statebtn.setOnClickListener(this);

        countrybtn = findViewById(R.id.countryWiseData);
        countrybtn.setOnClickListener(this);
    }

    //option bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                startActivity(new Intent(Dashboard.this, About.class));
                break;
//            case R.id.team:
//                startActivity(new Intent(Dashboard.this, Team.class));
//                break;
            case R.id.setting:
                startActivity(new Intent(Dashboard.this, Settings.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stateWiseData:
                startActivity(new Intent(Dashboard.this, States.class));
                break;
            case R.id.countryWiseData:
                startActivity(new Intent(Dashboard.this, Countries.class));
                break;
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        ToastMassage("on start");
//        ToastMassage("value: "+DARK_MOOD_CHECKER);
//        if (DARK_MOOD_CHECKER == 1){
//            setTheme(R.style.DarkActionBarTheme);
////            restartapp();
//        }else {
//            setTheme(R.style.LightActionBarTheme);
////            restartapp();
//        }
//    }
//
//    private void restartapp() {
//        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//        startActivity(intent);
//    }
}