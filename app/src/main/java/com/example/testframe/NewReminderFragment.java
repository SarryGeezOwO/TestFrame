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

        View v = inflater.inflate(R.layout.fragment_new_reminder, container, false);

        EditText input = v.findViewById(R.id.content_input);
        Button confirm = v.findViewById(R.id.confirm_button);

        Realm.init(v.getContext());
        Realm realm = Realm.getDefaultInstance();
        confirm.setOnClickListener(v1 -> {
            String content = input.getText().toString();
            long createdTime = System.currentTimeMillis();

            realm.beginTransaction();
            Reminder reminder = realm.createObject(Reminder.class);
            reminder.setContent(content);
            reminder.setDateCreated(createdTime);
            realm.commitTransaction();

            Toast.makeText(v.getContext(), "Successfully added a new reminder!", Toast.LENGTH_SHORT).show();
            input.setText("");

            AppCompatActivity activity = (AppCompatActivity) container.getContext();
            MainContent.callFragment(activity, R.id.fragment_container, new HomeFragment());
        });

        return v;
    }
}