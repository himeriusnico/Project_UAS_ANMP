package com.ubaya.project_uas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ubaya.project_uas.R
import com.ubaya.project_uas.databinding.ActivityMainBinding
import com.ubaya.project_uas.view.SignInFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
