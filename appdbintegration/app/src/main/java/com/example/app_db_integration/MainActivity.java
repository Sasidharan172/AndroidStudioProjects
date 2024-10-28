package com.example.app_db_integration;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseManager dbManager;
    EditText editTextName, editTextLocation;
    Button buttonInsert, buttonUpdate;
    TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseManager and open the database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Find views by ID
        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        // Set click listener for Insert button
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String location = editTextLocation.getText().toString();
                dbManager.insertUser(name, location);
                displayAllUsers();
            }
        });

        // Set click listener for Update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String newLocation = editTextLocation.getText().toString();
                dbManager.updateUserLocation(name, newLocation);
                displayAllUsers();
            }
        });

        // Display all users when the app starts
        displayAllUsers();
    }

    // Display all users in the TextView
    private void displayAllUsers() {
        Cursor cursor = dbManager.getAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String location = cursor.getString(2);
            stringBuilder.append(id).append(". ").append(name).append(" - ").append(location).append("\n");
        }
        textViewDisplay.setText(stringBuilder.toString());
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}