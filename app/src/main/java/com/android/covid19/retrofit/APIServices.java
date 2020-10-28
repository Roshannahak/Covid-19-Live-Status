package com.android.covid19.retrofit;

import com.android.covid19.model.CovidDataIndia;
import com.android.covid19.model.CovidDataWorld;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIServices {

    @GET("data.json")
    Call<CovidDataIndia> getCovidData();

    @GET()
    Call<List<CovidDataWorld>> getCovidDataWorld(@Url String url);

}
