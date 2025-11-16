package com.example.greenpath;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignup, btnGoToLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        btnSignup.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔐 Strong Password Check
            if (!isValidPassword(password)) {
                Toast.makeText(
                        SignupActivity.this,
                        "Password must contain:\n• 1 uppercase letter\n• 1 lowercase letter\n• 1 number\n• 1 special character\n• Min 8 characters",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, PlannerActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnGoToLogin.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class))
        );
    }

    // ✔ Validator Function
    private boolean isValidPassword(String password) {
        String passwordPattern =
                "^(?=.*[A-Z])" +        // at least 1 uppercase
                        "(?=.*[a-z])" +         // at least 1 lowercase
                        "(?=.*[0-9])" +         // at least 1 digit
                        "(?=.*[!@#$%^&*])" +    // at least 1 special character
                        ".{8,}$";               // minimum length 8

        return password.matches(passwordPattern);
    }
}
