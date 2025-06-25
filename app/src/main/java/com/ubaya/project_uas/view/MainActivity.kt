package com.ubaya.project_uas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ubaya.project_uas.R
import com.ubaya.project_uas.view.SignInFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Jika pertama kali dijalankan dan belum ada fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignInFragment())
                .commit()
        }
    }
}
