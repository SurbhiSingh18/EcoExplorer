package com.example.greenpath;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoToSignup;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToSignup = findViewById(R.id.btnGoToSignup);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            checkUserHistory();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnGoToSignup.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class))
        );
    }

    private void checkUserHistory() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users")
                .document(userId)
                .collection("previous_trips")
                .limit(1) // check if any history exists
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.isEmpty()) {

                        // ⭐ NEW USER → Go to Planner
                        Toast.makeText(this, "Welcome! Let's plan your first trip 🌿", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, PlannerActivity.class));
                        finish();

                    } else {

                        // ⭐ OLD USER → Go to History Page
                        Toast.makeText(this, "Welcome back! Showing your previous trips...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HistoryActivity.class));
                        finish();
                    }
                });
    }
}
