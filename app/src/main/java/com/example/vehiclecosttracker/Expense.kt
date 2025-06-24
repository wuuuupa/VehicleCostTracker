// ✅ 文件路径 5：
// app/src/main/java/com/example/vehiclecosttracker/Expense.kt

package com.example.vehiclecosttracker

data class Expense(
    val id: Int,
    val amount: Double,
    val note: String,
    val timestamp: String,
    val vehicle: String // ✅ 新增字段
)
