#include <jni.h>
#include <string>
#include <sstream>
#include <algorithm>
#include <cctype>

// 先声明/实现所有辅助函数
int countLetters(const std::string& str) {
    return std::count_if(str.begin(), str.end(), ::isalpha);
}

int countDigits(const std::string& str) {
    return std::count_if(str.begin(), str.end(), ::isdigit);
}

int countSpaces(const std::string& str) {
    return std::count(str.begin(), str.end(), ' ');
}

std::string getCurrentTimestamp() {
    return "2024-01-01 12:00:00";
}

std::string processText(const std::string& input) {
    std::ostringstream result;
    result << "Native接口处理结果:\n";
    result << "原始输入: " << input << "\n\n";
    result << "字符统计:\n";
    result << "- 总字符数: " << input.length() << "\n";
    result << "- 字母数: " << countLetters(input) << "\n";
    result << "- 数字数: " << countDigits(input) << "\n";
    result << "- 空格数: " << countSpaces(input) << "\n\n";
    std::string upperInput = input;
    std::transform(upperInput.begin(), upperInput.end(), upperInput.begin(), ::toupper);
    result << "转换为大写: " << upperInput << "\n\n";
    std::string lowerInput = input;
    std::transform(lowerInput.begin(), lowerInput.end(), lowerInput.begin(), ::tolower);
    result << "转换为小写: " << lowerInput << "\n\n";
    std::string reversed = input;
    std::reverse(reversed.begin(), reversed.end());
    result << "反转字符串: " << reversed << "\n\n";
    result << "处理时间: " << getCurrentTimestamp() << "\n";
    return result.str();
}

// JNI接口
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_testapp_MainActivity_processTextFromNative(
        JNIEnv* env,
        jobject /* this */,
        jstring input) {
    const char* inputStr = env->GetStringUTFChars(input, 0);
    std::string cppInput(inputStr);
    env->ReleaseStringUTFChars(input, inputStr);

    std::string result = processText(cppInput);

    return env->NewStringUTF(result.c_str());
}