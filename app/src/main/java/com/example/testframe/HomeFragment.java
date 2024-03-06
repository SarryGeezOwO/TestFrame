package com.example.testframe;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class HomeFragment extends Fragment {


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        TextView dayOfTheWeek = v.findViewById(R.id.calendar_day);
        TextView date = v.findViewById(R.id.calendar_day_number);

        Calendar now = Calendar.getInstance();
        String myDate = now.getTime().toString();
        String dateFormat = DateFormat.getDateInstance().format(now.getTime());
        dateFormat = dateFormat.replaceAll(" ", " / ");

        dayOfTheWeek.setText(findDay(myDate));
        date.setText(dateFormat);

        Realm.init(v.getContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Reminder> results = realm.where(Reminder.class).findAll();

        RecyclerView recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        RecycleAdapter adapter = new RecycleAdapter(v.getContext(), results);
        recyclerView.setAdapter(adapter);

        results.addChangeListener(results1 -> adapter.notifyDataSetChanged());
        return v;
    }

    public static String findDay(String myDate) {
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