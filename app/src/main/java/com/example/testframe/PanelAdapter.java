package com.example.testframe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class PanelAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    MainActivity activity;


    String[] titles = {
            "WELCOME!", "ABOUT", "ENJOY"
    };
    String[] descriptions = {
            "Hello there user!, we hope you learn something new and interesting from this application!",
            "Sining ng Pinas, is a application made by SavorSpare, this app explores the wonderful art in the country Philippines.",
            "Please have a wonderful time using this app, and don't leave bad rating please :("
    };
    int[] gradients = {
            R.drawable.gradientcol1,
            R.drawable.gradientcol2,
            R.drawable.gradientcol3
    };


    public PanelAdapter(Context context, MainActivity activity) {
        this.context = context;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.panel, container, false);
        LinearLayout layout = view.findViewById(R.id.panelLayout);

        TextView title = view.findViewById(R.id.titlePanel);
        TextView desc = view.findViewById(R.id.descriptionPanel);
        Button btn = view.findViewById(R.id.buttonPanel);

        if(position+1 == titles.length) {
            btn.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startContent();
            }
        });

        layout.setBackgroundResource(gradients[position]);
        title.setText(titles[position]);
        desc.setText(descriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
