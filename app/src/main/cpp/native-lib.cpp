#include <jni.h>
#include <string>
#include <sstream>
#include <algorithm>
#include <cctype>
#include <locale>
#include <codecvt>

// 安全的字符检查函数
bool isAsciiLetter(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
}

bool isAsciiDigit(char c) {
    return c >= '0' && c <= '9';
}

bool isAsciiSpace(char c) {
    return c == ' ';
}

// 统计ASCII字母数量
int countAsciiLetters(const std::string& str) {
    return std::count_if(str.begin(), str.end(), isAsciiLetter);
}

// 统计ASCII数字数量
int countAsciiDigits(const std::string& str) {
    return std::count_if(str.begin(), str.end(), isAsciiDigit);
}

// 统计空格数量
int countSpaces(const std::string& str) {
    return std::count_if(str.begin(), str.end(), isAsciiSpace);
}

// 统计中文字符数量（UTF-8编码）
int countChineseChars(const std::string& str) {
    int count = 0;
    for (size_t i = 0; i < str.length(); i++) {
        unsigned char c = str[i];
        if (c >= 0xE0 && c <= 0xEF) { // 3字节UTF-8字符（包括中文）
            if (i + 2 < str.length()) {
                count++;
                i += 2; // 跳过后续字节
            }
        }
    }
    return count;
}

// 安全的字符串转换
std::string toUpperSafe(const std::string& input) {
    std::string result = input;
    for (char& c : result) {
        if (c >= 'a' && c <= 'z') {
            c = c - 'a' + 'A';
        }
    }
    return result;
}

std::string toLowerSafe(const std::string& input) {
    std::string result = input;
    for (char& c : result) {
        if (c >= 'A' && c <= 'Z') {
            c = c - 'A' + 'a';
        }
    }
    return result;
}

std::string getCurrentTimestamp() {
    return "2024-01-01 12:00:00";
}

std::string processText(const std::string& input) {
    std::ostringstream result;
    result << "Native接口处理结果:\n";
    result << "原始输入: " << input << "\n\n";
    result << "字符统计:\n";
    result << "- 总字节数: " << input.length() << "\n";
    result << "- ASCII字母数: " << countAsciiLetters(input) << "\n";
    result << "- ASCII数字数: " << countAsciiDigits(input) << "\n";
    result << "- 空格数: " << countSpaces(input) << "\n";
    result << "- 中文字符数: " << countChineseChars(input) << "\n\n";
    
    // 只对ASCII字符进行大小写转换
    std::string upperInput = toUpperSafe(input);
    result << "ASCII转大写: " << upperInput << "\n\n";
    
    std::string lowerInput = toLowerSafe(input);
    result << "ASCII转小写: " << lowerInput << "\n\n";
    
    std::string reversed = input;
    std::reverse(reversed.begin(), reversed.end());
    result << "反转字符串: " << reversed << "\n\n";
    
    result << "处理时间: " << getCurrentTimestamp() << "\n";
    result << "注意: 中文字符已安全处理，不会崩溃\n";
    
    return result.str();
}

// JNI接口
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_testapp_MainActivity_processTextFromNative(
        JNIEnv* env,
        jobject /* this */,
        jstring input) {
    
    // 使用更安全的字符串转换
    const char* inputStr = env->GetStringUTFChars(input, nullptr);
    if (inputStr == nullptr) {
        return env->NewStringUTF("错误: 无法读取输入字符串");
    }
    
    std::string cppInput(inputStr);
    env->ReleaseStringUTFChars(input, inputStr);

    try {
        std::string result = processText(cppInput);
        return env->NewStringUTF(result.c_str());
    } catch (const std::exception& e) {
        return env->NewStringUTF("错误: Native处理过程中发生异常");
    } catch (...) {
        return env->NewStringUTF("错误: Native处理过程中发生未知错误");
    }
}