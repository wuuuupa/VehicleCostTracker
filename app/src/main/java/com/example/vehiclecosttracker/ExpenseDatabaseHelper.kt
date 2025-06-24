package com.example.vehiclecosttracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_EXPENSES = "expenses"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_EXPENSES (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                amount REAL,
                note TEXT,
                timestamp TEXT,
                vehicleName TEXT
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }

    fun insertExpense(amount: Double, note: String, vehicleName: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("amount", amount)
            put("note", note)
            put("timestamp", System.currentTimeMillis().toString())
            put("vehicleName", vehicleName)
        }
        db.insert(TABLE_EXPENSES, null, values)
        db.close()
    }

    fun getAllExpenses(): List<Expense> {
        val expenseList = mutableListOf<Expense>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EXPENSES ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
                val note = cursor.getString(cursor.getColumnIndexOrThrow("note"))
                val timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))
                val vehicleName = cursor.getString(cursor.getColumnIndexOrThrow("vehicleName"))

                val expense = Expense(id, amount, note, timestamp, vehicleName)
                expenseList.add(expense)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenseList
    }
}
