package com.example.libapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class BookDetailActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        databaseHelper = DatabaseHelper(this)
        bookId = intent.getIntExtra("BOOK_ID", -1)

        if (bookId == -1) {
            Toast.makeText(this, "Error: Book not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val book = databaseHelper.getBook(bookId)
        if (book == null) {
            Toast.makeText(this, "Error: Book not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<TextView>(R.id.titleTextView).text = book.title
        findViewById<TextView>(R.id.authorTextView).text = book.author
        Glide.with(this)
            .load(book.coverUrl)
            .into(findViewById<ImageView>(R.id.coverImageView))

        val actionButton = findViewById<Button>(R.id.actionButton)
        updateActionButton(book.isAvailable)

        actionButton.setOnClickListener {
            val newAvailability = !book.isAvailable
            databaseHelper.updateBookAvailability(bookId, newAvailability)
            updateActionButton(newAvailability)
            Toast.makeText(this, if (newAvailability) "Book returned" else "Book borrowed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateActionButton(isAvailable: Boolean) {
        val actionButton = findViewById<Button>(R.id.actionButton)
        if (isAvailable) {
            actionButton.text = "Borrow"
            actionButton.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
        } else {
            actionButton.text = "Return"
            actionButton.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
        }
    }
}