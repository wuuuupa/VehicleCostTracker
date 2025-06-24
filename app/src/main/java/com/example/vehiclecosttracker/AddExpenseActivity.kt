package com.example.vehiclecosttracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etNote = findViewById<EditText>(R.id.etNote)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val amountText = etAmount.text.toString()
            val noteText = etNote.text.toString()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 暂时我们只是弹出一个 Toast（后续再做保存）
            Toast.makeText(this, "保存成功：¥$amountText\n备注：$noteText", Toast.LENGTH_LONG).show()

            // 你可以选择 finish() 回到上个界面
            finish()
        }
    }
}
