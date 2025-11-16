package com.example.greenpath;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import android.text.Html;
import android.text.method.LinkMovementMethod;

import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SummaryActivity extends AppCompatActivity {

    private TextView tvDestination, tvPlanSummary, tvEcoTips;
    private ImageView imgDestination;

    private Handler handler;
    private Runnable runnable;

    private void saveTripHistory(String activity, int budget, String suggested, String chosen) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> trip = new HashMap<>();
        trip.put("activity", activity);
        trip.put("budget", budget);
        trip.put("suggestedDestination", suggested);
        trip.put("chosenDestination", chosen);
        trip.put("timestamp", System.currentTimeMillis());

        db.collection("users")
                .document(userId)
                .collection("previous_trips")
                .add(trip)
                .addOnSuccessListener(doc -> {})
                .addOnFailureListener(e -> {});
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ImageView btnBackHomeSummary = findViewById(R.id.btnBackHomeSummary);

        btnBackHomeSummary.setOnClickListener(v -> {
            Intent intent = new Intent(SummaryActivity.this, SuggestionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        tvDestination = findViewById(R.id.tvDestination);
        tvPlanSummary = findViewById(R.id.tvPlanSummary);
        tvEcoTips = findViewById(R.id.tvEcoTips);
        imgDestination = findViewById(R.id.imgDestination);


        tvPlanSummary.setMovementMethod(LinkMovementMethod.getInstance());
        tvEcoTips.setMovementMethod(LinkMovementMethod.getInstance());

        String destination = getIntent().getStringExtra("destination");
        int budget = getIntent().getIntExtra("budget", 0);
        String activity = getIntent().getStringExtra("activity");

        if (destination == null || budget == 0) {
            Toast.makeText(this, "Missing trip details!", Toast.LENGTH_SHORT).show();
            return;
        }

        tvDestination.setText("🌿 Eco Plan for " + destination);

        Button btnMoreEcoTips = findViewById(R.id.btnMoreEcoTips);
        btnMoreEcoTips.setOnClickListener(v -> {
            Intent intent = new Intent(SummaryActivity.this, EcoTipsActivity.class);
            intent.putExtra("destination", destination);
            startActivity(intent);
        });
        // Display different eco plans per destination
        showEcoPlan(destination, budget);
    }

    private void showEcoPlan(String destination, int budget) {
        switch (destination.toLowerCase()) {

            // ----------------------------------------------------------
            // ⭐ RISHIKESH
            // ----------------------------------------------------------
            case "rishikesh, uttarakhand":

                saveTripHistory(
                        getIntent().getStringExtra("activity"),
                        budget,
                        "Rishikesh, Uttarakhand",
                        "Rishikesh, Uttarakhand"
                );

                int[] rishikeshPhotos = {
                        R.drawable.rishikesh,
                        R.drawable.rishikesh2,
                        R.drawable.rishikesh3
                };

                final int[] currentIndex = {0};

                imgDestination.setImageResource(rishikeshPhotos[0]);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentIndex[0] = (currentIndex[0] + 1) % rishikeshPhotos.length;
                        imgDestination.setImageResource(rishikeshPhotos[currentIndex[0]]);
                        handler.postDelayed(this, 2000); // change image every 2 seconds
                    }
                };

                handler.postDelayed(runnable, 2000);

                tvPlanSummary.setText(Html.fromHtml(
                        "💰 <b>Estimated Budget:</b> ₹" + budget + "<br><br>" +

                                "🚍 <b>HOW TO REACH:</b><br>" +
                                "• Fly to Dehradun (DED Airport), then take a 45–60 min shared cab to Rishikesh.<br>" +
                                "• Or take a train to Haridwar, then shared auto/cab (₹150–300).<br><br>" +

                                "🏡 <b>ECO STAY OPTIONS:</b><br>" +
                                "• Live Free Hostel <a href='https://www.google.com/maps/search/?api=1&query=Live+Free+Hostel+Rishikesh'>Open Map</a><br>" +
                                "• Swiss Cottage & Spa by Salvus <a href='https://www.google.com/maps/search/?api=1&query=Swiss+Cottage+and+Spa+Rishikesh'>Open Map</a><br><br>" +

                                "🧘‍♀️ <b>ACTIVITIES:</b><br>" +
                                "• Ganga riverside yoga<br>" +
                                "• River rafting<br>" +
                                "• Nature walks in Rajaji Forest<br><br>" +

                                "🥗 <b>FOOD:</b><br>" +
                                "• Ayurvedic meals & organic cafés.<br>"
                ));


                tvEcoTips.setText(
                        "• Carry a refillable water bottle.\n" +
                                "• Avoid plastic waste along the Ganges.\n" +
                                "• Support local organic cafés.\n"
                );
                break;


            // ----------------------------------------------------------
            // ⭐ MUNNAR
            // ----------------------------------------------------------
            case "munnar, kerala":

                saveTripHistory(
                        getIntent().getStringExtra("activity"),
                        budget,
                        "Munnar, Kerala",
                        "Munnar, Kerala"
                );

                int[] munnarPhotos = {
                        R.drawable.munnar,
                        R.drawable.munnar2,
                        R.drawable.munnar3
                };

                final int[] munnarIndex = {0};

                imgDestination.setImageResource(munnarPhotos[0]);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        munnarIndex[0] = (munnarIndex[0] + 1) % munnarPhotos.length;
                        imgDestination.setImageResource(munnarPhotos[munnarIndex[0]]);
                        handler.postDelayed(this, 2000);
                    }
                };

                handler.postDelayed(runnable, 2000);

                tvPlanSummary.setText(Html.fromHtml(
                        "💰 <b>Estimated Budget:</b> ₹" + budget + "<br><br>" +

                                "🚍 <b>HOW TO REACH:</b><br>" +
                                "• Fly to Kochi Airpot → shared cab (4 hrs) to Munnar.<br>" +
                                "• Or train to Ernakulam → bus to Munnar.<br><br>" +

                                "🏡 <b>ECO STAY OPTIONS:</b><br>" +
                                "• Sitaram Mountain Retreat <a href='https://www.google.com/maps/search/?api=1&query=Kaivalyam+Wellness+Retreat+Munnar'>Open Map</a><br>" +
                                "• Nature Zone Jungle Resort <a href='https://www.google.com/maps/search/?api=1&query=Nature+Zone+Jungle+Resort+Munnar'>Open Map</a><br><br>" +

                                "🌄 <b>ACTIVITIES:</b><br>" +
                                "• Tea estate visits<br>" +
                                "• Wildlife sanctuary<br>" +
                                "• Forest camping<br><br>" +

                                "🥗 <b>FOOD:</b><br>" +
                                "• Kerala spice meals & organic lunches.<br>"
                ));

                tvEcoTips.setText("• Avoid littering in tea gardens.\n• Support local spice farmers.\n• Choose guided treks.");
                break;

            // ----------------------------------------------------------
            // ⭐ GOKARNA
            // ----------------------------------------------------------
            case "gokarna, karnataka":

                saveTripHistory(
                        getIntent().getStringExtra("activity"),
                        budget,
                        "Gokarna, Karnataka",
                        "Gokarna, Karnataka"
                );

                int[] gokarnaPhotos = {
                        R.drawable.gokarna,
                        R.drawable.gokarna2,
                        R.drawable.gokarna3
                };

                final int[] gokarnaIndex = {0};

                imgDestination.setImageResource(gokarnaPhotos[0]);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        gokarnaIndex[0] = (gokarnaIndex[0] + 1) % gokarnaPhotos.length;
                        imgDestination.setImageResource(gokarnaPhotos[gokarnaIndex[0]]);
                        handler.postDelayed(this, 2000);
                    }
                };

                handler.postDelayed(runnable, 2000);

                tvPlanSummary.setText(Html.fromHtml(
                        "💰 <b>Estimated Budget:</b> ₹" + budget + "<br><br>" +

                                "🚍 <b>HOW TO REACH:</b><br>" +
                                "• Train to Gokarna Road → auto to beaches.<br>" +
                                "• From Goa Airport → cab (2.5 hrs).<br><br>" +

                                "🏡 <b>ECO STAY OPTIONS:</b><br>" +
                                "• Zostel Gokarna <a href='https://www.google.com/maps/search/?api=1&query=Zostel+Gokarna'>Open Map</a><br>" +
                                "• Namaste Yoga Farm <a href='https://www.google.com/maps/search/?api=1&query=Namaste+Yoga+Farm+Gokarna'>Open Map</a><br><br>" +

                                "🏖️ <b>ACTIVITIES:</b><br>" +
                                "• Beach cleanups<br>" +
                                "• Yoga on Kudle Beach<br>" +
                                "• Visit vegan cafes<br><br>" +

                                "🥗 <b>FOOD:</b><br>" +
                                "• Vegan beach cafés & local meals.<br>"
                ));

                tvEcoTips.setText("• Use reef-safe sunscreen.\n• Avoid plastic on beaches.\n• Respect temple customs.");
                break;

            // ----------------------------------------------------------
            // ⭐ SIKKIM
            // ----------------------------------------------------------
            case "sikkim":

                saveTripHistory(
                        getIntent().getStringExtra("activity"),
                        budget,
                        "Sikkim",
                        "Sikkim"
                );

                int[] sikkimPhotos = {
                        R.drawable.sikkim,
                        R.drawable.sikkim2,
                        R.drawable.sikkim3
                };

                final int[] sikkimIndex = {0};

                imgDestination.setImageResource(sikkimPhotos[0]);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        sikkimIndex[0] = (sikkimIndex[0] + 1) % sikkimPhotos.length;
                        imgDestination.setImageResource(sikkimPhotos[sikkimIndex[0]]);
                        handler.postDelayed(this, 2000);
                    }
                };

                handler.postDelayed(runnable, 2000);

                tvPlanSummary.setText(Html.fromHtml(
                        "💰 <b>Estimated Budget:</b> ₹" + budget + "<br><br>" +

                                "🚍 <b>HOW TO REACH:</b><br>" +
                                "• Fly to Bagdogra Airpot → shared jeep (4 hrs) to Gangtok.<br>" +
                                "• Train to NJP Station → jeep to Sikkim.<br><br>" +

                                "🏡 <b>ECO STAY OPTIONS:</b><br>" +
                                "• Denzong Regency <a href='https://www.google.com/maps/search/?api=1&query=Denzong+Regency+Gangtok'>Open Map</a><br>" +
                                "• Bamboo Grove Retreat <a href='https://www.google.com/maps/search/?api=1&query=Bamboo+Grove+Retreat+Gangtok'>Open Map</a><br><br>" +

                                "⛰️ <b>ACTIVITIES:</b><br>" +
                                "• Monastery trails<br>" +
                                "• Organic farm tours<br>" +
                                "• Himalayan hikes<br><br>" +

                                "🥗 <b>FOOD:</b><br>" +
                                "• Organic momos & thukpa.<br>"
                ));

                tvEcoTips.setText("• Sikkim is plastic-free.\n• Avoid loud noise in monastery zones.\n• Hire eco-certified guides.");
                break;

            // ----------------------------------------------------------
            // ⭐ COORG
            // ----------------------------------------------------------
            case "coorg, karnataka":

                saveTripHistory(
                        getIntent().getStringExtra("activity"),
                        budget,
                        "Coorg, Karnataka",
                        "Coorg, Karnataka"
                );

                int[] coorgPhotos = {
                        R.drawable.coorg,
                        R.drawable.coorg2,
                        R.drawable.coorg3
                };

                final int[] coorgIndex = {0};

                imgDestination.setImageResource(coorgPhotos[0]);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        coorgIndex[0] = (coorgIndex[0] + 1) % coorgPhotos.length;
                        imgDestination.setImageResource(coorgPhotos[coorgIndex[0]]);
                        handler.postDelayed(this, 2000);
                    }
                };

                handler.postDelayed(runnable, 2000);

                tvPlanSummary.setText(Html.fromHtml(
                        "💰 <b>Estimated Budget:</b> ₹" + budget + "<br><br>" +

                                "🚍 <b>HOW TO REACH:</b><br>" +
                                "• Train to Mysuru → bus/cab to Coorg (2.5 hrs).<br>" +
                                "• From Bengaluru → direct buses to Madikeri.<br><br>" +

                                "🏡 <b>ECO STAY OPTIONS:</b><br>" +
                                "• Old Kent Estates <a href='https://www.google.com/maps/search/?api=1&query=Old+Kent+Estates+Coorg'>Open Map</a><br>" +
                                "• Coorg Wilderness Resort <a href='https://www.google.com/maps/search/?api=1&query=Coorg+Wilderness+Resort'>Open Map</a><br><br>" +

                                "🌳 <b>ACTIVITIES:</b><br>" +
                                "• Plantation tours<br>" +
                                "• Eco trekking<br>" +
                                "• Riverside camping<br><br>" +

                                "🥗 <b>FOOD:</b><br>" +
                                "• Coorgi meals & organic produce.<br>"
                ));

                tvEcoTips.setText("• Reduce noise near forests.\n• Buy coffee from local farmers.\n• Carry cloth bags.");
                break;

            // ----------------------------------------------------------
            // ⭐ WAYANAD
            // ----------------------------------------------------------
            case "wayanad, kerala":

                saveTripHistory(
                        getIntent().getStringExtra("activity"),
                        budget,
                        "Wayanad, Kerala",
                        "Wayanad, Kerala"
                );

                int[] wayanadPhotos = {
                        R.drawable.wayanad,
                        R.drawable.wayanad2,
                        R.drawable.wayanad3
                };

                final int[] wayanadIndex = {0};

                imgDestination.setImageResource(wayanadPhotos[0]);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        wayanadIndex[0] = (wayanadIndex[0] + 1) % wayanadPhotos.length;
                        imgDestination.setImageResource(wayanadPhotos[wayanadIndex[0]]);
                        handler.postDelayed(this, 2000);
                    }
                };

                handler.postDelayed(runnable, 2000);

                tvPlanSummary.setText(Html.fromHtml(
                        "💰 <b>Estimated Budget:</b> ₹" + budget + "<br><br>" +

                                "🚍 <b>HOW TO REACH:</b><br>" +
                                "• Fly to Kozhikode Airpot → cab (2 hrs) to Wayanad.<br>" +
                                "• Train to Kozhikode → bus/shared taxi.<br><br>" +

                                "🏡 <b>ECO STAY OPTIONS:</b><br>" +
                                "• Banasura Hill Resort <a href='https://www.google.com/maps/search/?api=1&query=Banasura+Hill+Resort+Wayanad'>Open Map</a><br>" +
                                "• Wayanad Wild CGH Earth <a href='https://www.google.com/maps/search/?api=1&query=Wayanad+Wild+CGH+Earth'>Open Map</a><br><br>" +

                                "🌿 <b>ACTIVITIES:</b><br>" +
                                "• Forest trekking<br>" +
                                "• Edakkal Caves<br>" +
                                "• Organic farming<br><br>" +

                                "🥗 <b>FOOD:</b><br>" +
                                "• Organic spice meals.<br>"
                ));

                tvEcoTips.setText("• Avoid plastic.\n• Support eco-stays.\n• Respect local culture.");
                break;

            // ----------------------------------------------------------
            // ⭐ AUROVILLE
            // ----------------------------------------------------------
            case "auroville, tamil nadu":

                saveTripHistory(
                        getIntent().getStringExtra("activity"),
                        budget,
                        "Auroville, Tamil Nadu",
                        "Auroville, Tamil Nadu"
                );

                int[] aurovillePhotos = {
                        R.drawable.auroville,
                        R.drawable.auroville2,
                        R.drawable.auroville3
                };

                final int[] aurovilleIndex = {0};

                imgDestination.setImageResource(aurovillePhotos[0]);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        aurovilleIndex[0] = (aurovilleIndex[0] + 1) % aurovillePhotos.length;
                        imgDestination.setImageResource(aurovillePhotos[aurovilleIndex[0]]);
                        handler.postDelayed(this, 2000);
                    }
                };

                handler.postDelayed(runnable, 2000);

                tvPlanSummary.setText(Html.fromHtml(
                        "💰 <b>Estimated Budget:</b> ₹" + budget + "<br><br>" +

                                "🚍 <b>HOW TO REACH:</b><br>" +
                                "• Fly to Chennai → bus/cab (3 hrs) to Auroville.<br>" +
                                "• From Pondicherry → auto to Auroville.<br><br>" +

                                "🏡 <b>ECO STAY OPTIONS:</b><br>" +
                                "• Auroville Bamboo Residency <a href='https://www.google.com/maps/search/?api=1&query=Auroville+Bamboo+Residency'>Open Map</a><br>" +
                                "• Gratitude Heritage Home <a href='https://www.google.com/maps/search/?api=1&query=Gratitude+Heritage+Home+Auroville'>Open Map</a><br><br>" +

                                "☀️ <b>ACTIVITIES:</b><br>" +
                                "• Meditation at Matrimandir<br>" +
                                "• Organic farm workshops<br>" +
                                "• Sustainability classes<br>" +
                                "• Green zone cycling<br><br>" +

                                "🥗 <b>FOOD:</b><br>" +
                                "• Vegan cafés & organic meals.<br>"
                ));

                tvEcoTips.setText("• Plastic is banned.\n• Support artisans.\n• Join eco-projects.");
                break;

            // ----------------------------------------------------------
            default:
                imgDestination.setImageResource(R.drawable.default_nature);
                tvPlanSummary.setText(
                        "💰 Estimated Budget: ₹" + budget +
                                "\n\nA beautiful eco-friendly journey awaits you!"
                );
                tvEcoTips.setText(
                        "🌿 Travel responsibly, reduce waste, and support local communities."
                );

        }
    } @Override
    protected void onPause() {
        super.onPause();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
