package com.example.eresapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eresapplication.R;

public class ViewComplaints extends AppCompatActivity {
Button btnBackToMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints);

        btnBackToMenu = findViewById(R.id.btnBackToMenu);

        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewComplaints.this,WelcomeScreen.class);
                startActivity(intent);
            }
        });
    }
}
