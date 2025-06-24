package com.example.vehiclecosttracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val expenseList: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvNote: TextView = itemView.findViewById(R.id.tvNote)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val tvVehicleName: TextView = itemView.findViewById(R.id.tvVehicleName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.tvAmount.text = "¥${expense.amount}"
        holder.tvNote.text = expense.note
        holder.tvTimestamp.text = expense.timestamp
        holder.tvVehicleName.text = "车辆：${expense.vehicleName}"
    }

    override fun getItemCount(): Int = expenseList.size
}
