package com.rootlol.teacherclock;

import com.rootlol.teacherclock.pojo.LoginUser;
import com.rootlol.teacherclock.pojo.Mess;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ApiServer {
    public interface Api {

        @GET("loginDevice")
        Call<Mess> login(@Query("email") String email, @Query("password") String password);

        @GET("getFIO")
        Call<Mess> getFIO(@Query("token") String token);
    }

    private static String urlBase = "http://192.168.1.12:5010/";
    private static Retrofit retrofit;
    private static Api api;

    public static Api getInstance(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(urlBase)
                    .build();
        }
        api = retrofit.create(Api.class);
        return api;
    }

}
