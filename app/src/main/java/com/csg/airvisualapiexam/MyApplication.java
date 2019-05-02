package com.csg.airvisualapiexam;

import android.app.Application;

import com.google.android.libraries.places.api.Places;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Places.
//        Places.initialize(getApplicationContext(), "AIzaSyD148oNEhvyv9dsS9e05gu0a1BXajgDbw4");
        Places.initialize(getApplicationContext(), "AIzaSyDQNVccMMZwWIUzcmWA9cX9M_O9Q00l3Qg");

// Create a new Places client instance.
    }
}
