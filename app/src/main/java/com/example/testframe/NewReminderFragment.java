package com.example.testframe;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class NewReminderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
            Since fragments are a pain in the ass, you can't simply call the findViewById();
            You have to get the View(Parent) that contains this fragment and then you can call findViewById();
        */

        // Assigning the newly created Fragment(View) to a variable
        View v = inflater.inflate(R.layout.fragment_new_reminder, container, false);

        // Some variables
        EditText input = v.findViewById(R.id.content_input);
        Button confirm = v.findViewById(R.id.confirm_button);

        // Initializing the Realm(database) to access some features
        Realm.init(v.getContext());

        // Referencing the database to a variable
        Realm realm = Realm.getDefaultInstance();

        confirm.setOnClickListener(v1 -> {
            // Setting values for the Reminder class
            String content = input.getText().toString();
            long createdTime = System.currentTimeMillis();

            realm.beginTransaction(); // Needed when changing something from the database(Realm)

            Reminder reminder = realm.createObject(Reminder.class); // Reminder class is a 'RealmObject'
            reminder.setContent(content);
            reminder.setDateCreated(createdTime);

            realm.commitTransaction(); // Feeding and saving the data to the database(Realm) (Required)

            Toast.makeText(v.getContext(), "Successfully added a new reminder!", Toast.LENGTH_SHORT).show();
            input.setText("");

            // Returning to the Home Fragment
            AppCompatActivity activity = (AppCompatActivity) container.getContext();
            MainContent.callFragment(activity, R.id.fragment_container, new HomeFragment());
        });

        return v;
    }
}