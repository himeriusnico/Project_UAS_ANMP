package com.ubaya.project_uas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.project_uas.R
import com.ubaya.project_uas.databinding.FragmentExpenseTrackerBinding
import com.ubaya.project_uas.viewmodel.ExpenseViewModel


class ExpenseTrackerFragment : Fragment() {

    private lateinit var binding: FragmentExpenseTrackerBinding
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseTrackerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        // RecyclerView setup
        binding.recyclerViewExpenseTracker.layoutManager = LinearLayoutManager(requireContext())

        // Observe ViewModel
        viewModel.expenses.observe(viewLifecycleOwner) { expenseList ->
            adapter = ExpenseTrackerAdapter(expenseList)
            binding.recyclerViewExpenseTracker.adapter = adapter
        }

        // FloatingActionButton click
        binding.fabExpenseTracker.setOnClickListener {
            // TODO: navigate to add expense screen
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }
}