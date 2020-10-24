package com.android.covid19.retrofit;

import com.android.covid19.model.CovidDataIndia;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIServices {

    @GET("data.json")
    Call<CovidDataIndia> getCovidData();

}
