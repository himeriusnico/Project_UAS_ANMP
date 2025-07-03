package com.ubaya.project_uas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.project_uas.R
import com.ubaya.project_uas.databinding.FragmentEditBudgetingBinding
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.viewmodel.DetailBudgetingViewModel

class EditBudgetingFragment : Fragment() {
    private lateinit var binding: FragmentEditBudgetingBinding
    private lateinit var viewModel: DetailBudgetingViewModel
    private var budget: Budget? = null
    private var totalUsed: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditBudgetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DetailBudgetingViewModel::class.java]
        val budgetId = EditBudgetingFragmentArgs.fromBundle(requireArguments()).budgetId

        viewModel.fetchBudgetById(budgetId)
        viewModel.fetchTotalUsedByBudgetIdLive(budgetId).observe(viewLifecycleOwner) {
            totalUsed = it ?: 0
        }

        binding.btnUpdate.text = "Save"
        binding.txtEditBudget.text = "Edit Budget"

        viewModel.budgetLD.observe(viewLifecycleOwner) { data ->
            budget = data
            data?.let {
                binding.txtTitle.setText(it.name)
                binding.txtNominal.setText(it.amount.toString())
            }
        }

        binding.btnUpdate.setOnClickListener {
            val updatedName = binding.txtTitle.text.toString()
            val updatedAmount = binding.txtNominal.text.toString().toIntOrNull() ?: 0

            if (updatedAmount < totalUsed) {
                Toast.makeText(requireContext(), "Nominal tidak boleh kurang dari total pengeluaran: IDR $totalUsed", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            budget?.let { oldBudget ->
                val updatedBudget = Budget(
                    name = updatedName,
                    amount = updatedAmount,
                    createdAt = oldBudget.createdAt,
                    userId = oldBudget.userId
                ).also {
                    it.id = oldBudget.id //biar room tahu ini update, bukan insert
                }
                viewModel.updateBudget(updatedBudget)
                Toast.makeText(requireContext(), "Budget updated!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
        binding.btnDelete.setOnClickListener {
            budget?.let {
                viewModel.deleteBudgetWithExpenses(it.id)
                Toast.makeText(requireContext(), "Budget deleted!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }
}