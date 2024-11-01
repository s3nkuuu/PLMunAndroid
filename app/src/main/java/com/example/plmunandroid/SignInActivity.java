package com.example.plmunandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;

public class SignInActivity extends AppCompatActivity {

    private EditText etStudentNum, etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        etStudentNum = findViewById(R.id.studentNumber);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);

        databaseHelper = new DatabaseHelper(this);

        btnSignIn.setOnClickListener(v -> {
            String studentNumber = etStudentNum.getText().toString();
            String password = etPassword.getText().toString();

            if (studentNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (verifyCredentials(studentNumber, password)) {
                    Toast.makeText(SignInActivity.this, "Sign-In Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "You type too fast, therefore its wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean verifyCredentials(String studentNumber, String password) {
        Cursor cursor = databaseHelper.getAllUsers();
        boolean isValid = false;

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String dbStudentNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STUDENT_NUMBER));
            @SuppressLint("Range") String dbPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
            if (dbStudentNumber.equals(studentNumber) && dbPassword.equals(password)) {
                isValid = true;
                break;
            }
        }
        cursor.close();
        return isValid;
    }
}
