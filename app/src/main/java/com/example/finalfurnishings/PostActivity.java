package com.example.finalfurnishings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalfurnishings.api.ApiService;
import com.example.finalfurnishings.api.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    EditText nameText;
    EditText priceText;
    EditText descText;
    Button postButton;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        nameText = findViewById(R.id.itemEditName);
        priceText = findViewById(R.id.itemEditPrice);
        descText = findViewById(R.id.itemEditDesc);
        postButton = findViewById(R.id.itemCreateButton);
        apiService = ApiUtils.getApiService();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    postFurnishing();
                }
            }
        });
    }

    private boolean validateFields() {
        return true;
    }

    private void postFurnishing() {
        String itemName = nameText.getText().toString();
        String itemDesc = descText.getText().toString();
        double itemPrice = Double.parseDouble(priceText.getText().toString());
        Call<String> call = apiService.postListing(itemName, itemPrice, itemDesc);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}