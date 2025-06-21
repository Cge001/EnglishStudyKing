package com.example.testapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.ActivityMainBinding
import android.view.View

// 单词数据类
data class Word(
    val number: Int,
    val word: String,
    val meaning: String,
    val isVisible: Boolean
)

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    private val words = mutableListOf<Word>()
    private val handler = Handler(Looper.getMainLooper())
    
    // 加载native库
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupButtons()
    }
    
    private fun setupRecyclerView() {
        wordAdapter = WordAdapter(words) { position ->
            wordAdapter.toggleVisibility(position)
        }
        binding.wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = wordAdapter
        }
    }
    
    private fun setupButtons() {
        binding.button.setOnClickListener {
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
        
        binding.btnWords.setOnClickListener {
            generateRandomWords()
        }
    }
    
    private fun showLoading(show: Boolean) {
        binding.loadingProgressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.button.isEnabled = !show
        binding.btnWords.isEnabled = !show
    }
    
    private fun showResult(result: String) {
        binding.resultScrollView.visibility = View.VISIBLE
        binding.resultTextView.text = result
    }
    
    private fun callNativeInterface(input: String): String {
        return try {
            stringFromJNI(input)
        } catch (e: Exception) {
            "Native接口调用失败: ${e.message}"
        }
    }
    
    private fun generateRandomWords() {
        val wordList = listOf(
            "apple" to "苹果",
            "book" to "书",
            "cat" to "猫",
            "dog" to "狗",
            "elephant" to "大象",
            "flower" to "花",
            "garden" to "花园",
            "house" to "房子",
            "ice" to "冰",
            "juice" to "果汁",
            "king" to "国王",
            "lion" to "狮子",
            "moon" to "月亮",
            "night" to "夜晚",
            "orange" to "橙子",
            "pencil" to "铅笔",
            "queen" to "女王",
            "rain" to "雨",
            "sun" to "太阳",
            "tree" to "树"
        )

        words.clear()
        val shuffledWords = wordList.shuffled().take(10)
        shuffledWords.forEachIndexed { index, (english, chinese) ->
            words.add(Word(index + 1, english, chinese, false))
        }
        wordAdapter.notifyDataSetChanged()
    }
    
    external fun stringFromJNI(input: String): String
} 