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

    public static final String EXTRA_SHOW_RLRC_INFO = "showRLRCInfo"; // Add this constant if not defined
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String email = getIntent().getStringExtra("user_email");
        Log.d("MainActivity", "Received Email: " + email);

        boolean showRLRCInfo = getIntent().getBooleanExtra(EXTRA_SHOW_RLRC_INFO, false);

        // Display ExploreFragment with RLRCInfo if flag is true, otherwise default to HomeFragment
        if (showRLRCInfo) {
            binding.bottomNavigationView.setSelectedItemId(R.id.explore); // Set Explore tab as selected
            replaceFragment(ExploreFragment.newInstance(true)); // Pass true to display RLRCInfo
        } else {
            replaceFragment(new HomeFragment()); // Default fragment
        }

        // Handle navigation item selection
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.explore) {
                replaceFragment(ExploreFragment.newInstance(false));
                return true;
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
                return true;
            } else {
                return false;
            }
        });
    }

    // Method to replace fragments in the container
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}