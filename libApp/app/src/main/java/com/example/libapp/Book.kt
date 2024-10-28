package com.example.libapp

import java.util.Date

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val isbn: String,
    val coverUrl: String,
    val publishDate: Date,
    val description: String,
    var isAvailable: Boolean,
    var borrowedBy: String? = null,
    var borrowDate: Date? = null,
    var returnDate: Date? = null
) {
    fun borrow(borrower: String) {
        if (isAvailable) {
            isAvailable = false
            borrowedBy = borrower
            borrowDate = Date()
            returnDate = null
        } else {
            throw IllegalStateException("Book is not available for borrowing")
        }
    }

    fun returnBook() {
        if (!isAvailable) {
            isAvailable = true
            borrowedBy = null
            returnDate = Date()
        } else {
            throw IllegalStateException("Book is already available")
        }
    }

    private fun getDaysUntilDue(loanPeriodDays: Int): Int? {
        if (isAvailable || borrowDate == null) {
            return null
        }
        val dueDate = Date(borrowDate!!.time + loanPeriodDays * 24 * 60 * 60 * 1000L)
        val today = Date()
        return ((dueDate.time - today.time) / (24 * 60 * 60 * 1000)).toInt()
    }

    fun isOverdue(loanPeriodDays: Int): Boolean {
        return getDaysUntilDue(loanPeriodDays)?.let { it < 0 } ?: false
    }

    companion object {
        fun createNew(
            title: String,
            author: String,
            isbn: String,
            coverUrl: String,
            publishDate: Date,
            description: String
        ): Book {
            return Book(
                id = 0, // This will be set by the database
                title = title,
                author = author,
                isbn = isbn,
                coverUrl = coverUrl,
                publishDate = publishDate,
                description = description,
                isAvailable = true
            )
        }
    }
}