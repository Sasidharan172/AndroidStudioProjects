package com.example.libapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "LibraryDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_BOOKS = "books"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_AUTHOR = "author"
        private const val KEY_COVER_URL = "cover_url"
        private const val KEY_IS_AVAILABLE = "is_available"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_BOOKS (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_TITLE TEXT,
                $KEY_AUTHOR TEXT,
                $KEY_COVER_URL TEXT,
                $KEY_IS_AVAILABLE INTEGER
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        onCreate(db)
    }

    fun addBook(book: Book): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_TITLE, book.title)
            put(KEY_AUTHOR, book.author)
            put(KEY_COVER_URL, book.coverUrl)
            put(KEY_IS_AVAILABLE, if (book.isAvailable) 1 else 0)
        }
        return db.insert(TABLE_BOOKS, null, values)
    }

    fun getAllBooks(): List<Book> {
        val books = mutableListOf<Book>()
        val selectQuery = "SELECT * FROM $TABLE_BOOKS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val book = Book(
                        id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                        title = it.getString(it.getColumnIndexOrThrow(KEY_TITLE)),
                        author = it.getString(it.getColumnIndexOrThrow(KEY_AUTHOR)),
                        coverUrl = it.getString(it.getColumnIndexOrThrow(KEY_COVER_URL)),
                        isAvailable = it.getInt(it.getColumnIndexOrThrow(KEY_IS_AVAILABLE)) == 1
                    )
                    books.add(book)
                } while (it.moveToNext())
            }
        }
        return books
    }

    fun updateBookAvailability(bookId: Int, isAvailable: Boolean): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_IS_AVAILABLE, if (isAvailable) 1 else 0)
        }
        return db.update(TABLE_BOOKS, values, "$KEY_ID = ?", arrayOf(bookId.toString()))
    }

    fun getBook(bookId: Int): Book? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_BOOKS,
            arrayOf(KEY_ID, KEY_TITLE, KEY_AUTHOR, KEY_COVER_URL, KEY_IS_AVAILABLE),
            "$KEY_ID = ?",
            arrayOf(bookId.toString()),
            null,
            null,
            null,
            "1"
        )
        return cursor.use {
            if (it.moveToFirst()) {
                Book(
                    id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                    title = it.getString(it.getColumnIndexOrThrow(KEY_TITLE)),
                    author = it.getString(it.getColumnIndexOrThrow(KEY_AUTHOR)),
                    coverUrl = it.getString(it.getColumnIndexOrThrow(KEY_COVER_URL)),
                    isAvailable = it.getInt(it.getColumnIndexOrThrow(KEY_IS_AVAILABLE)) == 1
                )
            } else {
                null
            }
        }
    }
}