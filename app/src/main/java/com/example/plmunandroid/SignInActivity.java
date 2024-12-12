package com.example.plmunandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {

    private EditText etStudentNum, etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;
    private DatabaseHelper databaseHelper;
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;


    private ImageView googleBTN;
    private void navigateToMainActivity(String email) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_email", email);
        startActivity(intent);
        finish(); // Optional: close current activity
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String email = preferences.getString("user_email", null); // Retrieve saved email from SharedPreferences

        if (firebaseAuth.getCurrentUser() != null || email != null) {
            navigateToMainActivity(email); // Pass email if it's stored in SharedPreferences
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this);

        // Initialize views
        etStudentNum = findViewById(R.id.studentNumber);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        googleBTN = findViewById(R.id.btnGoogleSignIn);

        databaseHelper = new DatabaseHelper(this);

        // Initialize FirebaseAuth
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // Now you can use FirebaseAuth or other Firebase features
        auth = FirebaseAuth.getInstance();

        // Initialize GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Google Sign-In Button
        googleBTN.setOnClickListener(v -> signInWithGoogle());

        // Regular Sign-In Button
        btnSignIn.setOnClickListener(v -> signInWithCredentials());

        // Sign-Up TextView Click
        tvSignUp.setOnClickListener(v -> navigateToSignUp());
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInWithCredentials() {
        String studentNumber = etStudentNum.getText().toString();
        String password = etPassword.getText().toString();

        if (studentNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (verifyCredentials(studentNumber, password)) {
                // Retrieve email from database
                String email = getEmailForStudentNumber(studentNumber);

                // Save both student number and email
                saveUserDetailsToSharedPreferences(studentNumber, email);

                Toast.makeText(SignInActivity.this, "Sign-In Successful", Toast.LENGTH_SHORT).show();
                navigateToMainActivity(email);
            } else {
                Toast.makeText(SignInActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getEmailForStudentNumber(String studentNumber) {
        Cursor cursor = databaseHelper.getAllUsers();
        String email = "";

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String dbStudentNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STUDENT_NUMBER));
            @SuppressLint("Range") String dbEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
            if (dbStudentNumber.equals(studentNumber)) {
                email = dbEmail;
                break;
            }
        }
        cursor.close();
        return email;
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


    private void navigateToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish(); // Optional: close current activity
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SignInActivity.this);
                        if (account != null) {
                            String email = account.getEmail();
                            // Extract student number if possible (you might need to modify this)
                            String studentNumber = extractStudentNumberFromEmail(email);

                            // Save both email and student number
                            saveUserDetailsToSharedPreferences(studentNumber, email);
                            navigateToMainActivity(email);
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String extractStudentNumberFromEmail(String email) {
        // This is a simple extraction method. Adjust based on your email format
        // Assumes email is like 22143089@plmun.edu.ph
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return "";
    }

    private void saveEmailToSharedPreferences(String email) {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_email", email);  // Save email to SharedPreferences
        editor.apply();
    }

    private void saveUserDetailsToSharedPreferences(String studentNumber, String email) {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("student_number", studentNumber);
        editor.putString("user_email", email);
        editor.apply();
    }
}
