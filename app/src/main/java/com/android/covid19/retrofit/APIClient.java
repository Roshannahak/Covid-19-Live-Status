package com.android.covid19.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static String BASE_URL = "https://api.covid19india.org/";

    public static APIServices apiServices = null;

    public static APIServices getinterface(){
        if (apiServices == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServices = retrofit.create(APIServices.class);
        }
        return apiServices;
    }
}
