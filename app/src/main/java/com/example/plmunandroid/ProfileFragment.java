package com.example.plmunandroid;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private MaterialButton btnLogout;
    private MaterialButton btnUserInfo;
    private MaterialButton btnHelpSupport;
    private MaterialButton btnAbout;
    private TextView user_email;
    private TextView user_name;

    private GoogleSignInClient googleSignInClient;  // Add GoogleSignInClient for sign-out

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        btnLogout = view.findViewById(R.id.btn_logout);
        btnUserInfo = view.findViewById(R.id.btn_user_info);
        btnHelpSupport = view.findViewById(R.id.btn_help_support);
        btnAbout = view.findViewById(R.id.btn_about);
        user_email = view.findViewById(R.id.user_email);
        user_name = view.findViewById(R.id.user_name);

        // Retrieve the email from SharedPreferences
        SharedPreferences preferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE);
        String email = preferences.getString("user_email", "");
        Log.d("ProfileFragment", "Received Email: " + email);  // Make sure it's logged correctly

        // Set the email in the TextView
        user_email.setText(email);

        // Update the username based on the email domain
        if (email.endsWith("@plmun.edu.ph")) {
            user_name.setText("STUDENT");
        } else {
            user_name.setText("GUEST");
        }

        // Initialize GoogleSignInClient for Google logout
        googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);

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
        Toast.makeText(requireContext(), "Help & Support Coming Soon", Toast.LENGTH_SHORT).show();
    }

    private void navigateToAbout() {
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
            // Sign the user out from Firebase
            FirebaseAuth.getInstance().signOut();

            // Sign out from Google
            googleSignInClient.signOut()
                    .addOnCompleteListener(requireActivity(), task -> {
                        if (task.isSuccessful()) {
                            // Clear SharedPreferences
                            SharedPreferences preferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE);
                            preferences.edit().clear().apply();  // Clear preferences

                            // Redirect the user to the SignInActivity to log in again
                            Intent intent = new Intent(requireContext(), SignInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
                            startActivity(intent);

                            // Optionally, display a success message
                            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                            // Finish the current activity to prevent navigating back to the profile page
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error logging out from Google", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error logging out", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
