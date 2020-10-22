package com.android.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class Dashboard extends AppCompatActivity {

    private long backpress;
    private int TIME_INTERVAL = 5000;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        linearLayout = findViewById(R.id.liniarlayout);

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