package com.example.greenpath;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.View;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class SuggestionActivity extends AppCompatActivity {

    private LinearLayout suggestionContainer;
    private Button btnBuildPlan;
    private FirebaseFirestore db;
    private String selectedDestination = "";
    private int userBudget = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        ImageView btnBackHomeSuggestion = findViewById(R.id.btnBackHomeSuggestion);
        btnBackHomeSuggestion.setOnClickListener(v -> {
            onBackPressed();   // ★ Correct navigation
        });


        db = FirebaseFirestore.getInstance();
        suggestionContainer = findViewById(R.id.suggestionContainer);
        btnBuildPlan = findViewById(R.id.btnBuildPlan);

        userBudget = getIntent().getIntExtra("budget", 0);
        String userActivity = getIntent().getStringExtra("activity");
        String activityFromPlanner = getIntent().getStringExtra("activity");


        if (userBudget == 0 || userActivity == null) {
            Toast.makeText(this, "Missing budget or activity!", Toast.LENGTH_SHORT).show();
            return;
        }

        fetchSuggestions(userBudget, userActivity.trim().toLowerCase());

        btnBuildPlan.setOnClickListener(v -> {
            if (selectedDestination.isEmpty()) {
                Toast.makeText(this, "Please tap a destination first!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(SuggestionActivity.this, SummaryActivity.class);
            intent.putExtra("destination", selectedDestination);
            intent.putExtra("budget", userBudget);
            intent.putExtra("activity", activityFromPlanner);   // ← add this
            startActivity(intent);
        });
    }

    private void fetchSuggestions(int budget, String userActivity) {
        db.collection("suggestions")
                .whereLessThanOrEqualTo("budget", budget)
                .whereArrayContains("activities_lower", userActivity.toLowerCase())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(this, "❌ No eco-destinations found for your filters.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String dest = document.getString("destination");
                        String desc = document.getString("description");
                        createDestinationCard(dest, desc);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // ✅ Create a clickable destination card dynamically
    private void createDestinationCard(String destination, String description) {

        // OUTER CARD LAYOUT (vertical)
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(20, 20, 20, 20);
        cardLayout.setBackground(createCardBackground(false));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 32);
        cardLayout.setLayoutParams(params);

        // LARGE IMAGE ON TOP
        ImageView img = new ImageView(this);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                380   // 🔥 Bigger image
        );
        img.setLayoutParams(imgParams);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Set correct image
        switch (destination.toLowerCase()) {
            case "rishikesh, uttarakhand":
                img.setImageResource(R.drawable.rishikesh);
                break;
            case "gokarna, karnataka":
                img.setImageResource(R.drawable.gokarna2);
                break;
            case "munnar, kerala":
                img.setImageResource(R.drawable.munnar);
                break;
            case "coorg, karnataka":
                img.setImageResource(R.drawable.coorg);
                break;
            case "sikkim":
                img.setImageResource(R.drawable.sikkim);
                break;
            case "wayanad, kerala":
                img.setImageResource(R.drawable.wayanad);
                break;
            case "auroville, tamil nadu":
                img.setImageResource(R.drawable.auroville);
                break;
            default:
                img.setImageResource(R.drawable.default_nature);
        }

        // TITLE BELOW IMAGE
        TextView tvTitle = new TextView(this);
        tvTitle.setText("🏞️ " + destination);
        tvTitle.setTextSize(20);
        tvTitle.setTextColor(Color.parseColor("#1B5E20"));
        tvTitle.setPadding(0, 16, 0, 8);

        // DESCRIPTION
        TextView tvDesc = new TextView(this);
        tvDesc.setText(description);
        tvDesc.setTextSize(15);
        tvDesc.setTextColor(Color.parseColor("#4E6C50"));
        tvDesc.setPadding(0, 0, 0, 12);

        // Add views to card
        cardLayout.addView(img);
        cardLayout.addView(tvTitle);
        cardLayout.addView(tvDesc);

        // ON CLICK: highlight + store
        cardLayout.setOnClickListener(v -> {
            selectedDestination = destination;
            Toast.makeText(this, "Selected: " + destination, Toast.LENGTH_SHORT).show();

            // Highlight effect
            for (int i = 0; i < suggestionContainer.getChildCount(); i++) {
                View child = suggestionContainer.getChildAt(i);
                child.setBackground(createCardBackground(false));
            }
            cardLayout.setBackground(createCardBackground(true));
        });

        suggestionContainer.addView(cardLayout);
    }



    // ✅ Helper to make cards rounded with green borders
    private GradientDrawable createCardBackground(boolean isSelected) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(24f);
        drawable.setStroke(4, isSelected ? Color.parseColor("#1B5E20") : Color.parseColor("#A5D6A7"));
        drawable.setColor(isSelected ? Color.parseColor("#C8E6C9") : Color.parseColor("#FFFFFF"));
        return drawable;
    }
}
