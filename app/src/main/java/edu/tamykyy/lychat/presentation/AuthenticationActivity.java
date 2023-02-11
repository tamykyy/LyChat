package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.tamykyy.lychat.R;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}