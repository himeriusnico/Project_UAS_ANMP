package com.ubaya.project_uas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.project_uas.R
import com.ubaya.project_uas.databinding.FragmentNewBudgetingBinding
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.viewmodel.NewBudgetingViewModel

class NewBudgetingFragment : Fragment() {
    private lateinit var binding: FragmentNewBudgetingBinding
    private lateinit var viewModel: NewBudgetingViewModel
    private val userId: Int
        get() {
            val sharedPref = requireContext().getSharedPreferences("com.ubaya.project_uas.PREF", android.content.Context.MODE_PRIVATE)
            return sharedPref.getInt("user_id", -1)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewBudgetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (userId == -1) {
            Toast.makeText(requireContext(), "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.signInFragment)
            return
        }

        viewModel = ViewModelProvider(this)[NewBudgetingViewModel::class.java]

        binding.btnAdd.setOnClickListener{
            val name = binding.txtNominal.text.toString()
            val amount = binding.txtNominal.text.toString()
                .replace("Rp", "")
                .replace(".", "")
                .trim()

            if (name.isNotEmpty() && amount.isNotEmpty()) {
                val amount = amount.toIntOrNull() ?: 0
                val budget = Budget(name, amount, System.currentTimeMillis(), userId = userId)
                viewModel.addBudgeting(budget)
            } else {
                Toast.makeText(requireContext(), "Nama dan nominal harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.budgetingCreateStatusLD.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Budget berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).popBackStack()
            } else {
                Toast.makeText(requireContext(), "Gagal menambahkan budget", Toast.LENGTH_SHORT).show()
            }
        }
    }
}