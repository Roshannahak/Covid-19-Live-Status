package com.colive.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.colive.covid19.R;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.colive.covid19.Countries.ACTIVE;
import static com.colive.covid19.Countries.CONFIRMED;
import static com.colive.covid19.Countries.COUNTRY_NAME;
import static com.colive.covid19.Countries.DECEASED;
import static com.colive.covid19.Countries.POPULATION;
import static com.colive.covid19.Countries.RECOVER;
import static com.colive.covid19.Countries.TEST;
import static com.colive.covid19.Countries.TODAY_CONFIRM;
import static com.colive.covid19.Countries.TODAY_DECEASED;
import static com.colive.covid19.Countries.TODAY_RECOVER;
import static com.colive.covid19.Countries.UPDATE;

public class PerCountry extends AppCompatActivity {

    AnimatedPieView pieView;
    TextView confirmtxt, activetxt, recovertxt, deceasedtxt, todayconfirmtxt, todayrecovertxt, todaydeceasedtxt, populationtxt, testedtxt, datetxt, timetxt;
    String country_name, confirm, active, recovered, deceased, todayconfirm, todayrecover, todaydeceased, population, test, update;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkActionBarTheme);
        }else setTheme(R.style.LightActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_country);

        initializationViews();
        getIntentData();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(country_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setInfoInTextView();
    }

    private void setInfoInTextView() {
        int confirmint = Integer.parseInt(confirm);
        confirm = NumberFormat.getInstance().format(confirmint);
        confirmtxt.setText(confirm);

        int activeint = Integer.parseInt(active);
        active = NumberFormat.getInstance().format(activeint);
        activetxt.setText(active);

        int recoverint = Integer.parseInt(recovered);
        recovered = NumberFormat.getInstance().format(recoverint);
        recovertxt.setText(recovered);

        int deceasedint = Integer.parseInt(deceased);
        deceased = NumberFormat.getInstance().format(deceasedint);
        deceasedtxt.setText(deceased);

        int todayconfirmint = Integer.parseInt(todayconfirm);
        todayconfirm = NumberFormat.getInstance().format(todayconfirmint);
        todayconfirmtxt.setText("+"+todayconfirm);

        int todayrecoverint = Integer.parseInt(todayrecover);
        todayrecover = NumberFormat.getInstance().format(todayrecoverint);
        todayrecovertxt.setText("+"+todayrecover);

        int todaydeceasedint = Integer.parseInt(todaydeceased);
        todaydeceased = NumberFormat.getInstance().format(todaydeceasedint);
        todaydeceasedtxt.setText("+"+todaydeceased);

        int populationint = Integer.parseInt(population);
        population = NumberFormat.getInstance().format(populationint);
        populationtxt.setText(population);

        int testint = Integer.parseInt(test);
        test = NumberFormat.getInstance().format(testint);
        testedtxt.setText(test);

        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.startAngle(-90)// Starting angle offset
                .strokeMode(false)
                .splitAngle(1)
                .autoSize(true)
//                .pieRadius(100)
                .pieRadiusRatio(1)
                .drawText(true)
                .canTouch(true)
                .addData(new SimplePieInfo(recoverint, Color.parseColor("#14E136"), "recovered"))//Data (bean that implements the IPieInfo interface)
                .addData(new SimplePieInfo(activeint, Color.parseColor("#149BE3"), "active"))
                .addData(new SimplePieInfo(deceasedint, Color.parseColor("#838383"), "deceased"))
                .duration(1000);// draw pie animation duration

        // The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
        pieView.applyConfig(config);
        pieView.start();

        String date = timeFormater(update, 1);
        datetxt.setText(date);

        String time = timeFormater(update, 2);
        timetxt.setText(time);
    }

    private String timeFormater(String update, int i) {
        String timeformat;
        long l = Long.parseLong(update);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        if (i == 1){
            timeformat = new SimpleDateFormat("dd MMM", Locale.US).format(calendar.getTime());
            return timeformat;
        }else if (i == 2){
            timeformat = new SimpleDateFormat("HH:mm a", Locale.US).format(calendar.getTime());
            return timeformat;
        }
        return update;
    }

    private void getIntentData() {
        toolbar = findViewById(R.id.per_country_toolbar);
        Intent intent = getIntent();
        country_name = intent.getStringExtra(COUNTRY_NAME);
        confirm = intent.getStringExtra(CONFIRMED);
        active = intent.getStringExtra(ACTIVE);
        recovered = intent.getStringExtra(RECOVER);
        deceased = intent.getStringExtra(DECEASED);
        todayconfirm = intent.getStringExtra(TODAY_CONFIRM);
        todayrecover = intent.getStringExtra(TODAY_RECOVER);
        todaydeceased = intent.getStringExtra(TODAY_DECEASED);
        population = intent.getStringExtra(POPULATION);
        test = intent.getStringExtra(TEST);
        update = intent.getStringExtra(UPDATE);
    }

    private void initializationViews() {
        confirmtxt = findViewById(R.id.country_confirm_count_textview);
        activetxt = findViewById(R.id.country_active_count_textview);
        recovertxt = findViewById(R.id.country_recovered_count_textview);
        deceasedtxt = findViewById(R.id.country_deceased_count_textview);
        todayconfirmtxt = findViewById(R.id.country_delta_confirmed_textview);
        todaydeceasedtxt = findViewById(R.id.country_delta_deceased_textview);
        todayrecovertxt = findViewById(R.id.country_delta_recovered_textview);
        populationtxt = findViewById(R.id.populationtextview);
        testedtxt = findViewById(R.id.country_tested_count_textview);
        datetxt = findViewById(R.id.country_dateTextview);
        timetxt = findViewById(R.id.country_timeTextview);

        pieView = findViewById(R.id.countrywiseanimatedpieview);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                final Intent intent = NavUtils.getParentActivityIntent(PerCountry.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(PerCountry.this, intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}