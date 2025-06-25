package com.ubaya.project_uas.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.ubaya.project_uas.R
import com.ubaya.project_uas.model.UserDatabase
import com.ubaya.project_uas.utils.SessionManager
import com.ubaya.project_uas.view.MainActivity

class SignInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val tvToSignUp = view.findViewById<TextView>(R.id.tvToSignUp)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Username dan Password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = UserDatabase(requireContext())
            Thread {
                val user = db.userDao().login(username, password)
                requireActivity().runOnUiThread {
                    if (user != null) {
                        SessionManager(requireContext()).saveUserId(user.id)
                        Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    } else {
                        Toast.makeText(context, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }

        tvToSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
