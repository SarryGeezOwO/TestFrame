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

    public RecycleAdapter(Context context, RealmResults<Reminder> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.reminder_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Reminder reminder = results.get(position);
        holder.contentOutput.setText(reminder.getContent());

        String formatedTime = DateFormat.getTimeInstance().format(reminder.dateCreated);
        holder.dateCreatedOutput.setText(formatedTime);
        holder.itemView.setOnLongClickListener(e1 -> {
            PopupMenu popupMenu = new PopupMenu(context, e1);
            popupMenu.getMenu().add("Delete");

            popupMenu.setOnMenuItemClickListener(e2 -> {
                if(e2.getTitle().equals("Delete")) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    reminder.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(context, "Reminder Deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            popupMenu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contentOutput;
        TextView dateCreatedOutput;

        public MyViewHolder(@NonNull View view) {
            super(view);
            contentOutput = view.findViewById(R.id.contentOutput);
            dateCreatedOutput = view.findViewById(R.id.dateCreatedOutput);
        }

    }
}
