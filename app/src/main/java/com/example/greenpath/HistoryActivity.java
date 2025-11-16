package com.example.greenpath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout historyContainer;
    private Button btnPlanNew, btnGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyContainer = findViewById(R.id.historyContainer);
        btnPlanNew = findViewById(R.id.btnPlanNew);
        btnGoHome = findViewById(R.id.btnGoHome);

        loadHistory();

        btnPlanNew.setOnClickListener(v -> {
            startActivity(new Intent(HistoryActivity.this, PlannerActivity.class));
            finish();
        });

        btnGoHome.setOnClickListener(v -> {
            startActivity(new Intent(HistoryActivity.this, MainActivity.class));
            finish();
        });
    }

    private void loadHistory() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(userId)
                .collection("previous_trips")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {

                    if (snapshot.isEmpty()) {
                        TextView noHistory = new TextView(this);
                        noHistory.setText("No previous trips found.\nStart planning now! 🌿");
                        noHistory.setTextSize(18);
                        noHistory.setTextColor(0xFF1B5E20);
                        noHistory.setGravity(Gravity.CENTER);
                        historyContainer.addView(noHistory);
                        return;
                    }

                    for (QueryDocumentSnapshot doc : snapshot) {
                        addTripCard(
                                doc.getString("activity"),
                                doc.getLong("budget").intValue(),
                                doc.getString("suggestedDestination"),
                                doc.getString("chosenDestination")
                        );
                    }
                });
    }

    private void addTripCard(String activity, int budget, String suggested, String chosen) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(30, 30, 30, 30);

        card.setBackgroundResource(R.drawable.rounded_card);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 30);
        card.setLayoutParams(params);

        TextView details = new TextView(this);
        details.setText(
                "📌 Activity: " + activity + "\n" +
                        "💰 Budget: ₹" + budget + "\n" +
                        "🗺 Suggested: " + suggested + "\n" +
                        "✔ Chosen: " + chosen
        );
        details.setTextColor(0xFF2E7D32);
        details.setTextSize(16);

        card.addView(details);

        // Click card → open summary of chosen destination
        card.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, SummaryActivity.class);
            intent.putExtra("destination", chosen);
            intent.putExtra("budget", budget);
            intent.putExtra("activity", activity);
            startActivity(intent);
        });

        historyContainer.addView(card);
    }
}
