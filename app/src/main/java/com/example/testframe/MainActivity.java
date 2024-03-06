package com.example.testframe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    int selectedDot;
    int emptyDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RelativeLayout layout = findViewById(R.id.mainLayout);
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(800);
        layout.startAnimation(animation);


        selectedDot = R.drawable.selected_dot;
        emptyDot = R.drawable.empty_dot;
        TextView[] dots = {
                findViewById(R.id.dot1),
                findViewById(R.id.dot2),
                findViewById(R.id.dot3)
        };


        pager = findViewById(R.id.pager);
        PagerAdapter adapter = new PanelAdapter(this, this);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for(int i = 0; i < dots.length; i++) {
                    if(i != position) {
                        dots[i].setBackgroundResource(emptyDot);
                    }else {
                        dots[position].setBackgroundResource(selectedDot);
                    }
                }
            }
        });

    }

    public void startContent() {
        Intent i = new Intent(this, MainContent.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(i, b);
    }
}