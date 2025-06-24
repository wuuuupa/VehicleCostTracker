package com.example.vehiclecosttracker

import android.content.Intent
import android.os.Bundle
import android.os.Environment
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

        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 初始化按钮
        addButton = findViewById(R.id.button_add_expense)
        exportButton = findViewById(R.id.button_export_csv)

        // 设置新增支出按钮点击事件
        addButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // 设置导出 CSV 按钮点击事件
        exportButton.setOnClickListener {
            exportExpensesToCSV()
        }

        // 加载支出数据
        loadExpenses()
    }

    override fun onResume() {
        super.onResume()
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
            val writer = FileWriter(file)
            writer.append("金额,备注,时间\n")
            for (expense in expenses) {
                writer.append("${expense.amount},${expense.note},${expense.timestamp}\n")
            }
            writer.flush()
            writer.close()
            Toast.makeText(this, "CSV导出成功：${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "导出失败：${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
