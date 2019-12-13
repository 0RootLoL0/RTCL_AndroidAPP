package com.rootlol.teacherclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rootlol.teacherclock.pojo.LoginUser;
import com.rootlol.teacherclock.pojo.Mess;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        mSettings = getSharedPreferences("settings", Context.MODE_PRIVATE);

    }

    public void buttonRegister(View view){
        startActivity(new Intent(LoginActivity.this, RegistActivity.class));
    }

    public void buttonSingIn(View view){
        ApiServer.getInstance().login(email.getText().toString(), password.getText().toString()).enqueue(new Callback<Mess>() {
            @Override
            public void onResponse(Call<Mess> call, Response<Mess> response) {
                if(!response.body().getMess().equals("error")) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("token", response.body().getMess());
                    Toast.makeText(getApplicationContext(), response.body().getMess(), Toast.LENGTH_LONG).show();
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<Mess> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "error login", Toast.LENGTH_LONG).show();
            }
        });
        ApiServer.getInstance().getFIO(mSettings.getString("token", "")).enqueue(new Callback<Mess>() {
            @Override
            public void onResponse(Call<Mess> call, Response<Mess> response) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("fio", response.body().getMess());
                editor.apply();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Mess> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "error login", Toast.LENGTH_LONG).show();
            }
        });
    }
}
