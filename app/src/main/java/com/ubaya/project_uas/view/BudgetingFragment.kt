package com.ubaya.project_uas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.project_uas.databinding.FragmentBudgetingBinding
import com.ubaya.project_uas.viewmodel.BudgetingViewModel

class BudgetingFragment : Fragment() {
    private lateinit var binding: FragmentBudgetingBinding
    private lateinit var viewModel: BudgetingViewModel
    private var userId = 1 // Replace this with actual login user ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BudgetingViewModel::class.java]
        binding.recyclerBudget.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getBudgetsByUser(userId).observe(viewLifecycleOwner) { budgetList ->
            binding.progressBar.visibility = View.GONE
            if (budgetList.isEmpty()) {
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = "No budgets found"
            } else {
                binding.txtError.visibility = View.GONE
                binding.recyclerBudget.adapter = BudgetingAdapter(budgetList) { budget ->
                    Toast.makeText(requireContext(), "Edit ${budget.name}", Toast.LENGTH_SHORT).show()
                    // TODO: Navigate to edit screen
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add new budget", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to add budget screen
        }
    }
}
