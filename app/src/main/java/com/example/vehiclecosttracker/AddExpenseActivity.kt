package com.example.vehiclecosttracker

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var databaseHelper: ExpenseDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        databaseHelper = ExpenseDatabaseHelper(this)

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

            // 获取车辆名称
            val sharedPref = getSharedPreferences("VehiclePrefs", Context.MODE_PRIVATE)
            val vehicleName = sharedPref.getString("vehicle_name", "") ?: ""

            if (vehicleName.isEmpty()) {
                Toast.makeText(this, "请先设置车辆名称", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 保存数据
            databaseHelper.insertExpense(amount, noteText, vehicleName)

            Toast.makeText(this, "保存成功：¥$amount\n备注：$noteText", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
