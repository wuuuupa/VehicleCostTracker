package com.example.vehiclecosttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
    private lateinit var settingsButton: Button
    private lateinit var tvVehicleName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = ExpenseDatabaseHelper(this)

        // 初始化视图
        tvVehicleName = findViewById(R.id.tvVehicleName)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addButton = findViewById(R.id.button_add_expense)
        exportButton = findViewById(R.id.button_export_csv)
        settingsButton = findViewById(R.id.button_vehicle_settings)

        // 新增支出按钮点击事件
        addButton.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // 导出CSV按钮点击事件
        exportButton.setOnClickListener {
            exportExpensesToCSV()
        }

        // 设置按钮点击事件
        settingsButton.setOnClickListener {
            startActivity(Intent(this, VehicleSettingsActivity::class.java))
        }

        loadVehicleName()
        loadExpenses()
    }

    override fun onResume() {
        super.onResume()
        loadVehicleName()
        loadExpenses()
    }

    private fun loadVehicleName() {
        val prefs = getSharedPreferences("vehicle_prefs", MODE_PRIVATE)
        val vehicleName = prefs.getString("vehicle_name", "默认车辆")
        tvVehicleName.text = "车辆：$vehicleName"
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
