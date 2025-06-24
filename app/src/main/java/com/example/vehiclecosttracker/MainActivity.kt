// 路径：app/src/main/java/com/example/vehiclecosttracker/MainActivity.kt

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 显示车辆名称
        val tvVehicleName = findViewById<TextView>(R.id.tvVehicleName)
        val prefs = getSharedPreferences("vehicle_prefs", MODE_PRIVATE)
        val vehicleName = prefs.getString("vehicle_name", "未命名")
        tvVehicleName.text = "车辆：$vehicleName"

        // 初始化数据库
        databaseHelper = ExpenseDatabaseHelper(this)

        // 设置 RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 初始化按钮
        addButton = findViewById(R.id.button_add_expense)
        exportButton = findViewById(R.id.button_export_csv)
        settingsButton = findViewById(R.id.button_vehicle_settings)

        // 点击新增支出按钮
        addButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // 点击导出 CSV 按钮
        exportButton.setOnClickListener {
            exportExpensesToCSV()
        }

        // 点击车辆设置按钮
        settingsButton.setOnClickListener {
            val intent = Intent(this, VehicleSettingsActivity::class.java)
            startActivity(intent)
        }

        // 初次加载支出数据
        loadExpenses()
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
        // 更新车辆名称（避免从设置页面返回后仍是旧值）
        val tvVehicleName = findViewById<TextView>(R.id.tvVehicleName)
        val prefs = getSharedPreferences("vehicle_prefs", MODE_PRIVATE)
        val vehicleName = prefs.getString("vehicle_name", "未命名")
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
