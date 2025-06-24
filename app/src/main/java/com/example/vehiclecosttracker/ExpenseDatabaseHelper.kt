package com.example.vehiclecosttracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

data class Expense(val id: Int, val amount: Double, val note: String, val timestamp: String, val vehicle: String)

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_EXPENSES = "expenses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_NOTE = "note"
        private const val COLUMN_TIMESTAMP = "timestamp"
        private const val COLUMN_VEHICLE = "vehicle"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_EXPENSES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AMOUNT REAL,
                $COLUMN_NOTE TEXT,
                $COLUMN_TIMESTAMP TEXT,
                $COLUMN_VEHICLE TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }

    fun insertExpense(amount: Double, note: String, vehicle: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AMOUNT, amount)
            put(COLUMN_NOTE, note)
            put(COLUMN_TIMESTAMP, getCurrentTime())
            put(COLUMN_VEHICLE, vehicle)
        }
        db.insert(TABLE_EXPENSES, null, values)
        db.close()
    }

    fun getAllExpenses(): List<Expense> {
        val expenseList = mutableListOf<Expense>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EXPENSES ORDER BY $COLUMN_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))
                val timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                val vehicle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE))
                expenseList.add(Expense(id, amount, note, timestamp, vehicle))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenseList
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
