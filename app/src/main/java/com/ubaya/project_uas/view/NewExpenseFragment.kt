package com.ubaya.project_uas.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.project_uas.databinding.FragmentNewExpenseBinding
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.Expense
import com.ubaya.project_uas.utils.SessionManager
import com.ubaya.project_uas.viewmodel.NewExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewExpenseFragment : Fragment() {
    private lateinit var binding: FragmentNewExpenseBinding
    private lateinit var viewModel: NewExpenseViewModel
    private var selectedBudget: Budget? = null
    private var totalSpent = 0
    private val userId: Int
        get() {
            val sharedPref = requireContext().getSharedPreferences("com.ubaya.project_uas.PREF", android.content.Context.MODE_PRIVATE)
            return sharedPref.getInt("user_id", -1)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NewExpenseViewModel::class.java]



        // Set current date
        val formattedDate =
            SimpleDateFormat("dd MMMM yyyy HH.mm a", Locale.getDefault()).format(Date())
        binding.txtDate.text = formattedDate

        // Observe budget list and populate spinner
        viewModel.getBudgetsByUser(userId).observe(viewLifecycleOwner) { budgetList ->

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                budgetList.map { it.name } // only names shown in spinner
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerExpense.adapter = adapter

            // Handle selection
            binding.spinnerExpense.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedBudget = budgetList[position]

                        // Show budget value
                        binding.txtBudget.text = "Rp${selectedBudget!!.amount}"

                        // Observe total spent for selected budget
                        viewModel.getTotalSpentForBudget(selectedBudget!!.id, userId)
                            .observe(viewLifecycleOwner) { total ->
                                totalSpent = total ?: 0
                                binding.txtCurrentSpending.text = "Rp$totalSpent"

                                // Update progress bar
                                val progressPercent =
                                    (totalSpent * 100 / selectedBudget!!.amount).coerceAtMost(100)
                                binding.progressBarExpense.progress = progressPercent
                            }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        selectedBudget = null
                    }
                }
        }

        // Handle Add Expense button
        binding.btnAddExpense.setOnClickListener {
            val amountStr = binding.txtExpensePrice.text.toString()
            val note = binding.txtNominal.text.toString()

            if (selectedBudget == null) {
                Toast.makeText(requireContext(), "Please select a budget", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val amount = amountStr.toIntOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(
                    requireContext(),
                    "Enter a valid positive amount",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (amount + totalSpent > selectedBudget!!.amount) {
                Toast.makeText(
                    requireContext(),
                    "Amount exceeds remaining budget",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val expense = Expense(
                budgetId = selectedBudget!!.id,
                amount = amount,
                description = note,
                createdAt = System.currentTimeMillis()
            )

            viewModel.insertExpense(expense)

            Toast.makeText(requireContext(), "Expense added", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}
