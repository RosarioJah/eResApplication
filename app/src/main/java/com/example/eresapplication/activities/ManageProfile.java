package com.example.eresapplication.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eresapplication.R;

public class ManageProfile extends AppCompatActivity {

    EditText etFirstName, etLastName, etStudentNo, etResidenceName, etUsername, etPassword, etConfirmPass;
    Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etResidenceName = findViewById(R.id.etResidenceName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etStudentNo = findViewById(R.id.etStudentNo);
        etConfirmPass = findViewById(R.id.etConfirmPassword);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String residenceName = etResidenceName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPass.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    etFirstName.setError("Valid First Name is required...");
                }
                if (TextUtils.isEmpty(lastName)) {
                    etLastName.setError("Valid Last Name is required...");
                }
                if (TextUtils.isEmpty(residenceName)) {
                    etResidenceName.setError("Valid Residence Name is required...");
                }
                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("Username is required...");
                }

                etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            etPassword.setError(null);
                        } else {
                            if (etPassword.toString().isEmpty()) {
                                {
                                    etPassword.setText("");
                                    etPassword.setError("Password Too Simple!");
                                }
                            } else {
                                etPassword.setError("Sorry Password Required!!");
                            }
                        }
                    }
                });

                etConfirmPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            etConfirmPass.setError(null);
                        }
                    }
                });
                Intent intent = new Intent(ManageProfile.this,WelcomeScreen.class);
                startActivity(intent);
            }
        });
    }
}


