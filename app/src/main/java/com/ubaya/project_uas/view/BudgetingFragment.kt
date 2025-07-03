package com.ubaya.project_uas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.project_uas.R
import com.ubaya.project_uas.databinding.FragmentBudgetingBinding
import com.ubaya.project_uas.viewmodel.BudgetingViewModel

class BudgetingFragment : Fragment() {
    private lateinit var binding: FragmentBudgetingBinding
    private lateinit var viewModel: BudgetingViewModel
    private val userId: Int
        get() {
            val sharedPref = requireContext().getSharedPreferences("com.ubaya.project_uas.PREF", android.content.Context.MODE_PRIVATE)
            return sharedPref.getInt("user_id", -1)
        }

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

        binding.progressBar.visibility = View.VISIBLE
        binding.txtError.visibility = View.GONE

        viewModel.getBudgetsByUser(userId).observe(viewLifecycleOwner) { budgetList ->
            binding.progressBar.visibility = View.GONE
            if (budgetList.isEmpty()) {
                binding.txtError.visibility = View.VISIBLE
                binding.recyclerBudget.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.GONE
                binding.recyclerBudget.visibility = View.VISIBLE
                binding.recyclerBudget.adapter = BudgetingAdapter(budgetList) { budget ->
                    Toast.makeText(requireContext(), "Edit ${budget.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = BudgetingFragmentDirections.actionBudgetingFragmentToNewBudgetingFragment()
            findNavController().navigate(action)
        }
    }
}
