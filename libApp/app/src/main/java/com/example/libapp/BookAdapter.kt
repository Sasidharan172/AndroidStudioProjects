package com.example.libapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookAdapter(
    private var books: List<Book>,
    private val onItemClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverImageView: ImageView = view.findViewById(R.id.coverImageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val authorTextView: TextView = view.findViewById(R.id.authorTextView)
        val availabilityTextView: TextView = view.findViewById(R.id.availabilityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]

        holder.titleTextView.text = book.title
        holder.authorTextView.text = book.author

        // Load book cover image
        Glide.with(holder.itemView.context)
            .load(book.coverUrl)
            .placeholder(R.drawable.book_placeholder) // You need to create this drawable
            .error(R.drawable.book_error) // You need to create this drawable
            .into(holder.coverImageView)

        // Set availability text and color
        if (book.isAvailable) {
            holder.availabilityTextView.text = "Available"
            holder.availabilityTextView.setTextColor(holder.itemView.context.getColor(android.R.color.holo_green_dark))
        } else {
            holder.availabilityTextView.text = "Borrowed"
            holder.availabilityTextView.setTextColor(holder.itemView.context.getColor(android.R.color.holo_red_dark))
        }

        // Set click listener
        holder.itemView.setOnClickListener { onItemClick(book) }
    }

    override fun getItemCount() = books.size

    fun updateBooks(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}