package com.example.eresapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eresapplication.R;

public class BrowseAnnouncements extends AppCompatActivity {

    TextView tvAnnouncements;
    Button btnBackToMainScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_announcements);

        tvAnnouncements = findViewById(R.id.tvAnnouncements);
        btnBackToMainScreen = findViewById(R.id.btnBackToMainScreen);

        btnBackToMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseAnnouncements.this,WelcomeScreen.class);
                startActivity(intent);
            }
        });
    }
}
