package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.testapp.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    
    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupDrawer()
        setupClickListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }
    
    private fun setupDrawer() {
        drawerLayout = binding.drawerLayout
        val navigationView = binding.navigationView
        
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        
        navigationView.setNavigationItemSelectedListener(this)
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
    
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // 已经在首页，关闭抽屉
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_english_learning -> {
                val intent = Intent(this, EnglishLearningActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_native_interface -> {
                val intent = Intent(this, NativeInterfaceActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_about -> {
                showAboutDialog()
            }
            R.id.nav_version -> {
                showVersionDialog()
            }
            R.id.nav_features -> {
                showFeaturesDialog()
            }
            R.id.nav_tech_stack -> {
                showTechStackDialog()
            }
            R.id.nav_github -> {
                // 打开GitHub仓库
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = android.net.Uri.parse("https://github.com/Cge001/EnglishStudyKing")
                startActivity(intent)
            }
            R.id.nav_share -> {
                shareApp()
            }
        }
        
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("关于应用")
            .setMessage("""
                EnglishStudyKing 是一个专为英语学习设计的Android应用。
                
                主要特点：
                • 智能文字处理，支持中英文混合输入
                • 单词记忆系统，帮助学习英语词汇
                • 现代化UI设计，用户体验流畅
                • 集成C++ Native代码，高性能处理
                
                开发者：AI助手
                版本：v1.0.4
            """.trimIndent())
            .setPositiveButton("确定", null)
            .show()
    }
    
    private fun showVersionDialog() {
        AlertDialog.Builder(this)
            .setTitle("版本信息")
            .setMessage("""
                当前版本：v1.0.4
                Version Code：5
                
                更新内容：
                ✅ 修复中文输入导致应用崩溃的问题
                ✅ 改进UTF-8字符处理，支持中英文混合输入
                ✅ 优化单词记忆界面，支持显示/隐藏翻译
                ✅ 更新应用名称为EnglishStudyKing
                ✅ 完善异常处理机制，提高应用稳定性
                ✅ 添加侧滑菜单功能
                ✅ 优化软键盘处理
                
                系统要求：Android 5.0 (API 21) 及以上
            """.trimIndent())
            .setPositiveButton("确定", null)
            .show()
    }
    
    private fun showFeaturesDialog() {
        AlertDialog.Builder(this)
            .setTitle("功能特性")
            .setMessage("""
                🔤 智能文字处理
                • 支持中英文混合输入
                • 调用C++ Native接口进行文字处理
                • 安全处理各种字符编码
                
                📚 单词记忆系统
                • 随机生成英文单词
                • 每个单词都包含中文释义
                • 支持显示/隐藏翻译功能
                
                🎨 现代化UI
                • 采用Material Design设计语言
                • 界面美观，用户体验流畅
                • 响应式设计，适配各种设备
                
                ⚡ 高性能处理
                • 集成C++ Native代码
                • 提供高性能的文字处理能力
                • 支持UTF-8编码
            """.trimIndent())
            .setPositiveButton("确定", null)
            .show()
    }
    
    private fun showTechStackDialog() {
        AlertDialog.Builder(this)
            .setTitle("技术栈")
            .setMessage("""
                🛠️ 开发技术：
                
                • Kotlin - 主要开发语言
                • Android SDK - 原生Android开发
                • C++ - Native代码处理
                • CMake - 跨平台构建系统
                • JNI - Java Native Interface
                • UTF-8 - 字符编码支持
                • Gradle - 项目构建工具
                • Material Design - UI设计规范
                • View Binding - 视图绑定
                
                📱 架构特点：
                • 原生Android应用
                • 混合开发模式
                • 高性能Native处理
                • 现代化UI设计
            """.trimIndent())
            .setPositiveButton("确定", null)
            .show()
    }
    
    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EnglishStudyKing - 英语学习王")
        shareIntent.putExtra(Intent.EXTRA_TEXT, """
            推荐一个超棒的英语学习应用！
            
            🏆 EnglishStudyKing - 英语学习王
            📱 智能单词记忆，支持中英文混合处理
            🔗 下载地址：https://github.com/Cge001/EnglishStudyKing
            
            功能特点：
            • 智能文字处理
            • 单词记忆系统
            • 现代化UI设计
            • 高性能Native处理
            
            快来试试吧！
        """.trimIndent())
        
        startActivity(Intent.createChooser(shareIntent, "分享应用"))
    }
    
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
} 