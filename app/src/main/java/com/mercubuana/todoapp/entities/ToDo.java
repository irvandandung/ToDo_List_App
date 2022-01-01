package com.mercubuana.todoapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ToDo implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "dateInput")
    private String dateInput;

    @ColumnInfo(name = "dateAlarm")
    private String dateAlarm;

    @ColumnInfo(name = "timeAlarm")
    private String timeAlarm;

    @Ignore
    public ToDo(){

    }

    protected ToDo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        dateInput = in.readString();
        dateAlarm = in.readString();
        timeAlarm = in.readString();
    }

    public static final Creator<ToDo> CREATOR = new Creator<ToDo>() {
        @Override
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        @Override
        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

    public ToDo(int id, String title, String description, String dateInput, String dateAlarm, String timeAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateInput = dateInput;
        this.dateAlarm = dateAlarm;
        this.timeAlarm = timeAlarm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(dateInput);
        dest.writeString(dateAlarm);
        dest.writeString(timeAlarm);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateInput() {
        return dateInput;
    }

    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
    }

    public String getDateAlarm() {
        return dateAlarm;
    }

    public void setDateAlarm(String dateAlarm) {
        this.dateAlarm = dateAlarm;
    }

    public String getTimeAlarm() {
        return timeAlarm;
    }

    public void setTimeAlarm(String timeAlarm) {
        this.timeAlarm = timeAlarm;
    }
}
