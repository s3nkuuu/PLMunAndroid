package com.example.plmunandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plmunandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load the default HomeFragment when the app starts
        replaceFragment(new HomeFragment());

        // Set up the bottom navigation
        binding.bottomNavigationView.setBackground(null);

        // Handle item selection in the bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId(); // Get the selected item's ID

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.explore) {
                replaceFragment(new ExploreFragment());
                return true;
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
                return true;
            } else {
                return false;
            }
        });
    }

    // Method to replace fragments in the container (frame_layout)
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}