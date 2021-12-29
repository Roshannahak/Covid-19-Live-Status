package com.colive.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.colive.covid19.R;

public class Team extends AppCompatActivity implements View.OnClickListener {

    ImageView closebtn;
    ImageView gitadi, linkedinadi, instaadi, gitros, linkedinros, instaros, gitsam, linkedinsam, instasam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        initializationViews();
        clickListeners();
        closeButtonListener();

    }

    private void clickListeners() {
        gitadi.setOnClickListener(this);
        instaadi.setOnClickListener(this);
        linkedinadi.setOnClickListener(this);

        gitros.setOnClickListener(this);
        linkedinros.setOnClickListener(this);
        instaros.setOnClickListener(this);

        gitsam.setOnClickListener(this);
        linkedinsam.setOnClickListener(this);
        instasam.setOnClickListener(this);
    }

    private void initializationViews() {
        closebtn = findViewById(R.id.closeButtonImage);

        gitadi = findViewById(R.id.adarshgitImageview);
        linkedinadi = findViewById(R.id.adarshlinkedinImageview);
        instaadi = findViewById(R.id.adarshinstaImageview);

        gitros = findViewById(R.id.roshangitImageview);
        linkedinros = findViewById(R.id.roshanlinkedinImageview);
        instaros = findViewById(R.id.roshaninstaImageview);

        gitsam = findViewById(R.id.samgitImageview);
        linkedinsam = findViewById(R.id.samlinkedinImageview);
        instasam = findViewById(R.id.saminstaImageview);
    }

    private void closeButtonListener() {
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Team.this, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adarshgitImageview:
                ToastMassage("Not available...");
                break;
            case R.id.adarshlinkedinImageview:
                ToastMassage("Not available...");
                break;
            case R.id.adarshinstaImageview:
                getUrl("https://www.instagram.com/adi._adarsh/");
                break;

            case R.id.roshangitImageview:
                getUrl("https://github.com/Roshannahak");
                break;
            case R.id.roshanlinkedinImageview:
                getUrl("https://www.linkedin.com/in/roshan-nahak-a15833193/");
                break;
            case R.id.roshaninstaImageview:
                getUrl("https://www.instagram.com/roshan_nahak/");
                break;

            case R.id.samgitImageview:
                ToastMassage("Not available...");
                break;
            case R.id.samlinkedinImageview:
                getUrl("https://www.linkedin.com/in/samrudhi-mohanty-80b667193/");
                break;
            case R.id.saminstaImageview:
                getUrl("https://www.instagram.com/sm._.7771/");
                break;
        }
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void getUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}