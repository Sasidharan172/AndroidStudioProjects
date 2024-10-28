package com.example.libapp

import android.content.Context

object DatabaseInitializer {
    fun populateDatabase(context: Context) {
        val databaseHelper = DatabaseHelper(context)

        val books = listOf(
            Book(1, "To Kill a Mockingbird", "Harper Lee", "https://example.com/mockingbird.jpg", true),
            Book(2, "1984", "George Orwell", "https://example.com/1984.jpg", true),
            Book(3, "Pride and Prejudice", "Jane Austen", "https://example.com/pride.jpg", true),
            Book(4, "The Great Gatsby", "F. Scott Fitzgerald", "https://example.com/gatsby.jpg", true),
            Book(5, "Moby Dick", "Herman Melville", "https://example.com/mobydick.jpg", true)
        )

        books.forEach { book ->
            databaseHelper.addBook(book)
        }
    }
}