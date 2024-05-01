package com.example.organizerforlaserhairremovalsalon.Splash;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.os.Bundle;

import com.example.organizerforlaserhairremovalsalon.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.organizerforlaserhairremovalsalon.databinding.ActivitySplashBinding;

import com.example.organizerforlaserhairremovalsalon.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.launchMainButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.launchMainButton) {
            Intent launchMainIntent = new Intent(SplashActivity.this, MainActivity.class);
            launchMainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(launchMainIntent);
        }
    }
}