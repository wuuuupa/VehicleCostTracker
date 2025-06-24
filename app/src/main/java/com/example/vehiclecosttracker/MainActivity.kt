package com.example.vehiclecosttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var databaseHelper: ExpenseDatabaseHelper
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化数据库
        databaseHelper = ExpenseDatabaseHelper(this)

        // 初始化视图
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 从数据库加载数据
        val expenseList = databaseHelper.getAllExpenses()
        adapter = ExpenseAdapter(expenseList)
        recyclerView.adapter = adapter

        // 新增支出按钮
        addButton = findViewById(R.id.button_add_expense)
        addButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // 每次返回首页时刷新数据
        val updatedList = databaseHelper.getAllExpenses()
        adapter = ExpenseAdapter(updatedList)
        recyclerView.adapter = adapter
    }
}
