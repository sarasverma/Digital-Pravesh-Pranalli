package com.saras.trying.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saras.trying.frags.Home;
import com.saras.trying.frags.Profile;
import com.saras.trying.R;
import com.saras.trying.frags.Tickets;
import com.saras.trying.frags.Verify;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNav);

        // for initial app load
        loadFragment(new Home());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
                    loadFragment(new Home());
                    break;
                case R.id.navTicket:
                    loadFragment(new Tickets());
                    break;
                case R.id.navVerify:
                    loadFragment(new Verify());
                    break;
                case R.id.navProfile:
                    loadFragment(new Profile());
                    break;
            }
            return true;
        });
    }

    private void loadFragment(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, frag);
        ft.commit();
    }
}