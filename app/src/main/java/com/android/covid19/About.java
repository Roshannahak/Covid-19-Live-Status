package com.android.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity implements View.OnClickListener {

    TextView link1, link2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        link1 = findViewById(R.id.firstLinkTextview);
        link2 = findViewById(R.id.secondLinkTextview);

        link1.setOnClickListener(this);
        link2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.firstLinkTextview:
                geturl("https://api.covid19india.org/data.json");
                break;
            case R.id.secondLinkTextview:
                geturl("https://corona.lmao.ninja/v2/countries");
                break;
        }
    }

    private void geturl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}