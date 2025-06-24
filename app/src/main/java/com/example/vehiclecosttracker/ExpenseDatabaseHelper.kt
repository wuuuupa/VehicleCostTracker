package com.example.vehiclecosttracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "expenses.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "expenses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_NOTE = "note"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AMOUNT REAL,
                $COLUMN_NOTE TEXT,
                $COLUMN_TIMESTAMP INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExpense(expense: Expense): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_NOTE, expense.note)
            put(COLUMN_TIMESTAMP, expense.timestamp)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllExpenses(): List<Expense> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_TIMESTAMP DESC")
        val expenses = mutableListOf<Expense>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val amount = getDouble(getColumnIndexOrThrow(COLUMN_AMOUNT))
                val note = getString(getColumnIndexOrThrow(COLUMN_NOTE))
                val timestamp = getLong(getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                expenses.add(Expense(id, amount, note, timestamp))
            }
        }
        cursor.close()
        return expenses
    }
}
