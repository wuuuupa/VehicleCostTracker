package com.example.vehiclecosttracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "expenses"
        const val COLUMN_ID = "id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_NOTE = "note"
        const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AMOUNT REAL NOT NULL,
                $COLUMN_NOTE TEXT,
                $COLUMN_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExpense(amount: Double, note: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AMOUNT, amount)
            put(COLUMN_NOTE, note)
        }
        val result = db.insert(TABLE_NAME, null, values)
        return result != -1L
    }
}
