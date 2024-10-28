package com.example.libraryapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public class BookGalleryActivity extends AppCompatActivity {
    private DatabaseHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_gallery);

        dbHelper = new DatabaseHandler(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch and display the books
        List<Book> books = dbHelper.getAllBooks();
        if (books.isEmpty()) {
            populateBooks();
            books = dbHelper.getAllBooks();
        }
        BookAdapter bookAdapter = new BookAdapter(this, books);
        recyclerView.setAdapter(bookAdapter);
    }

    private void populateBooks() {
        List<Book> books = dbHelper.getAllBooks();
        if (books.isEmpty()) {
            dbHelper.addBook("The Great Gatsby", "F. Scott Fitzgerald", 1);
            dbHelper.addBook("1984", "George Orwell", 1);
            dbHelper.addBook("To Kill a Mockingbird", "Harper Lee", 1);
            dbHelper.addBook("Moby-Dick", "Herman Melville", 1);
            Log.d("DB", "Books inserted successfully");
        } else {
            Log.d("DB", "Books already present in DB");
        }
    }

}

