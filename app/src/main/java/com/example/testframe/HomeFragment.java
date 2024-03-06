package com.example.testframe;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;


public class HomeFragment extends Fragment {


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Assigning the newly created Fragment(View) to a variable
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // Setting up some UI as a variable
        TextView dayOfTheWeek = v.findViewById(R.id.calendar_day);
        TextView date = v.findViewById(R.id.calendar_day_number);

        // Get the System calendar
        Calendar now = Calendar.getInstance();

        // Get the Current Date in a string form
        String myDate = now.getTime().toString();

        // Formatted version the string
        String dateFormat = DateFormat.getDateInstance().format(now.getTime());
        dateFormat = dateFormat.replaceAll(" ", " / "); // Replacing all spaces in the given string with '/'

        // Setting up the Text values
        dayOfTheWeek.setText(findDay(myDate));
        date.setText(dateFormat);

        // Initializing the Realm(database) to access some features
        Realm.init(v.getContext());

        // Referencing the database to a variable
        Realm realm = Realm.getDefaultInstance();

        // Find all Reminder class inside of the Database(Realm)
        RealmResults<Reminder> results = realm.where(Reminder.class).findAll();

        // Setting up the RecyclerView element
        RecyclerView recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        // Setting up the Adapter for the RecyclerView and applying the Adapter to the recyclerView
        RecycleAdapter adapter = new RecycleAdapter(v.getContext(), results);
        recyclerView.setAdapter(adapter);

        // Notify the adapter that something has been changed in the database(Realm)
        results.addChangeListener(results1 -> adapter.notifyDataSetChanged());
        return v;
    }

    public static String findDay(String myDate) {
        // Split the string into a array with a length of 2
        // string get separated if there is a space between words
        String[] val = myDate.split(" ", 2);

        if (val[0].equals("Mon")) {
            return "Monday";
        }
        if (val[0].equals("Tue")) {
            return "Tuesday";
        }
        if (val[0].equals("Wed")) {
            return "Wednesday";
        }
        if (val[0].equals("Thu")) {
            return "Thursday";
        }
        if (val[0].equals("Fri")) {
            return "Friday";
        }
        if (val[0].equals("Sat")) {
            return "Saturday";
        }
        return "Sunday";
    }
}