package com.example.eresapplication.activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.eresapplication.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import static com.example.eresapplication.Classes.ApplicationClass.user;

public class Register extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$");
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etFirstName, etLastName, etStudentNo, etResidenceName, etOccupation, etUsername, etPassword, etConfirmPass;
    Button btnRegisterNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etResidenceName = findViewById(R.id.etResidenceName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etStudentNo = findViewById(R.id.etStudentNo);
        etConfirmPass = findViewById(R.id.etConfirmPassword);
        etOccupation = findViewById(R.id.etOccupation);
        btnRegisterNewUser = findViewById(R.id.btnRegisterNewUser);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register Profile");

        btnRegisterNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String residenceName = etResidenceName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String studentNo = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPass.getText().toString().trim();
                String occupation = etOccupation.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    etFirstName.setError("First Name required...");
                }
                    if (TextUtils.isEmpty(lastName)) {
                        etLastName.setError("Last Name required...");
                    }
                        if (TextUtils.isEmpty(residenceName)) {
                            etResidenceName.setError("Residence Name required...");
                        }
                            if (TextUtils.isEmpty(studentNo)) {
                                etStudentNo.setError("Student Number required...");
                            }
                                if (TextUtils.isEmpty(username)) {
                                    etUsername.setError("Username required...");
                                }
                                    if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                                        etUsername.setError("Invalid Username, eg: user@12345...");
                                    }
                                        if (TextUtils.isEmpty(password)) {
                                            etPassword.setError("Password required...");
                                        }
                                            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                                                etPassword.setError("Password too simple,try again");
                                            }
                                                if (TextUtils.isEmpty(confirmPassword)) {
                                                    etConfirmPass.setError("Please confirm your password...");
                                                }
                                                    if (!confirmPassword.matches(password))
                                                    {
                                                        etConfirmPass.setError("Password do not match...");
                                                    }
                                                    if (confirmPassword.equals(password))
                                                    {
                                                        BackendlessUser user = new BackendlessUser();
                                                        user.setEmail(username);
                                                        user.setPassword(password);
                                                        user.setProperty("firstName",firstName);
                                                        user.setProperty("lastName",lastName);
                                                        user.setProperty("studentNumber",studentNo);
                                                        user.setProperty("residenceName",residenceName);

                                                        showProgress(true);
                                                        tvLoad.setText("Registering new user!!!");


                                                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                                                            @Override
                                                            public void handleResponse(BackendlessUser response) {
                                                                startActivity(new Intent(Register.this,Login.class));

                                                                Toast.makeText(Register.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();
                                                                Register.this.finish();
                                                            }

                                                            @Override
                                                            public void handleFault(BackendlessFault fault) {
                                                                Toast.makeText(Register.this, "Error -> " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                                                showProgress(false);
                                                                Register.this.finish();
                                                            }
                                                        });
                                                    }
                                                }
                /**
                 * Shows the progress UI and hides the login form.
                 */
                @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                private void showProgress ( final boolean show){
                    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
                    // for very easy animations. If available, use these APIs to fade-in
                    // the progress spinner.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                            }
                        });

                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        mProgressView.animate().setDuration(shortAnimTime).alpha(
                                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                            }
                        });

                        tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                        tvLoad.animate().setDuration(shortAnimTime).alpha(
                                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                            }
                        });
                    } else {
                        // The ViewPropertyAnimator APIs are not available, so simply show
                        // and hide the relevant UI components.
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                }
            });
        }
    }
