package com.csg.airvisualapiexam;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.csg.airvisualapiexam.ui.AirVisualFragment;
import com.csg.airvisualapiexam.ui.MapFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        AirVisualFragment airVisualFragment = new AirVisualFragment();
        MapFragment mapFragment = new MapFragment();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, airVisualFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mapFragment)
                    .commit();
        }



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
//                    case R.id.
                }
                return true;
            }
        });


    }
}
