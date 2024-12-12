package com.example.plmunandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etStudentNum, etEmail, etUsername, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private TextView tvSignIn;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        // Initialize views
        etStudentNum = findViewById(R.id.studentNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);

        databaseHelper = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(v -> {
            // Validate and register user
            if (validateInputs()) {
                String studentNumber = etStudentNum.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Insert user with all details
                databaseHelper.insertUser(studentNumber, password, email, username);

                Toast.makeText(SignUpActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate Student Number
        String studentNumber = etStudentNum.getText().toString().trim();
        if (studentNumber.isEmpty()) {
            etStudentNum.setError("Student number is required");
            isValid = false;
        }

        // Validate Email
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            isValid = false;
        } else if (!email.endsWith("@plmun.edu.ph")) {
            etEmail.setError("Please use your institutional email");
            isValid = false;
        }

        // Validate Username
        String username = etUsername.getText().toString().trim();
        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            isValid = false;
        }

        // Validate Password
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            isValid = false;
        } else if (password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }
}