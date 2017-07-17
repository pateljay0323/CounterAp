package com.jay.counterapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by baldor on 4/7/17.
 */

public class Jap extends RealmObject {
    @PrimaryKey
    private long id;

    private int japCount;

    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getJapCount() {
        return japCount;
    }

    public void setJapCount(int japCount) {
        this.japCount = japCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
