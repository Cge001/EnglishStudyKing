package com.example.testapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityNativeInterfaceBinding

class NativeInterfaceActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityNativeInterfaceBinding
    private val handler = Handler(Looper.getMainLooper())
    
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNativeInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupButtons()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    
    private fun setupButtons() {
        binding.button.setOnClickListener {
            // 隐藏软键盘
            hideSoftKeyboard()
            
            val input = binding.editText.text.toString()
            if (input.isNotEmpty()) {
                showLoading(true)
                // 使用后台线程调用native接口
                Thread {
                    val result = callNativeInterface(input)
                    handler.post {
                        showLoading(false)
                        showResult(result)
                    }
                }.start()
            } else {
                Toast.makeText(this, "请输入文字", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
    
    private fun showLoading(show: Boolean) {
        binding.loadingProgressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.button.isEnabled = !show
    }
    
    private fun showResult(result: String) {
        binding.resultScrollView.visibility = View.VISIBLE
        binding.resultTextView.text = result
    }
    
    private fun callNativeInterface(input: String): String {
        return try {
            MainActivity.stringFromJNI(input)
        } catch (e: Exception) {
            "Native接口调用失败: ${e.message}"
        }
    }
} 