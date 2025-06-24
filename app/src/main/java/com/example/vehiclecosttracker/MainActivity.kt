package com.example.vehiclecosttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ExpenseDatabaseHelper(this)

        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 获取支出数据并设置 Adapter
        val expenseList = dbHelper.getAllExpenses()
        adapter = ExpenseAdapter(expenseList)
        recyclerView.adapter = adapter

        // 按钮跳转到新增支出页面
        val addButton = findViewById<Button>(R.id.button_add_expense)
        addButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // 刷新列表数据
        val updatedList = dbHelper.getAllExpenses()
        adapter = ExpenseAdapter(updatedList)
        recyclerView.adapter = adapter
    }
}
