package com.example.testframe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    Context context;
    RealmResults<Reminder> results;

    // Constructor
    public RecycleAdapter(Context context, RealmResults<Reminder> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creating a new MyViewHolder class
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.reminder_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Getting the specific Reminder at the given index(Position)
        Reminder reminder = results.get(position);

        // Setting the content Text value of the Reminder XML
        holder.contentOutput.setText(reminder.getContent());

        // String with a format, and assigning the string to the DateCreated Text of the Reminder XML
        String formatedTime = DateFormat.getTimeInstance().format(reminder.dateCreated);
        holder.dateCreatedOutput.setText(formatedTime);

        holder.itemView.setOnLongClickListener(e1 -> {
            // Creating a new Popup
            PopupMenu popupMenu = new PopupMenu(context, e1);

            // Adding a option 'Delete' to the Popup
            popupMenu.getMenu().add("Delete");

            popupMenu.setOnMenuItemClickListener(e2 -> {
                // check if the clicked menu is the 'Delete' menu
                if(e2.getTitle().equals("Delete")) {

                    Realm realm = Realm.getDefaultInstance(); // Get the Database(Realm)
                    realm.beginTransaction(); // Needed when changing something from the Database(Realm)
                    reminder.deleteFromRealm(); // Deleting the given Data from the Database(Realm)
                    realm.commitTransaction(); // Feeding and saving the data to the Database(Realm) (Required)
                    Toast.makeText(context, "Reminder Deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            popupMenu.show(); // Display the Popup
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        /*
            This class is basically the 'Reminder' XML and were referencing the variables that will be changed
            through code.
        */

        TextView contentOutput;
        TextView dateCreatedOutput;

        public MyViewHolder(@NonNull View view) {
            super(view);
            contentOutput = view.findViewById(R.id.contentOutput);
            dateCreatedOutput = view.findViewById(R.id.dateCreatedOutput);
        }

    }
}
