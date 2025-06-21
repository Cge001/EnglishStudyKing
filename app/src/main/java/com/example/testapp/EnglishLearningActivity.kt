package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.ActivityEnglishLearningBinding

class EnglishLearningActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityEnglishLearningBinding
    private lateinit var wordAdapter: WordAdapter
    private val words = mutableListOf<Word>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnglishLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
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
    
    private fun setupRecyclerView() {
        wordAdapter = WordAdapter(words) { position ->
            wordAdapter.toggleVisibility(position)
        }
        binding.wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@EnglishLearningActivity)
            adapter = wordAdapter
        }
    }
    
    private fun setupButtons() {
        binding.btnWords.setOnClickListener {
            generateRandomWords()
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
} 