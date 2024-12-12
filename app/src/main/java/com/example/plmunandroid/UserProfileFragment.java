package com.example.plmunandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserProfileFragment extends Fragment {
    private TextInputEditText editUsername;
    private TextInputEditText editStudentNumber;
    private TextInputEditText editEmail;
    private TextInputEditText editPassword;
    private MaterialButton btnSave;
    private FloatingActionButton btnChangePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

            // Load user details from SharedPreferences
            String username = prefs.getString("username", "");
            String studentNumber = prefs.getString("student_number", "");
            String email = prefs.getString("user_email", "");

            // Set the loaded values to the input fields
            editUsername.setText(username);
            editStudentNumber.setText(studentNumber);
            editEmail.setText(email);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error loading user data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveUserProfile() {
        try {
            // Validate inputs
            if (!validateInputs()) {
                return;
            }

            // Get values from input fields
            String username = editUsername.getText().toString().trim();
            String studentNumber = editStudentNumber.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            // Save to SharedPreferences
            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", username);
            editor.putString("student_number", studentNumber);
            editor.putString("user_email", email);
            editor.apply();

            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();

            // back to previous screen
            requireActivity().getSupportFragmentManager().popBackStack();

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error saving profile", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate Username
        if (editUsername.getText().toString().trim().isEmpty()) {
            editUsername.setError("Username is required");
            isValid = false;
        }

        // Validate Student Number
        if (editStudentNumber.getText().toString().trim().isEmpty()) {
            editStudentNumber.setError("Student number is required");
            isValid = false;
        }

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

        // Validate Password
        String password = editPassword.getText().toString();
        if (!password.isEmpty() && password.length() < 8) {
            editPassword.setError("Password must be at least 8 characters");
            isValid = false;
        }

        return isValid;
    }

    private void handlePhotoChange() {
        // TODO: Implement photo change functionality
        // This could involve:
        // 1. Opening camera
        // 2. Opening gallery
        // 3. Showing a bottom sheet with options
        Toast.makeText(requireContext(), "Photo change feature coming soon", Toast.LENGTH_SHORT).show();
    }
}