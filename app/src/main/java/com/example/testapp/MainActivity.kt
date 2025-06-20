package com.example.testapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    // 加载native库
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
    
    // 声明native方法
    external fun processTextFromNative(input: String): String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.button.setOnClickListener {
            val inputText = binding.editText.text.toString()
            
            if (inputText.isEmpty()) {
                Toast.makeText(this, "请输入一些文字", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            try {
                // 调用native接口处理文字
                val result = processTextFromNative(inputText)
                binding.resultTextView.text = result
            } catch (e: Exception) {
                binding.resultTextView.text = "Native接口调用失败: ${e.message}"
            }
        }
    }
} 