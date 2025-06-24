package com.example.vehiclecosttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileWriter
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var databaseHelper: ExpenseDatabaseHelper
    private lateinit var addButton: Button
    private lateinit var exportButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化数据库
        databaseHelper = ExpenseDatabaseHelper(this)

        // 设置 RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 绑定按钮
        addButton = findViewById(R.id.button_add_expense)
        exportButton = findViewById(R.id.button_export_csv)

        // 点击“新增支出”
        addButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // 点击“导出 CSV”
        exportButton.setOnClickListener {
            exportExpensesToCSV()
        }

        // 初次加载支出列表
        loadExpenses()
    }

    override fun onResume() {
        super.onResume()
        // 页面重新显示时刷新支出数据
        loadExpenses()
    }

    private fun loadExpenses() {
        val updatedList = databaseHelper.getAllExpenses()
        adapter = ExpenseAdapter(updatedList)
        recyclerView.adapter = adapter
    }

    private fun exportExpensesToCSV() {
        val expenses = databaseHelper.getAllExpenses()
        if (expenses.isEmpty()) {
            Toast.makeText(this, "暂无支出记录可导出", Toast.LENGTH_SHORT).show()
            return
        }

        val fileName = "expenses.csv"
        val file = File(getExternalFilesDir(null), fileName)

        try {
            FileWriter(file).use { writer ->
                writer.append("金额,备注,时间\n")
                for (expense in expenses) {
                    writer.append("${expense.amount},${expense.note},${expense.timestamp}\n")
                }
            }
            Toast.makeText(this, "CSV 导出成功：\n${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Toast.makeText(this, "导出失败：${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
