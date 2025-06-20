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
    
    private val WORD_LIST = listOf(
        "apple", "banana", "orange", "grape", "peach", "pear", "plum", "melon", "kiwi", "lemon",
        "cat", "dog", "fish", "bird", "horse", "sheep", "tiger", "lion", "bear", "wolf",
        "table", "chair", "desk", "sofa", "bed", "lamp", "door", "window", "floor", "ceiling",
        "computer", "phone", "mouse", "keyboard", "screen", "printer", "camera", "speaker", "router", "cable",
        "book", "pen", "pencil", "paper", "notebook", "bag", "clock", "watch", "calendar", "map",
        "river", "mountain", "forest", "ocean", "lake", "island", "valley", "desert", "beach", "hill",
        "happy", "sad", "angry", "excited", "bored", "tired", "scared", "brave", "calm", "proud",
        "run", "walk", "jump", "swim", "fly", "drive", "ride", "climb", "crawl", "dance",
        "red", "blue", "green", "yellow", "black", "white", "purple", "brown", "pink", "gray",
        "spring", "summer", "autumn", "winter", "rain", "snow", "wind", "cloud", "sun", "moon"
    )

    private fun getRandomWords(n: Int = 20): List<String> {
        return WORD_LIST.shuffled().take(n)
    }
    
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
        binding.btnWords.setOnClickListener {
            val words = getRandomWords(20)
            val result = buildString {
                append("请背诵以下单词：\n")
                words.forEachIndexed { i, word ->
                    append("${i + 1}. $word\n")
                }
            }
            binding.resultTextView.text = result
        }
    }
} 