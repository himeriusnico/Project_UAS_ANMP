package com.ubaya.project_uas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.project_uas.databinding.FragmentExpenseTrackerBinding
import com.ubaya.project_uas.model.ExpenseDisplay
import com.ubaya.project_uas.viewmodel.ExpenseViewModel

class ExpenseTrackerFragment : Fragment() {

    private lateinit var binding: FragmentExpenseTrackerBinding
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseTrackerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // No special logic in onCreate for now
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup ViewModel
        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        // Setup RecyclerView
        binding.recyclerViewExpenseTracker.layoutManager = LinearLayoutManager(requireContext())

        // Observe expenses
        viewModel.expenses.observe(viewLifecycleOwner) { expenseList ->
            adapter = ExpenseTrackerAdapter(expenseList, ::onAmountClick)
            binding.recyclerViewExpenseTracker.adapter = adapter
        }

        // FloatingActionButton click (optional)
        binding.fabExpenseTracker.setOnClickListener {
            // TODO: navigate to add expense screen
        }
    }

    // Click handler for expense amount
    private fun onAmountClick(expense: ExpenseDisplay) {
        // You can show a Toast, navigate to detail screen, or log
        // Example:
        println("Clicked amount: Rp${expense.amount} | Description: ${expense.description}")
    }
}
