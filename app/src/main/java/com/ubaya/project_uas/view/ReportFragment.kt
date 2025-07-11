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
    private val userId: Int
        get() {
            val sharedPref = requireContext().getSharedPreferences(
                "com.ubaya.project_uas.PREF",
                android.content.Context.MODE_PRIVATE
            )
            return sharedPref.getInt("user_id", -1)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        viewModel = ViewModelProvider(this)[ReportViewModel::class.java]

        // RecyclerView
        binding.recyclerReport.layoutManager = LinearLayoutManager(requireContext())

        // Observe data
        viewModel.getBudgetsWithTotalUsed(userId).observe(viewLifecycleOwner) { budgetList ->
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
