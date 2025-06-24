package com.example.vehiclecosttracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        val listView = findViewById<ListView>(R.id.listExpenses)

        // 从数据库读取支出数据（这里我们用假数据代替，下一步会接入 SQLite）
        val expenses = listOf(
            "2024-06-01: ¥100 - 加油",
            "2024-06-05: ¥50 - 停车",
            "2024-06-08: ¥80 - 洗车"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, expenses)
        listView.adapter = adapter
    }
}
