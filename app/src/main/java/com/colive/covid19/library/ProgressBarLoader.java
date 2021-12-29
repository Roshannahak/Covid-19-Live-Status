package com.colive.covid19.library;

import android.app.Activity;
import android.app.ProgressDialog;
import com.colive.covid19.R;

import java.util.Objects;

public class ProgressBarLoader {

    private static ProgressDialog progressDialog;

    public static void showDialog(Activity activity){
        progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressbar);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
    }
    public static void dismissDialog(){
        progressDialog.cancel();
    }

}
