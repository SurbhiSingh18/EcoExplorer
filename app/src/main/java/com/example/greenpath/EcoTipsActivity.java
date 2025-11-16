package com.example.greenpath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EcoTipsActivity extends AppCompatActivity {

    private TextView titleText, bestTimeText, firstTimersText, experiencedText, packingText, dosDontsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_tips);

        titleText = findViewById(R.id.titleText);
        firstTimersText = findViewById(R.id.firstTimersText);
        experiencedText = findViewById(R.id.experiencedText);
        packingText = findViewById(R.id.packingText);
        dosDontsText = findViewById(R.id.dosDontsText);
        bestTimeText = findViewById(R.id.bestTimeText);
        Button btnBackToSummary = findViewById(R.id.btnBackToSummary);
        Button btnGoHome = findViewById(R.id.btnGoHome);
        Button btnGoToPlanner = findViewById(R.id.btnGoToPlanner);
// Go back to Summary
        btnBackToSummary.setOnClickListener(v -> {
            Intent intent = new Intent(EcoTipsActivity.this, SummaryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

// Go home
        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(EcoTipsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnGoToPlanner.setOnClickListener(v -> {
            Intent intent = new Intent(EcoTipsActivity.this, PlannerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });


        String destination = getIntent().getStringExtra("destination");

        if (destination != null) {
            loadTips(destination.toLowerCase());
        }
    }


    private void loadTips(String destination) {

        switch (destination) {

            // ----------------------------------------------------------------------
            case "rishikesh, uttarakhand":
                titleText.setText("Eco Travel Tips – Rishikesh");

                bestTimeText.setText("February – June and September – November.\nPleasant weather ideal for rafting, yoga, and outdoor activities.");
                firstTimersText.setText(
                        "• Choose certified guides for rafting.\n" +
                                "• Wear proper life jackets.\n" +
                                "• Book eco-stays in Tapovan.\n" +
                                "• Attend early morning yoga.\n" +
                                "• Carry cash for local shops."
                );

                experiencedText.setText(
                        "• Trek to Neer Garh waterfalls.\n" +
                                "• Join Ganga cleanup drives.\n" +
                                "• Explore hidden cafés.\n" +
                                "• Attend peaceful Ganga Aarti."
                );

                packingText.setText(
                        "• Metal water bottle\n" +
                                "• Quick-dry towel\n" +
                                "• River-safe sunscreen\n" +
                                "• Trekking shoes\n" +
                                "• Power bank + torch"
                );

                dosDontsText.setText(
                        "✔ Respect silence at ghats\n" +
                                "✔ Support organic cafés\n" +
                                "✘ Don’t litter in Ganga\n" +
                                "✘ Don’t disrupt yoga groups"
                );
                break;


            // ----------------------------------------------------------------------
            case "munnar, kerala":
                titleText.setText("Eco Travel Tips – Munnar");

                bestTimeText.setText("September – May.\nCool temperatures, lush greenery, perfect for tea gardens and trekking.");
                firstTimersText.setText(
                        "• Book tea estate tours.\n" +
                                "• Start early for national parks.\n" +
                                "• Roads are curvy – carry medication.\n" +
                                "• Learn simple Malayalam phrases."
                );

                experiencedText.setText(
                        "• Trek Chokramudi Peak.\n" +
                                "• Visit spice gardens.\n" +
                                "• Stay in eco treehouses."
                );

                packingText.setText(
                        "• Jacket (cold climate)\n" +
                                "• Anti-leech socks\n" +
                                "• Binoculars\n" +
                                "• Natural mosquito repellent"
                );

                dosDontsText.setText(
                        "✔ Buy local spices\n" +
                                "✔ Respect wildlife\n" +
                                "✘ Don’t pluck tea leaves\n" +
                                "✘ Don’t feed animals"
                );
                break;


            // ----------------------------------------------------------------------
            case "gokarna, karnataka":
                titleText.setText("Eco Travel Tips – Gokarna");

                bestTimeText.setText("October – March.\nClear skies, calm beaches, ideal for yoga and water activities.");
                firstTimersText.setText(
                        "• Wear trekking shoes for beach routes.\n" +
                                "• Avoid walking alone at night.\n" +
                                "• Prefer coconut water over cold drinks.\n" +
                                "• Confirm auto prices beforehand."
                );

                experiencedText.setText(
                        "• Try Half-Moon → Paradise beach trek.\n" +
                                "• Join weekly beach cleanups.\n" +
                                "• Explore sunset cliff viewpoints."
                );

                packingText.setText(
                        "• Reusable bottle\n" +
                                "• Reef-safe sunscreen\n" +
                                "• Swimwear\n" +
                                "• Dry pouch\n" +
                                "• Beach mat"
                );

                dosDontsText.setText(
                        "✔ Support vegan cafés\n" +
                                "✔ Carry your trash back\n" +
                                "✘ Don’t light beach fires\n" +
                                "✘ Don’t disturb marine life"
                );
                break;


            // ----------------------------------------------------------------------
            case "sikkim":
                titleText.setText("Eco Travel Tips – Sikkim");

                bestTimeText.setText("March – June and September – December.\nSnow views in winter, colourful landscapes in summer.");
                firstTimersText.setText(
                        "• Plastic banned – bring refillable bottles.\n" +
                                "• Weather changes fast; carry poncho.\n" +
                                "• High altitude – hydrate well.\n" +
                                "• Roads narrow – keep extra travel time."
                );

                experiencedText.setText(
                        "• Visit organic farms.\n" +
                                "• Explore monasteries during prayers.\n" +
                                "• Hike offbeat trails near Rumtek."
                );

                packingText.setText(
                        "• Thermals + gloves\n" +
                                "• Water purifier bottle\n" +
                                "• Camera filters\n" +
                                "• Woolen socks"
                );

                dosDontsText.setText(
                        "✔ Stay in homestays\n" +
                                "✔ Follow monastery guidelines\n" +
                                "✘ Don’t trek alone\n" +
                                "✘ Don’t litter – strict laws"
                );
                break;


            // ----------------------------------------------------------------------
            case "coorg, karnataka":
                titleText.setText("Eco Travel Tips – Coorg");

                bestTimeText.setText("October – April.\nComfortable climate for plantations, trekking, and waterfalls.");
                firstTimersText.setText(
                        "• Carry trekking shoes.\n" +
                                "• Plantation areas are private – get permission.\n" +
                                "• Start early for waterfalls."
                );

                experiencedText.setText(
                        "• Try night safaris.\n" +
                                "• Explore Mandalpatti peak.\n" +
                                "• Support spice shops."
                );

                packingText.setText(
                        "• Rainproof jacket\n" +
                                "• Mosquito repellent\n" +
                                "• Cloth bags\n" +
                                "• Trekking shoes"
                );

                dosDontsText.setText(
                        "✔ Buy coffee from farmers\n" +
                                "✔ Reduce forest noise\n" +
                                "✘ Don’t disturb wildlife\n" +
                                "✘ Don’t enter rivers during monsoon"
                );
                break;


            // ----------------------------------------------------------------------
            case "wayanad, kerala":
                titleText.setText("Eco Travel Tips – Wayanad");

                bestTimeText.setText("October – May.\nCooler climate ideal for caves, camping, and forest tours.");
                firstTimersText.setText(
                        "• Leeches common – use socks.\n" +
                                "• Book Edakkal Caves early.\n" +
                                "• Avoid cliff edges during monsoon."
                );

                experiencedText.setText(
                        "• Try cycling tours.\n" +
                                "• Stay in treehouses.\n" +
                                "• Visit tribal cultural centers."
                );

                packingText.setText(
                        "• Flashlight\n" +
                                "• Trekking poles\n" +
                                "• Anti-leech socks\n" +
                                "• Organic mosquito repellent"
                );

                dosDontsText.setText(
                        "✔ Join guided treks\n" +
                                "✔ Try organic spice meals\n" +
                                "✘ Don’t trek alone\n" +
                                "✘ Don’t play loud music"
                );
                break;


            // ----------------------------------------------------------------------
            case "auroville, tamil nadu":
                titleText.setText("Eco Travel Tips – Auroville");

                bestTimeText.setText("November – March.\nPleasant weather for meditation, workshops, and cycling.");
                firstTimersText.setText(
                        "• Matrimandir requires advance booking.\n" +
                                "• Eco zones allow bicycles only.\n" +
                                "• Workshops fill fast – pre-register."
                );

                experiencedText.setText(
                        "• Volunteer in Sadhana Forest.\n" +
                                "• Join vegan cooking classes.\n" +
                                "• Explore forest trails early morning."
                );

                packingText.setText(
                        "• Cotton clothes\n" +
                                "• Cycling gear\n" +
                                "• Reusable bottle\n" +
                                "• Natural deodorant"
                );

                dosDontsText.setText(
                        "✔ Support local artisans\n" +
                                "✔ Follow silence rules at Matrimandir\n" +
                                "✘ No plastic bags allowed\n" +
                                "✘ Don’t disturb meditation areas"
                );
                break;


            // ----------------------------------------------------------------------
            default:
                titleText.setText("Eco Travel Tips");
                firstTimersText.setText("General eco travel tips will appear here.");
                experiencedText.setText("-");
                packingText.setText("-");
                dosDontsText.setText("-");
        }
    }
}
