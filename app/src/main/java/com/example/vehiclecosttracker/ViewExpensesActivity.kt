package com.example.vehiclecosttracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        listView = findViewById(R.id.listViewExpenses)

        // 临时模拟数据，后面会从数据库中读取
        val sampleExpenses = listOf(
            "2025-06-01 - 加油 ¥120",
            "2025-06-05 - 停车费 ¥20",
            "2025-06-08 - 洗车 ¥30"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sampleExpenses)
        listView.adapter = adapter
    }
}
