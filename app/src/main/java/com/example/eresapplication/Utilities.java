package com.example.eresapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.example.eresapplication.Classes.Caretaker;
import com.example.eresapplication.Classes.HouseCommittee;
import com.example.eresapplication.Classes.Mentor;
import com.example.eresapplication.Classes.ResManager;
import com.example.eresapplication.Classes.Student;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Utilities extends Application {


    public static Student student;
    public static ResManager resManager;
    public static HouseCommittee houseCommittee;
    public static Mentor mentor;
    public static Caretaker caretaker;

    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
    }


    public static String getInputText(TextInputEditText inputText) {
        return inputText.getText().toString().trim();
    }

    public static boolean validateInput(TextInputLayout[] inputLayouts, String[] editTextErrors, TextInputEditText... inputTexts) {
        int errors = 0;

        for (int i = 0; i < inputTexts.length; ++i) {
            if (getInputText(inputTexts[i]).isEmpty()) {
                inputLayouts[i].setError(editTextErrors[i]);

                ++errors;
            } else {
                inputLayouts[i].setError(null);
            }
        }
        return errors == 0;
    }

    public static void message(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void StoreUserCredentials(String email, String password) {
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();

            if (editor != null) {
                editor.putString("username", email);
                editor.putString("password", password);
                editor.apply();
            }
        }
    }

    public static Student GetStoredCredentials() {
        String username;
        String password;

        Student student = null;

        if (preferences != null) {
            if (preferences.contains("username") && preferences.contains("password")) {
                username = preferences.getString("email", null);
                password = preferences.getString("password", null);
                student = new Student();

                return student;
            }
        }

        return student;
    }
}
