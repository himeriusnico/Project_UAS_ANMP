package com.ubaya.project_uas.view

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.ubaya.project_uas.R
import com.ubaya.project_uas.model.User
import com.ubaya.project_uas.model.UserDatabase

class SignUpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etFirstName = view.findViewById<EditText>(R.id.etFirstName)
        val etLastName = view.findViewById<EditText>(R.id.etLastName)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val tvToSignIn = view.findViewById<TextView>(R.id.tvToSignIn)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val password = etPassword.text.toString()
            val confirm = etConfirmPassword.text.toString()

            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(context, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(context, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = UserDatabase(requireContext())
            Thread {
                val existing = db.userDao().getUserByUsername(username)
                if (existing != null) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Username sudah digunakan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    db.userDao().insertUser(User(username, firstName, lastName, password))
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Registrasi berhasil, silakan login", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.popBackStack()
                    }
                }
            }.start()
        }

        tvToSignIn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
