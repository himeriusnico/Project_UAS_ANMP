package com.ubaya.project_uas.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubaya.project_uas.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("com.ubaya.project_uas.PREF", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_id", -1)

        if (userId != -1) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else{
            setContentView(R.layout.activity_auth)
        }
    }
}