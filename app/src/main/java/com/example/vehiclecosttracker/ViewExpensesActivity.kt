package com.example.vehiclecosttracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        val listView = findViewById<ListView>(R.id.listViewExpenses)

        // 示例数据（我们稍后会从 SQLite 加载）
        val sampleExpenses = listOf(
            "¥50 - 加油（2025-06-24）",
            "¥30 - 停车费（2025-06-23）",
            "¥120 - 保养（2025-06-20）"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sampleExpenses)
        listView.adapter = adapter
    }
}
