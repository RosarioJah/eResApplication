package com.example.eresapplication.Classes;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class ApplicationClass extends Application
{
    //CHANGE THE APPLICATION ID AND API KEY

    public static final String APPLICATION_ID = "19004BA1-BBF7-6ED4-FF8E-A2FBC6E3BE00";
    public static final String API_KEY = "BA85C400-1434-47ED-AA81-15794D31395B";
    public static final String SERVER_URL = "https://api.backendless.com";
    public static BackendlessUser user;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}