package com.example.testframe;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainContent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        frameLayout = findViewById(R.id.fragment_container);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }else {
                    finish();
                }
            }
        };

        ComponentActivity activity = this;
        getOnBackPressedDispatcher().addCallback(activity, callback);

        dialog = new Dialog(this);
        ImageButton notificationOpen = findViewById(R.id.notification_open);
        notificationOpen.setOnClickListener(v -> {
            showDialog(dialog);
        });
    }

    public void showDialog(Dialog d) {
        d.setContentView(R.layout.notification_dialog);

        TextView close = d.findViewById(R.id.notification_exit_button);
        close.setOnClickListener(v -> {
            d.dismiss();
        });
        d.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.home)
            callFragment(this, R.id.fragment_container, new HomeFragment());
        else if(item.getItemId() == R.id.trending)
            callFragment(this, R.id.fragment_container, new TrendFragment());
        else if(item.getItemId() == R.id.add)
            callFragment(this, R.id.fragment_container, new NewReminderFragment());
        else if(item.getItemId() == R.id.check_profile)
            callFragment(this, R.id.fragment_container, new CheckProfileFragment());
        else if(item.getItemId() == R.id.about)
            callFragment(this, R.id.fragment_container, new AboutFragment());
        else if(item.getItemId() == R.id.exit) {
            finish();
            Toast.makeText(this, "Logout successfully!", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
    }

    static void callFragment(AppCompatActivity activity, int viewHolder, Fragment frag) {
        activity.getSupportFragmentManager().beginTransaction().replace(viewHolder, frag).commit();
    }
}