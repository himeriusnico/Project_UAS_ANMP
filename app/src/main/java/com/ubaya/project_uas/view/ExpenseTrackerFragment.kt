package com.ubaya.project_uas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.project_uas.R
import com.ubaya.project_uas.databinding.DialogExpenseDetailBinding
import com.ubaya.project_uas.databinding.FragmentExpenseTrackerBinding
import com.ubaya.project_uas.model.ExpenseDisplay
import com.ubaya.project_uas.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseTrackerFragment : Fragment() {

    private lateinit var binding: FragmentExpenseTrackerBinding
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseTrackerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_expenseTrackerFragment_to_newExpenseFragment)
        }

    }

    // Click handler for expense amount
    private fun onAmountClick(expense: ExpenseDisplay) {
        val dialogBinding = DialogExpenseDetailBinding.inflate(LayoutInflater.from(requireContext()))

        // Set values using ViewBinding
        val formattedDate =
            SimpleDateFormat("dd MMM yyyy HH.mm a", Locale("id")).format(Date(expense.createdAt))
        dialogBinding.txtDate.text = formattedDate
        dialogBinding.txtDescription.text = expense.description
        dialogBinding.chip2.text = expense.budgetName
        dialogBinding.txtAmount.text = "IDR %,d".format(expense.amount)

        // Build the dialog with custom view
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}
