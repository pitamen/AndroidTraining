package com.taregan.sqlitedemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.taregan.sqlitedemo.database.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pitambar on 4/5/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //define database version
    public static final int DATABASE_VERSION =1;

    //database name
    public static final String DATABASE_NAME ="notes.db";



    //create database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Note.CREATE_TABLE);

    }

    //upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //drop table if exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Note.TABLE_NAME);
        //create new table
        onCreate(sqLiteDatabase);

    }


    //CRUD OPERATIONS

    /**
     * insert note to database
     * @param note
     * @return
     */
    public long insertNotes(String note){


        //create writable instance of database
        SQLiteDatabase db = this.getWritableDatabase();

        //initialize content values
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note);



        //insert data to the table
        long id = db.insert(Note.TABLE_NAME,null,values);

        //close database
        db.close();

        //return newly inserted id
        return id;

    }

    /**
     * read data from database
     * @param id
     * @return
     */

    public Note getNote(long id){

        //get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_NOTE, Note.COLUMN_TIMESTAMP},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        //prepare note object
        Note note = new Note(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

        cursor.close();
        db.close();

        return note;

    }

    /**
     *
     * @return
     */
    public List<Note> getAllNotes(){

        List<Note> notes  = new ArrayList<>();

        //select all query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        //looping
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                note.setTimeStamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                notes.add(note);

            }while (cursor.moveToNext());
        }

        cursor.close();

        db.close();
        return notes;

    }


    public int getNotesCount(){

        String countQuery = "SELECT * FROM "+Note.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;



    }

    /**
     * update database
     * @param note
     * @return
     */

    public int updateNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }


    public void deleteNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();

    }


}
