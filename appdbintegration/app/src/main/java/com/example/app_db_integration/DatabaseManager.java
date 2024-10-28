package com.example.app_db_integration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Open the database connection
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database connection
    public void close() {
        dbHelper.close();
    }

    // Insert user name and location
    public void insertUser(String name, String location) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("location", location);
        database.insert("users", null, values);
    }

    // Retrieve all users and locations
    public Cursor getAllUsers() {
        String[] columns = {"id", "name", "location"};
        return database.query("users", columns, null, null, null, null, null);
    }

    // Update user location based on their name
    public void updateUserLocation(String name, String newLocation) {
        ContentValues values = new ContentValues();
        values.put("location", newLocation);
        database.update("users", values, "name=?", new String[]{name});
    }
}
