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
import com.example.finalfurnishings.api.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button registerButton;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.registerButton);
        usernameText = findViewById(R.id.registerUsername);
        passwordText = findViewById(R.id.registerPassword);
        apiService = ApiUtils.getApiService();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                if (validateFields(username, password)) {
                    registerUser(username, password);
                }
            }
        });
    }

    private boolean validateFields(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(this, "Both fields are required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser(String username, String password) {
        User user = new User(username, password);
        Call<ResObj> call = apiService.register(username, password);
        call.enqueue(new Callback<ResObj>() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}