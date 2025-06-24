package com.example.vehiclecosttracker

data class Expense(
    val id: Long,
    val amount: Double,
    val note: String,
    val timestamp: String,
    val vehicleName: String // 新增字段：车辆名称
)
