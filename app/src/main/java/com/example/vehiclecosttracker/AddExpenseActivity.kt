package com.example.vehiclecosttracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var etVehicle: EditText
    private lateinit var btnSave: Button
    private lateinit var databaseHelper: ExpenseDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        etAmount = findViewById(R.id.etAmount)
        etNote = findViewById(R.id.etNote)
        etVehicle = findViewById(R.id.etVehicle) // 新增输入框
        btnSave = findViewById(R.id.btnSave)

        databaseHelper = ExpenseDatabaseHelper(this)

        btnSave.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()
            val vehicleText = etVehicle.text.toString().trim()

            if (amountText.isEmpty() || vehicleText.isEmpty()) {
                Toast.makeText(this, "请输入金额和车辆名称", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "请输入有效金额", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            databaseHelper.insertExpense(amount, noteText, vehicleText)
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
