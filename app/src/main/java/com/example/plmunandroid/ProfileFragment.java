package com.example.plmunandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfileFragment extends Fragment {

    private MaterialButton btnLogout;
    private MaterialButton btnUserInfo;
    private MaterialButton btnHelpSupport;
    private MaterialButton btnAbout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize all buttons
        btnLogout = view.findViewById(R.id.btn_logout);
        btnUserInfo = view.findViewById(R.id.btn_user_info);
        btnHelpSupport = view.findViewById(R.id.btn_help_support);
        btnAbout = view.findViewById(R.id.btn_about);

        // Set click listeners
        btnLogout.setOnClickListener(v -> showLogoutConfirmationDialog());
        btnUserInfo.setOnClickListener(v -> navigateToUserInfo());
        btnHelpSupport.setOnClickListener(v -> navigateToHelpSupport());
        btnAbout.setOnClickListener(v -> navigateToAbout());

        return view;
    }

    private void navigateToUserInfo() {
        try {
            UserProfileFragment userProfileFragment = new UserProfileFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.frame_layout, userProfileFragment);
            transaction.addToBackStack(null);  // Allows back navigation
            transaction.commit();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error navigating to User Info", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void navigateToHelpSupport() {
        // TODO: navigation to Help & Support screen
        Toast.makeText(requireContext(), "Help & Support Coming Soon", Toast.LENGTH_SHORT).show();
    }

    private void navigateToAbout() {
        // TODO: navigation to About screen
        Toast.makeText(requireContext(), "About Coming Soon", Toast.LENGTH_SHORT).show();
    }

    private void showLogoutConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> performLogout())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void performLogout() {
        try {
            // Clear any saved user data or preferences
            // SharedPreferences preferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            // preferences.edit().clear().apply();

            // Create intent to start SignInActivity
            Intent intent = new Intent(requireContext(), SignInActivity.class);
            // Clear the back stack so user can't go back after logout
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Finish the current activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error logging out", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}