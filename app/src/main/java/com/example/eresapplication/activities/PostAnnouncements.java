package com.example.eresapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eresapplication.R;

public class PostAnnouncements extends AppCompatActivity {

    Button btnPostAnnouncements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_announcements);

        btnPostAnnouncements = findViewById(R.id.btnSubmitAnnouncement);

        btnPostAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(PostAnnouncements.this, "Successfully  posted your Announcement!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PostAnnouncements.this, WelcomeScreen.class);
                startActivity(intent);
            }
        });
    }
}
