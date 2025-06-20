package com.example.testapp

import org.junit.Assert.*
import org.junit.Test

class NativeTest {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun processTextFromNative(input: String): String

    @Test
    fun testNativeProcess() {
        val result = processTextFromNative("abc 123")
        assertTrue(result.contains("Native接口处理结果"))
        assertTrue(result.contains("字母数: 3"))
        assertTrue(result.contains("数字数: 3"))
    }
} 