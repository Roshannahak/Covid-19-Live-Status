package com.android.covid19.retrofit;

import com.android.covid19.model.CovidDataIndia;
import com.android.covid19.model.CovidDataWorld;
import com.android.covid19.model.vaccination.DistrictVaccineCenters;
import com.android.covid19.model.vaccination.VaccinationDistricts;
import com.android.covid19.model.vaccination.VaccinationState;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIServices {

    @GET()
    Call<CovidDataIndia> getCovidData(@Url String url);

    @GET()
    Call<List<CovidDataWorld>> getCovidDataWorld(@Url String url);

    @GET("admin/location/states")
    Call<VaccinationState> getStatesForVaccine(@Header("user-agent") String header);

    @Headers("user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
    @GET("admin/location/districts/{stateid}")
    Call<VaccinationDistricts> getDistrictForVaccine(@Path("stateid") int id);

    @GET("appointment/sessions/public/findByDistrict")
    Call<DistrictVaccineCenters> findVaccineCenters(@Query("district_id") int id, @Query("date") String date);

}
