package com.rootlol.teacherclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rootlol.teacherclock.pojo.Mess;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectActivity extends AppCompatActivity {

    SharedPreferences mSettings;
    LinearLayout LLConnect;
    LinearLayout LLPin;
    ProgressBar PBar;
    TextView TVerror;
    EditText ETpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        mSettings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        LLConnect = findViewById(R.id.linearLayout);
        LLPin = findViewById(R.id.linearLayout2);
        PBar = findViewById(R.id.progressBar);
        TVerror = findViewById(R.id.textView7);
        ETpin = findViewById(R.id.editText5);
    }

    public void buttonConnect(View view){
        LLConnect.setVisibility(View.INVISIBLE);
        PBar.setVisibility(View.VISIBLE);

        ApiClock.getInstance().genPin().enqueue(new Callback<Mess>() {
            @Override
            public void onResponse(Call<Mess> call, Response<Mess> response) {
                if (response.body().getMess().equals("input pin")){
                    PBar.setVisibility(View.INVISIBLE);
                    TVerror.setVisibility(View.INVISIBLE);
                    LLPin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Mess> call, Throwable throwable) {
                PBar.setVisibility(View.INVISIBLE);
                TVerror.setVisibility(View.VISIBLE);
                LLConnect.setVisibility(View.VISIBLE);
            }
        });
    }

    public void buttonSetToken(View view){
        LLPin.setVisibility(View.INVISIBLE);
        PBar.setVisibility(View.VISIBLE);
        String pin = ETpin.getText().toString();
        if (!pin.equals("")) {
            ApiClock.getInstance().setToken(pin, mSettings.getString("token", "")).enqueue(new Callback<Mess>() {
                @Override
                public void onResponse(Call<Mess> call, Response<Mess> response) {
                    if (response.body().getMess().equals("ok")) {
                        startActivity(new Intent(ConnectActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        PBar.setVisibility(View.INVISIBLE);
                        TVerror.setVisibility(View.VISIBLE);
                        LLPin.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Mess> call, Throwable throwable) {
                    PBar.setVisibility(View.INVISIBLE);
                    TVerror.setVisibility(View.VISIBLE);
                    LLPin.setVisibility(View.VISIBLE);
                }
            });
        }else{
            PBar.setVisibility(View.INVISIBLE);
            TVerror.setVisibility(View.VISIBLE);
            LLPin.setVisibility(View.VISIBLE);
        }
    }
}
