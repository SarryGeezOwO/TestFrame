package com.example.testframe;

import io.realm.RealmObject;

public class Reminder extends RealmObject {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    String content;
    long dateCreated;
}
