package com.example.eresapplication.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.eresapplication.Classes.ApplicationClass;
import com.example.eresapplication.Classes.HouseCommittee;
import com.example.eresapplication.Classes.ResManager;
import com.example.eresapplication.R;
import com.example.eresapplication.Utilities;

public class Login extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etUsername, etPassword, etResetMail;
    RadioGroup rgOccupations;
    Button btnRegister, btnLogin;
    TextView tvReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("eRes");
        getSupportActionBar().hide();



        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        etUsername = findViewById(R.id.etUserName);
        etPassword =  findViewById(R.id.etPassword);
        rgOccupations = findViewById(R.id.rgOccupations);
        tvReset = findViewById(R.id.tvReset);


        //CHECK IF USER IS STILL LOGGED IN and KEEP THEM LOGGED IN
        tvLoad.setText("Checking User...Please be patient");
        showProgress(false);

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response)
            {
                if(response)
                {
                    tvLoad.setText("User Authenticated... Signing In");
                    String userObjectID = UserIdStorageFactory.instance().getStorage().get();
                    Backendless.Data.of(BackendlessUser.class).findById(userObjectID, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response)
                        {
                            startActivity(new Intent(Login.this, WelcomeScreen.class));
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) 
                        {
                            Toast.makeText(Login.this, "Error -> "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    });
                }
                else
                {
                    showProgress(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                Toast.makeText(Login.this, "Error -> "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });//END KEEP USER LOGGED IN

        //temporary variables to get the username and password texts
        //used under clicked Radio Buttons respectively

        final String userName = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        //configuring the buttons
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivityIntent = new Intent(Login.this,Register.class);
                startActivity(registerActivityIntent);
            }
        });

        //first button (Reset username and send reset link to an email)
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dialog for entering the user's email
                AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                dialog.setMessage("Enter an email address ypou want to receive the reset link on: ");

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_view,null);
                dialog.setView(dialogView);

                etResetMail = dialogView.findViewById(R.id.etResetMail);
                dialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        
                        if(etResetMail.getText().toString().isEmpty())
                        {
                            Toast.makeText(Login.this, "Please enter an Email Address", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            tvLoad.setText("Sending info to email address");
                            showProgress(true);

                        }
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });


        /*radio buttons configuration
        if CareTaker Radio button is clicked..... The login button will jump to the CareTaker activity
         */
        rgOccupations.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, final int checkedId) {
                if(checkedId == R.id.rbCaretaker)
                {
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(etUsername.getText().toString().isEmpty()|| etPassword.getText().toString().isEmpty())
                            {
                                Toast.makeText(Login.this, "Specify Login Credentials and Occupation", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                showProgress(true);
                                tvLoad.setText("Signing In as CareTaker....Please wait");

                                Backendless.UserService.login(userName, password, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response)
                                    {
                                        ApplicationClass.user = response;
                                        Toast.makeText(Login.this, "Successfully Logged In as CareTaker", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, CareTakerActivity.class));
                                        Login.this.finish();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault)
                                    {
                                        Toast.makeText(Login.this, "Error -> " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                        showProgress(false);
                                    }
                                }, true);
                            }
                        }
                    });
                }
/*
                //if Student Radio button is clicked..... The login button will jump to the Student activity
                if(checkedId == R.id.rbStudent)
                {
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(etUsername.getText().toString().isEmpty()|| etPassword.getText().toString().isEmpty())
                            {
                                Toast.makeText(Login.this, "Specify Login Credentials and Occupation", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                tvLoad.setText("Signing In as Student....Please wait");
                                showProgress(true);

                                Backendless.UserService.login(userName, password, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response)
                                    {
                                        Toast.makeText(Login.this, "Successfully Logged In as Student", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, StudentActivity.class));
                                        Login.this.finish();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault)
                                    {
                                        Toast.makeText(Login.this, "Error -> " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                        showProgress(false);
                                    }
                                }, true);
                            }
                        }
                    });
                }

                //if HC Radio button is clicked..... The login button will jump to the House Committee activity
                if(checkedId == R.id.rbHouseCommittee)
                {
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(etUsername.getText().toString().isEmpty()|| etPassword.getText().toString().isEmpty())
                            {
                                Toast.makeText(Login.this, "Specify Login Credentials and Occupation", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                tvLoad.setText("Signing In as House Committee....Please wait");
                                showProgress(true);

                                Backendless.UserService.login(userName, password, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response) {
                                        Toast.makeText(Login.this, "Successfully Logged In as House Committee", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, HCActivity.class));
                                        Login.this.finish();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(Login.this, "Error -> " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                        showProgress(false);
                                    }
                                }, true);
                            }
                        }
                    });
                }

                //if Mentor Radio button is clicked..... The login button will jump to the Mentor activity
                if(checkedId == R.id.rbMentor)
                {
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(etUsername.getText().toString().isEmpty()|| etPassword.getText().toString().isEmpty())
                            {
                                Toast.makeText(Login.this, "Specify Login Credentials and Occupation", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                tvLoad.setText("Signing in as Mentor....Please wait");
                                showProgress(true);

                                Backendless.UserService.login(userName, password, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response) {
                                        Toast.makeText(Login.this, "Successfully Logged In as Mentor", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, MentorActivity.class));
                                        Login.this.finish();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(Login.this, "Error -> " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                        showProgress(false);
                                    }
                                }, true);
                            }
                        }
                    });
                }

                //if CareTaker Radio button is clicked..... The login button will jump to the Res Manager activity
                if(checkedId == R.id.rbResManager)
                {
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(etUsername.getText().toString().isEmpty()|| etPassword.getText().toString().isEmpty())
                            {
                                Toast.makeText(Login.this, "Specify Login Credentials and Occupation", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                tvLoad.setText("Signing in as Res Manager....Please wait");
                                showProgress(true);

                                Backendless.UserService.login(userName, password, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response) {
                                        Toast.makeText(Login.this, "Successfully Logged In as Res Manager", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, ResManagerActivity.class));
                                        Login.this.finish();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(Login.this, "Error -> " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                        showProgress(false);
                                    }
                                }, true);
                            }
                        }
                    });
                }*/
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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

}