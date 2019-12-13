package com.rootlol.teacherclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.rootlol.teacherclock.pojo.Mess;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        Timer mTimer = new Timer();
        mTimer.schedule(new MyTimerTask(), 1000);

        if(mSettings.contains("token")) {
            ApiServer.getInstance().getFIO(mSettings.getString("token", "")).enqueue(new Callback<Mess>() {
                @Override
                public void onResponse(Call<Mess> call, Response<Mess> response) {
                    if (response.body().getMess() != null) {
                        SharedPreferences.Editor editor = mSettings.edit();
                        Toast.makeText(getApplicationContext(), response.body().getMess(), Toast.LENGTH_LONG).show();
                        editor.putString("fio", response.body().getMess());
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call<Mess> call, Throwable throwable) {

                }
            });
        }
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mSettings.contains("token")) {
                        if(!mSettings.getString("token", "").equals("") ) {
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        }
                    }else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            });
        }
    }
}
