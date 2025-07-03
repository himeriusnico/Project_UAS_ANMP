package com.ubaya.project_uas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.project_uas.databinding.ItemReportBinding
import com.ubaya.project_uas.model.BudgetWithTotalUsed

class ReportAdapter(private val budgetList: List<BudgetWithTotalUsed>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budget: BudgetWithTotalUsed) {
            binding.txtExpenseReport.text = budget.name
            binding.txtExpensesPriceReport.text = "Rp${budget.used}"
            binding.txtBudgetReport.text = "Rp${budget.amount}"

            val left = budget.amount - budget.used
            binding.txtBudgetLeftReport.text = "Rp$left"

            val percentage = if (budget.amount != 0) {
                (budget.used.toFloat() / budget.amount * 100).toInt()
            } else 0

            binding.progressBar.max = 100
            binding.progressBar.progress = percentage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReportBinding.inflate(inflater, parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(budgetList[position])
    }

    override fun getItemCount(): Int = budgetList.size
}
