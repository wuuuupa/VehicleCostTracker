package com.example.vehiclecosttracker

data class Expense(
    val id: Long = 0,           // 数据库主键ID
    val amount: Double,         // 支出金额
    val note: String,           // 备注信息
    val timestamp: Long         // 时间戳（用于排序、显示）
)
