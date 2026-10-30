package com.example.practicalfive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Municipality extends AppCompatActivity {

    private TextView nameView;
    private TextView infoView;
    private ImageView imageView;
    private Button visitButton;

    private String municipalityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);
        View root = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameView = findViewById(R.id.municipalityName);
        infoView = findViewById(R.id.municipalityInfo);
        imageView = findViewById(R.id.municipalityImage);
        visitButton = findViewById(R.id.visitSiteButton);

        String extra = getIntent().getStringExtra(MainActivity.EXTRA_MUNICIPALITY);
        municipalityName = TextProcessor.processMunicipality(extra);
        if (municipalityName == null) {
            Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        showMunicipality();
    }

    private void showMunicipality() {
        int colorRes;
        int imageRes;
        int summaryIndex;
        switch (municipalityName) {
            case "Shanghai":
                colorRes = R.color.municipality_shanghai;
                imageRes = R.drawable.shanghai;
                summaryIndex = 1;
                break;
            case "Chongqing":
                colorRes = R.color.municipality_chongqing;
                imageRes = R.drawable.chongqing;
                summaryIndex = 2;
                break;
            case "Tianjin":
                colorRes = R.color.municipality_tianjin;
                imageRes = R.drawable.tianjin;
                summaryIndex = 3;
                break;
            default:
                colorRes = R.color.municipality_beijing;
                imageRes = R.drawable.beijing;
                summaryIndex = 0;
                break;
        }

        int color = ContextCompat.getColor(this, colorRes);
        int lightText = ContextCompat.getColor(this, R.color.color_text_light);
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);

        nameView.setText(municipalityName);
        nameView.setBackgroundColor(color);
        nameView.setTextColor(lightText);

        imageView.setImageResource(imageRes);
        imageView.setContentDescription(municipalityName);

        infoView.setText(summaries[summaryIndex]);
        infoView.setTextColor(color);

        visitButton.setBackgroundTintList(ColorStateList.valueOf(color));
        visitButton.setTextColor(lightText);
        visitButton.setOnClickListener(v -> openWebsite());
    }

    private int websiteResource() {
        switch (municipalityName) {
            case "Shanghai":
                return R.string.website_shanghai;
            case "Chongqing":
                return R.string.website_chongqing;
            case "Tianjin":
                return R.string.website_tianjin;
            default:
                return R.string.website_beijing;
        }
    }

    private void openWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(websiteResource())));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.visit_failed, Toast.LENGTH_LONG).show();
        }
    }
}
