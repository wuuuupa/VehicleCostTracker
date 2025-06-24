package com.example.vehiclecosttracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "expenses.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE expenses (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "amount REAL, " +
                    "note TEXT, " +
                    "timestamp TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS expenses")
        onCreate(db)
    }

    fun insertExpense(amount: Double, note: String, timestamp: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("amount", amount)
            put("note", note)
            put("timestamp", timestamp)
        }
        return db.insert("expenses", null, values)
    }

    fun getAllExpenses(): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM expenses ORDER BY id DESC", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
            val note = cursor.getString(cursor.getColumnIndexOrThrow("note"))
            val timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))

            expenses.add(Expense(id, amount, note, timestamp))
        }

        cursor.close()
        return expenses
    }
}
