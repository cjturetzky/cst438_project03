package com.example.cst438_project3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    EditText username, password;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmBtn = findViewById(R.id.confirm_button);

    }
}