package com.ubaya.project_uas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.project_uas.databinding.FragmentSignUpBinding // Pastikan nama ini sesuai file layout Anda

import com.ubaya.project_uas.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        // Mengamati perubahan dari ViewModel
        observeViewModel()

        // Menangani klik pada tombol register
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val password = binding.etPassword.text.toString()

            // Fragment hanya mengirim data, ViewModel yang akan memvalidasi
            viewModel.register(username, firstName, lastName, password)
        }

        // Menangani klik untuk kembali ke halaman Sign In
        binding.tvToSignIn.setOnClickListener {
            // Kembali ke fragment sebelumnya (SignInFragment)
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeViewModel() {
        // Mengamati jika registrasi berhasil
        viewModel.registrationSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "Registrasi berhasil! Silakan login.", Toast.LENGTH_LONG).show()
                // Kembali ke halaman Sign In setelah berhasil
                parentFragmentManager.popBackStack()
            }
        }

        // Mengamati jika ada pesan error
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Tampilkan pesan error jika ada
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}