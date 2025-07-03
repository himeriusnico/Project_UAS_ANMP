package com.ubaya.project_uas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.project_uas.databinding.FragmentReportBinding
import com.ubaya.project_uas.viewmodel.ReportViewModel
import com.ubaya.project_uas.adapter.ReportAdapter
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.BudgetWithTotalUsed

class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModel: ReportViewModel
    private lateinit var adapter: ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel (done in onViewCreated for consistency)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        viewModel = ViewModelProvider(this)[ReportViewModel::class.java]

        // RecyclerView
        binding.recyclerReport.layoutManager = LinearLayoutManager(requireContext())

        // Observe data
        viewModel.budgets.observe(viewLifecycleOwner) { budgetList ->
            adapter = ReportAdapter(budgetList)
            binding.recyclerReport.adapter = adapter
            updateSummary(budgetList)
        }
    }

    private fun updateSummary(budgetList: List<BudgetWithTotalUsed>) {
        val totalUsed = budgetList.sumOf { it.used }
        val totalMax = budgetList.sumOf { it.amount }

        binding.txtTotalExpenses.text = "Rp$totalUsed"
        binding.txtBudgetReportFragment.text = "Rp$totalMax"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }
}
