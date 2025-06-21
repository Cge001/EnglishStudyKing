package com.example.testapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHomeBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // 英语学习按钮
        binding.btnEnglishLearning.setOnClickListener {
            val intent = Intent(this, EnglishLearningActivity::class.java)
            startActivity(intent)
        }
        
        // Native接口按钮
        binding.btnNativeInterface.setOnClickListener {
            val intent = Intent(this, NativeInterfaceActivity::class.java)
            startActivity(intent)
        }
    }
} 