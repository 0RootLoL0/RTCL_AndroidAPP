package com.rootlol.teacherclock;

import com.rootlol.teacherclock.pojo.Mess;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClock {
    public interface Api2 {

        @GET("genPin")
        Call<Mess> genPin();

        @GET("setToken")
        Call<Mess> setToken(@Query("pin") String pin, @Query("token") String token);
    }

    private static String urlBase = "http://192.168.1.12:5100/";
    private static Retrofit retrofit;
    private static Api2 api;

    public static Api2 getInstance(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(urlBase)
                    .build();
        }
        api = retrofit.create(Api2.class);
        return api;
    }

}