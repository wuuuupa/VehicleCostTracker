package com.example.vehiclecosttracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        dbHelper = ExpenseDatabaseHelper(this)  // ✅ 初始化数据库助手

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etNote = findViewById<EditText>(R.id.etNote)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "请输入有效金额", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ 保存到数据库
            val db = dbHelper.writableDatabase
            val sql = "INSERT INTO expenses (amount, note) VALUES (?, ?)"
            val statement = db.compileStatement(sql)
            statement.bindDouble(1, amount)
            statement.bindString(2, noteText)
            statement.executeInsert()

            Toast.makeText(this, "保存成功：¥$amount\n备注：$noteText", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}
