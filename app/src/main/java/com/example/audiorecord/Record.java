package com.example.audiorecord;

public class Record {
    private int id;
    private String date;
    private int currentTime;

    public Record() {
    }

    public Record(String date, int currentTime) {
        this.date = date;
        this.currentTime = currentTime;
    }

    public Record(int id, String date, int currentTime) {
        this.id = id;
        this.date = date;
        this.currentTime = currentTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
