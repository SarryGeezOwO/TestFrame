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


    String[] titles = { // Titles to be displayed in the slider (Ordered)
            "WELCOME!", "ABOUT", "ENJOY"
    };
    String[] descriptions = { // Description to be displayed in the slider (Ordered)
            "Hello there user!, we hope you learn something new and interesting from this application!",
            "Sining ng Pinas, is a application made by SavorSpare, this app explores the wonderful art in the country Philippines.",
            "Please have a wonderful time using this app, and don't leave bad rating please :("
    };
    int[] gradients = { // Background to be displayed in the slider (Ordered)
            R.drawable.gradientcol1,
            R.drawable.gradientcol2,
            R.drawable.gradientcol3
    };


    // Constructor
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
        // Comparing the View to the object if they're the same
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // I still don't know much about LayoutInflators but essentially they're the one responsible for creating and applying View to a container(ViewGroup)
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Setting up the Panel(Slider Page)
        View view = inflater.inflate(R.layout.panel, container, false);
        LinearLayout layout = view.findViewById(R.id.panelLayout);

        TextView title = view.findViewById(R.id.titlePanel);
        TextView desc = view.findViewById(R.id.descriptionPanel);
        Button btn = view.findViewById(R.id.buttonPanel);

        /*
            Basically all slider page has a button that has a visibility of 'GONE'(Invisible but doesn't take up space)
            now when the given position is the end(last page) the button of that slider page is now set to 'Visible'
        */
        if(position+1 == titles.length) {
            btn.setVisibility(View.VISIBLE);
        }

        // Move to the MainContent Activity
        btn.setOnClickListener(v -> activity.startContent());

        // Setting values to the Panel texts and background related to the given position
        layout.setBackgroundResource(gradients[position]);
        title.setText(titles[position]);
        desc.setText(descriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // Hiding the slider if they're not in the given position
        container.removeView((LinearLayout)object);
    }
}
