package com.ubaya.project_uas.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.project_uas.databinding.ItemExpenseTrackerBinding
import com.ubaya.project_uas.model.ExpenseDisplay
import java.text.SimpleDateFormat
import java.util.*

class ExpenseTrackerAdapter(
    private val expenses: List<ExpenseDisplay>,
    private val onAmountClick: (ExpenseDisplay) -> Unit
) : RecyclerView.Adapter<ExpenseTrackerAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(val binding: ItemExpenseTrackerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExpenseTrackerBinding.inflate(inflater, parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        val formattedDate =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(expense.createdAt))

        holder.binding.txtExpenseReport.text = formattedDate
        holder.binding.txtBudgetReport.text = "Rp${expense.amount}"
        holder.binding.chip.text = expense.budgetName

        // Handle amount click
        holder.binding.txtBudgetReport.setOnClickListener {
            onAmountClick(expense)
        }
    }

    override fun getItemCount(): Int = expenses.size
}
