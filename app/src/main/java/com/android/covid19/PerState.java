package com.android.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.android.covid19.States.ACTIVE;
import static com.android.covid19.States.CONFIRMED;
import static com.android.covid19.States.DECEASED;
import static com.android.covid19.States.DELTA_CONFIRMED;
import static com.android.covid19.States.DELTA_DECEASED;
import static com.android.covid19.States.DELTA_RECOVERED;
import static com.android.covid19.States.LAST_UPDATE;
import static com.android.covid19.States.RECOVERED;
import static com.android.covid19.States.STATE_NAME;

public class PerState extends AppCompatActivity {

    AnimatedPieView animatedPieView;
    TextView confirmtxt, activetxt, recoveredtxt, deceasedtxt, delta_confirmtxt, delta_deceasedtxt, delta_recoveredtxt, delta_activetxt, lastUpdateDatetxt, lastUpdateTimetxt;
    String confirmed, active, recovered, deceased, delta_confirmed, delta_deceased, delta_recovered, delta_active, state_name, last_update;
    String dateUpdate, timeUpdate;
    Toolbar toolbar;

    LinearLayout vaccinebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkActionBarTheme);
        }else setTheme(R.style.LightActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_state);

        initializationView();
        fetchIntentData();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(state_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setDataInTextView();

        handleButtonClicks();

    }

    private void handleButtonClicks() {
        vaccinebtn.setOnClickListener(v -> {
            Intent intent = new Intent(PerState.this, VaccinationCenters.class);
            intent.putExtra(STATE_NAME, state_name);
            startActivity(intent);
        });
    }

    private void setDataInTextView() {
        int confirmint = Integer.parseInt(confirmed);
        confirmed = NumberFormat.getInstance().format(confirmint);
        confirmtxt.setText(confirmed);

        int activeint = Integer.parseInt(active);
        active = NumberFormat.getInstance().format(activeint);
        activetxt.setText(active);

        int recoverint = Integer.parseInt(recovered);
        recovered = NumberFormat.getInstance().format(recoverint);
        recoveredtxt.setText(recovered);

        int deceasedint = Integer.parseInt(deceased);
        deceased = NumberFormat.getInstance().format(deceasedint);
        deceasedtxt.setText(deceased);

        int deltaconfirmint = Integer.parseInt(delta_confirmed);
        delta_confirmed = NumberFormat.getInstance().format(deltaconfirmint);
        delta_confirmtxt.setText("+"+delta_confirmed);

        int deltarecoverint = Integer.parseInt(delta_recovered);
        delta_recovered = NumberFormat.getInstance().format(deltarecoverint);
        delta_recoveredtxt.setText("+"+delta_recovered);

        int deltadeceasedint = Integer.parseInt(delta_deceased);
        delta_deceased = NumberFormat.getInstance().format(deltadeceasedint);
        delta_deceasedtxt.setText("+"+delta_deceased);

        int deltaactiveint = deltaconfirmint - deltarecoverint + deltadeceasedint;
        delta_active = NumberFormat.getInstance().format(deltaactiveint);
        delta_activetxt.setText("+"+delta_active);

        dateUpdate = formatTime(last_update, 1);
        lastUpdateDatetxt.setText(dateUpdate);

        timeUpdate = formatTime(last_update, 2);
        lastUpdateTimetxt.setText(timeUpdate);

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
        animatedPieView.applyConfig(config);
        animatedPieView.start();

    }

    private String formatTime(String last_update, int i) {
        Date date = null;
        String dateformat;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(last_update);
            if (i == 1){
                dateformat = new SimpleDateFormat("dd MMM", Locale.US).format(date);
                return dateformat;
            }else if (i == 2){
                dateformat = new SimpleDateFormat("HH:mm a", Locale.US).format(date);
                return dateformat;
            }else return "error";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return last_update;
    }

    private void fetchIntentData() {
        Intent intent = getIntent();
        confirmed = intent.getStringExtra(CONFIRMED);
        active = intent.getStringExtra(ACTIVE);
        recovered = intent.getStringExtra(RECOVERED);
        deceased = intent.getStringExtra(DECEASED);
        delta_confirmed = intent.getStringExtra(DELTA_CONFIRMED);
        delta_recovered = intent.getStringExtra(DELTA_RECOVERED);
        delta_deceased = intent.getStringExtra(DELTA_DECEASED);
        state_name = intent.getStringExtra(STATE_NAME);
        last_update = intent.getStringExtra(LAST_UPDATE);
    }

    private void initializationView() {

        toolbar = findViewById(R.id.per_state_toolbar);

        animatedPieView = findViewById(R.id.perstateAnimatedpieview);

        confirmtxt = findViewById(R.id.perstate_confirm_count_textview);
        activetxt = findViewById(R.id.perstate_active_count_textview);
        recoveredtxt = findViewById(R.id.perstate_recovered_count_textview);
        deceasedtxt = findViewById(R.id.perstate_deceased_count_textview);
        delta_confirmtxt = findViewById(R.id.perstate_delta_confirmed_textview);
        delta_deceasedtxt = findViewById(R.id.perstate_delta_deceased_textview);
        delta_recoveredtxt = findViewById(R.id.perstate_delta_recovered_textview);
        delta_activetxt = findViewById(R.id.perstate_delta_active_textview);
        lastUpdateDatetxt = findViewById(R.id.perstate_dateTextview);
        lastUpdateTimetxt = findViewById(R.id.perstate_timeTextview);

        vaccinebtn = findViewById(R.id.vaccineCenterButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                final Intent intent = NavUtils.getParentActivityIntent(PerState.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(PerState.this, intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}