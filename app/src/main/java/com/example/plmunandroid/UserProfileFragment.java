package com.example.plmunandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class UserProfileFragment extends Fragment {
    private TextInputEditText editStudentNumber;
    private TextInputEditText editEmail;
    private TextInputEditText editPassword;
    private MaterialButton btnSave;
    private FloatingActionButton btnChangePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_info, container, false);

        // Initialize views
        initViews(view);

        // Setup listeners
        setupListeners();

        // Load user data
        loadUserData();

        return view;
    }

    private void initViews(View view) {
        editStudentNumber = view.findViewById(R.id.edit_student_number);
        editEmail = view.findViewById(R.id.edit_email);
        editPassword = view.findViewById(R.id.edit_password);
        btnSave = view.findViewById(R.id.btn_save);
        btnChangePhoto = view.findViewById(R.id.btn_change_photo);
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveUserProfile());
        btnChangePhoto.setOnClickListener(v -> handlePhotoChange());
    }

    private void loadUserData() {
        try {
            SharedPreferences preferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            String email = preferences.getString("user_email", "");
            Log.d("UserProfileFragment", "Loaded Email: " + email);

            // Set the email in the TextInputEditText
            editEmail.setText(email);

            // Update the username based on the email domain
            if (email.endsWith("@plmun.edu.ph")) {
                editStudentNumber.setText("STUDENT");
                editPassword.setText("");
            } else {
                editStudentNumber.setText("GUEST");
                editPassword.setText("NA");
            }

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error loading user data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveUserProfile() {
        try {
            if (!validateInputs()) {
                return;
            }

            String email = editEmail.getText().toString().trim();

            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_email", email);
            editor.apply();

            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error saving profile", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate Email
        String email = editEmail.getText().toString().trim();
        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email address");
            isValid = false;
        } else if (!email.endsWith("plmun.edu.ph")) {
            editEmail.setError("Please use your institutional email");
            isValid = false;
        }

        return isValid;
    }

    private void handlePhotoChange() {
        Toast.makeText(requireContext(), "Photo change feature coming soon", Toast.LENGTH_SHORT).show();
    }
}
