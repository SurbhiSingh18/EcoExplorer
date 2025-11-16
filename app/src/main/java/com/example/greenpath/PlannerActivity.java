package com.example.greenpath;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class PlannerActivity extends AppCompatActivity {

    private Button btnGetSuggestions;
    private EditText etBudget;
    private Spinner spinnerActivity;
    private FirebaseFirestore db;
    private ArrayList<String> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        // Back to home button
        ImageView btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(PlannerActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        etBudget = findViewById(R.id.etBudget);
        spinnerActivity = findViewById(R.id.spinnerActivity);
        btnGetSuggestions = findViewById(R.id.btnGetSuggestions);

        db = FirebaseFirestore.getInstance();
        loadActivitiesFromFirestore();

        btnGetSuggestions.setOnClickListener(v -> {

            String budgetText = etBudget.getText().toString().trim();
            String selectedActivity = spinnerActivity.getSelectedItem().toString().trim();

            // ✅ Check if budget is empty
            if (budgetText.isEmpty()) {
                Toast.makeText(this, "Please enter your budget!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate numeric budget
            int budget;
            try {
                budget = Integer.parseInt(budgetText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter a valid number for budget!", Toast.LENGTH_SHORT).show();
                return;
            }

            // ❗ NEW: Minimum budget validation
            if (budget < 5000) {
                Toast.makeText(this, "Minimum budget required is ₹5000", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Check if user selected a valid activity
            if (selectedActivity.equals("Select your activity 🌿")) {
                Toast.makeText(this, "Please select an activity!", Toast.LENGTH_SHORT).show();
                return;
            }

            // All good → go to next page
            Intent intent = new Intent(PlannerActivity.this, SuggestionActivity.class);
            intent.putExtra("budget", budget);
            intent.putExtra("activity", selectedActivity.toLowerCase());
            startActivity(intent);
        });
    }

    private void loadActivitiesFromFirestore() {
        db.collection("suggestions")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    HashSet<String> uniqueActivities = new HashSet<>();

                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        ArrayList<String> activities = (ArrayList<String>) doc.get("activities_lower");
                        if (activities != null) {
                            uniqueActivities.addAll(activities);
                        }
                    }

                    activityList.clear();
                    activityList.addAll(uniqueActivities);

                    // 🔠 Sort alphabetically
                    Collections.sort(activityList);

                    // Add first item
                    activityList.add(0, "Select your activity 🌿");

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this, android.R.layout.simple_spinner_item, activityList
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerActivity.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load activities: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
