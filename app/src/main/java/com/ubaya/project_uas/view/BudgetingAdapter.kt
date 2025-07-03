package com.ubaya.project_uas.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.project_uas.databinding.BudgetItemBinding
import com.ubaya.project_uas.model.Budget

class BudgetingAdapter(
    private val budgets: List<Budget>,
    private val onBudgetClick: (Budget) -> Unit
) : RecyclerView.Adapter<BudgetingAdapter.BudgetViewHolder>() {

    class BudgetViewHolder(val binding: BudgetItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BudgetItemBinding.inflate(inflater, parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgets[position]
        holder.binding.txtBudgetName.text = budget.name
        holder.binding.txtBudgetNominal.text = "IDR ${budget.amount}"

        holder.binding.root.setOnClickListener {
            onBudgetClick(budget)
        }
    }

    override fun getItemCount(): Int = budgets.size
}
