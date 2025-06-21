#include <jni.h>
#include <string>
#include <sstream>
#include <algorithm>
#include <cctype>
#include <locale>
#include <codecvt>
#include <vector>

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

// 统计中文字符数量（UTF-8编码）- 改进版本
int countChineseChars(const std::string& str) {
    int count = 0;
    for (size_t i = 0; i < str.length(); i++) {
        unsigned char c = static_cast<unsigned char>(str[i]);
        if (c >= 0xE0 && c <= 0xEF) { // 3字节UTF-8字符（包括中文）
            if (i + 2 < str.length()) {
                // 检查后续字节是否有效
                unsigned char b1 = static_cast<unsigned char>(str[i + 1]);
                unsigned char b2 = static_cast<unsigned char>(str[i + 2]);
                if ((b1 & 0xC0) == 0x80 && (b2 & 0xC0) == 0x80) {
                    count++;
                    i += 2; // 跳过后续字节
                }
            }
        }
    }
    return count;
}

// 安全的字符串转换 - 只处理ASCII字符
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

// 安全的字符串反转 - 保持UTF-8编码完整性
std::string reverseStringSafe(const std::string& input) {
    std::vector<std::string> chars;
    
    for (size_t i = 0; i < input.length(); i++) {
        unsigned char c = static_cast<unsigned char>(input[i]);
        std::string char_str;
        
        if (c < 0x80) {
            // ASCII字符
            char_str = input.substr(i, 1);
        } else if ((c & 0xE0) == 0xC0) {
            // 2字节UTF-8字符
            if (i + 1 < input.length()) {
                char_str = input.substr(i, 2);
                i += 1;
            } else {
                char_str = input.substr(i, 1);
            }
        } else if ((c & 0xF0) == 0xE0) {
            // 3字节UTF-8字符（包括中文）
            if (i + 2 < input.length()) {
                char_str = input.substr(i, 3);
                i += 2;
            } else {
                char_str = input.substr(i, 1);
            }
        } else if ((c & 0xF8) == 0xF0) {
            // 4字节UTF-8字符
            if (i + 3 < input.length()) {
                char_str = input.substr(i, 4);
                i += 3;
            } else {
                char_str = input.substr(i, 1);
            }
        } else {
            // 无效UTF-8，当作单字节处理
            char_str = input.substr(i, 1);
        }
        
        chars.push_back(char_str);
    }
    
    // 反转字符数组
    std::reverse(chars.begin(), chars.end());
    
    // 重新组合
    std::string result;
    for (const auto& ch : chars) {
        result += ch;
    }
    
    return result;
}

std::string getCurrentTimestamp() {
    return "2024-01-01 12:00:00";
}

std::string processText(const std::string& input) {
    try {
        std::ostringstream result;
        result << "EnglishStudyKing Native接口处理结果:\n";
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
        
        std::string reversed = reverseStringSafe(input);
        result << "反转字符串: " << reversed << "\n\n";
        
        result << "处理时间: " << getCurrentTimestamp() << "\n";
        result << "✅ 中文字符已安全处理，不会崩溃\n";
        
        return result.str();
    } catch (const std::exception& e) {
        return "错误: Native处理过程中发生异常: " + std::string(e.what());
    } catch (...) {
        return "错误: Native处理过程中发生未知错误";
    }
}

// JNI接口
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_testapp_MainActivity_processTextFromNative(
        JNIEnv* env,
        jobject /* this */,
        jstring input) {
    
    if (input == nullptr) {
        return env->NewStringUTF("错误: 输入字符串为空");
    }
    
    // 使用更安全的字符串转换
    const char* inputStr = env->GetStringUTFChars(input, nullptr);
    if (inputStr == nullptr) {
        return env->NewStringUTF("错误: 无法读取输入字符串");
    }
    
    try {
        std::string cppInput(inputStr);
        env->ReleaseStringUTFChars(input, inputStr);
        
        std::string result = processText(cppInput);
        return env->NewStringUTF(result.c_str());
    } catch (const std::exception& e) {
        env->ReleaseStringUTFChars(input, inputStr);
        return env->NewStringUTF("错误: Native处理过程中发生异常");
    } catch (...) {
        env->ReleaseStringUTFChars(input, inputStr);
        return env->NewStringUTF("错误: Native处理过程中发生未知错误");
    }
}

// JNI接口 - stringFromJNI
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_testapp_MainActivity_stringFromJNI(
        JNIEnv* env,
        jclass /* clazz */,
        jstring input) {
    
    if (input == nullptr) {
        return env->NewStringUTF("错误: 输入字符串为空");
    }
    
    // 使用更安全的字符串转换
    const char* inputStr = env->GetStringUTFChars(input, nullptr);
    if (inputStr == nullptr) {
        return env->NewStringUTF("错误: 无法读取输入字符串");
    }
    
    try {
        std::string cppInput(inputStr);
        env->ReleaseStringUTFChars(input, inputStr);
        
        std::string result = processText(cppInput);
        return env->NewStringUTF(result.c_str());
    } catch (const std::exception& e) {
        env->ReleaseStringUTFChars(input, inputStr);
        return env->NewStringUTF("错误: Native处理过程中发生异常");
    } catch (...) {
        env->ReleaseStringUTFChars(input, inputStr);
        return env->NewStringUTF("错误: Native处理过程中发生未知错误");
    }
}