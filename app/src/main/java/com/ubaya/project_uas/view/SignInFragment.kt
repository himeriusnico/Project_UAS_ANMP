package com.ubaya.project_uas.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.project_uas.R
import com.ubaya.project_uas.databinding.FragmentSignInBinding

import com.ubaya.project_uas.viewmodel.SignInViewModel

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        observeViewModel()

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Username dan Password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(username, password)
            }
        }

        binding.txtSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observeViewModel() {
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            user?.let {
                Toast.makeText(context, "Login berhasil!", Toast.LENGTH_SHORT).show()

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}