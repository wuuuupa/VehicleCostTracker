package com.example.vehiclecosttracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ExpenseListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        // 假设我们使用临时数据来展示
        val dummyExpenses = listOf(
            "¥50.0 - 加油",
            "¥30.0 - 停车费",
            "¥100.0 - 维修",
            "¥15.0 - 洗车"
        )

        val listView = findViewById<ListView>(R.id.listViewExpenses)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dummyExpenses)
        listView.adapter = adapter
    }
}
