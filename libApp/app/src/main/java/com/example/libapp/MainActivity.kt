package com.example.libapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class MainActivity : AppCompatActivity() {
  private lateinit var recyclerView: RecyclerView
  private lateinit var bookAdapter: BookAdapter
  private lateinit var databaseHelper: DatabaseHelper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    databaseHelper = DatabaseHelper(this)

    recyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = GridLayoutManager(this, 2)

    bookAdapter = BookAdapter(emptyList()) { book ->
      val intent = Intent(this, BookDetailActivity::class.java)
      intent.putExtra("BOOK_ID", book.id)
      startActivity(intent)
    }
    recyclerView.adapter = bookAdapter

    loadBooks()
  }

  private fun loadBooks() {
    val books = databaseHelper.getAllBooks()
    bookAdapter.updateBooks(books)
  }

  override fun onResume() {
    super.onResume()
    loadBooks() // Refresh the book list when returning to this activity
  }
}
