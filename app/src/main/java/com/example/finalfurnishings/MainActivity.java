package com.example.finalfurnishings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalfurnishings.api.ApiService;
import com.example.finalfurnishings.api.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

        Button userProfBtn;
        Button postButton;
        Button logoutButton;
        ApiService apiService;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            userProfBtn = findViewById(R.id.profileButton);
            postButton = findViewById(R.id.postListingButton);
            logoutButton = findViewById(R.id.logoutButton);
            apiService = ApiUtils.getApiService();

            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (MainActivity.this, PostActivity.class);
                    startActivity(intent);
                }
            });

            userProfBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, UserProfile.class);
                    startActivity(intent);
                }
            });

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });


        }

        private void logout() {
            Call<String> call = apiService.logout();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                    startActivity(intent);
                    System.out.println(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }