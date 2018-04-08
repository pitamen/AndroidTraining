package com.taregan.sqlitedemo.database.model;

/**
 * Created by pitambar on 4/5/18.
 */

public class Note {
    public   static String TABLE_NAME = "notes";

    public static String COLUMN_ID ="id";
    public static String COLUMN_NOTE ="note";
    public static String COLUMN_TIMESTAMP ="timestamp";


    private int id;
    private String note;
    private String timeStamp;

    //Sql query to create database table
    public static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_NOTE+" TEXT,"+COLUMN_TIMESTAMP+" DATETIME DEFAULT CURRENT_TIMESTAMP"+")";


    public Note() {
    }

    public Note(int id, String note, String timeStamp) {
        this.id = id;
        this.note = note;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
