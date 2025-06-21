package com.example.testapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityMainBinding

// 单词数据类
data class Word(
    val word: String,
    val meaning: String
)

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
        Word("apple", "苹果"),
        Word("banana", "香蕉"),
        Word("orange", "橙子"),
        Word("grape", "葡萄"),
        Word("peach", "桃子"),
        Word("pear", "梨"),
        Word("plum", "李子"),
        Word("melon", "甜瓜"),
        Word("kiwi", "猕猴桃"),
        Word("lemon", "柠檬"),
        Word("cat", "猫"),
        Word("dog", "狗"),
        Word("fish", "鱼"),
        Word("bird", "鸟"),
        Word("horse", "马"),
        Word("sheep", "羊"),
        Word("tiger", "老虎"),
        Word("lion", "狮子"),
        Word("bear", "熊"),
        Word("wolf", "狼"),
        Word("table", "桌子"),
        Word("chair", "椅子"),
        Word("desk", "书桌"),
        Word("sofa", "沙发"),
        Word("bed", "床"),
        Word("lamp", "台灯"),
        Word("door", "门"),
        Word("window", "窗户"),
        Word("floor", "地板"),
        Word("ceiling", "天花板"),
        Word("computer", "电脑"),
        Word("phone", "手机"),
        Word("mouse", "鼠标"),
        Word("keyboard", "键盘"),
        Word("screen", "屏幕"),
        Word("printer", "打印机"),
        Word("camera", "相机"),
        Word("speaker", "扬声器"),
        Word("router", "路由器"),
        Word("cable", "电缆"),
        Word("book", "书"),
        Word("pen", "钢笔"),
        Word("pencil", "铅笔"),
        Word("paper", "纸"),
        Word("notebook", "笔记本"),
        Word("bag", "包"),
        Word("clock", "时钟"),
        Word("watch", "手表"),
        Word("calendar", "日历"),
        Word("map", "地图"),
        Word("river", "河流"),
        Word("mountain", "山"),
        Word("forest", "森林"),
        Word("ocean", "海洋"),
        Word("lake", "湖泊"),
        Word("island", "岛屿"),
        Word("valley", "山谷"),
        Word("desert", "沙漠"),
        Word("beach", "海滩"),
        Word("hill", "小山"),
        Word("happy", "快乐的"),
        Word("sad", "悲伤的"),
        Word("angry", "生气的"),
        Word("excited", "兴奋的"),
        Word("bored", "无聊的"),
        Word("tired", "疲倦的"),
        Word("scared", "害怕的"),
        Word("brave", "勇敢的"),
        Word("calm", "平静的"),
        Word("proud", "骄傲的"),
        Word("run", "跑"),
        Word("walk", "走"),
        Word("jump", "跳"),
        Word("swim", "游泳"),
        Word("fly", "飞"),
        Word("drive", "驾驶"),
        Word("ride", "骑"),
        Word("climb", "爬"),
        Word("crawl", "爬行"),
        Word("dance", "跳舞"),
        Word("red", "红色"),
        Word("blue", "蓝色"),
        Word("green", "绿色"),
        Word("yellow", "黄色"),
        Word("black", "黑色"),
        Word("white", "白色"),
        Word("purple", "紫色"),
        Word("brown", "棕色"),
        Word("pink", "粉色"),
        Word("gray", "灰色"),
        Word("spring", "春天"),
        Word("summer", "夏天"),
        Word("autumn", "秋天"),
        Word("winter", "冬天"),
        Word("rain", "雨"),
        Word("snow", "雪"),
        Word("wind", "风"),
        Word("cloud", "云"),
        Word("sun", "太阳"),
        Word("moon", "月亮")
    )

    private fun getRandomWords(n: Int = 20): List<Word> {
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
                append("请背诵以下单词：\n\n")
                words.forEachIndexed { i, word ->
                    append("${i + 1}. ${word.word} - ${word.meaning}\n")
                }
            }
            binding.resultTextView.text = result
        }
    }
} 