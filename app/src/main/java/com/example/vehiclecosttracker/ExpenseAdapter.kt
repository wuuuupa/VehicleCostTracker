package com.example.vehiclecosttracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val expenseList: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amountTextView: TextView = itemView.findViewById(R.id.tvAmount)
        val noteTextView: TextView = itemView.findViewById(R.id.tvNote)
        val timeTextView: TextView = itemView.findViewById(R.id.tvTimestamp)
        val vehicleTextView: TextView = itemView.findViewById(R.id.tvVehicle) // 显示车辆名称
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.amountTextView.text = "金额：¥${expense.amount}"
        holder.noteTextView.text = "备注：${expense.note}"
        holder.timeTextView.text = "时间：${expense.timestamp}"
        holder.vehicleTextView.text = "车辆：${expense.vehicle}" // 设置车辆名
    }

    override fun getItemCount(): Int = expenseList.size
}
