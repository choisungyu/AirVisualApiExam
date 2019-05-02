package com.csg.airvisualapiexam;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.csg.airvisualapiexam.ui.AirVisualFragment;
import com.csg.airvisualapiexam.ui.MapFragment;

public class MainActivity extends AppCompatActivity {

    AirVisualFragment airVisualFragment = new AirVisualFragment();
    MapFragment mapFragment = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.airvisual);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, airVisualFragment)
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.map:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, mapFragment)
                                .commit();
                        break;
                    case R.id.airvisual:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, airVisualFragment)
                                .commit();
                        break;
                }
                return true;
            }
        });


    }
}
