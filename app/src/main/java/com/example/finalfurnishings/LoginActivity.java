package com.example.finalfurnishings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalfurnishings.api.ApiService;
import com.example.finalfurnishings.api.ApiUtils;
import com.example.finalfurnishings.api.ResObj;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button loginButton;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        usernameText = findViewById(R.id.loginUsername);
        passwordText = findViewById(R.id.loginPassword);
        apiService = ApiUtils.getApiService();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                if (login(username, password)) {
                    // go to activity
                }
                else {
                    // toast
                }

            }
        });
    }

    private boolean login(String username, String password) {
        Call<ResObj> call = apiService.login(username, password);
        call.enqueue(new Callback<ResObj>() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        return true;
    }
}
