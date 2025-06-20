# TestApp

一个原生 Android 应用，使用 Kotlin 和 C++ 实现。

## 功能

- 提供文本输入界面
- 调用 Native C++ 接口处理文本
- 显示处理结果，包括：
  - 字符统计（总字符数、字母数、数字数、空格数）
  - 文本转换（大写、小写、反转）
  - 处理时间戳

## 技术栈

- **语言**: Kotlin (Android), C++ (Native)
- **构建工具**: Gradle
- **Native 构建**: CMake
- **UI 框架**: AndroidX + Material Design

## 构建和运行

1. 确保已安装 Android Studio 和 Android SDK
2. 打开项目
3. 同步 Gradle 文件
4. 连接 Android 设备或启动模拟器
5. 点击运行按钮

## 项目结构

```
TestApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/testapp/
│   │   │   └── MainActivity.kt          # 主活动
│   │   ├── cpp/
│   │   │   ├── native-lib.cpp          # C++ 实现
│   │   │   └── CMakeLists.txt          # CMake 配置
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml   # 主布局
│   │   │   ├── values/
│   │   │   │   ├── strings.xml         # 字符串资源
│   │   │   │   ├── colors.xml          # 颜色资源
│   │   │   │   └── themes.xml          # 主题样式
│   │   │   └── drawable/
│   │   │       └── result_background.xml # 背景样式
│   │   └── AndroidManifest.xml         # 应用清单
│   └── build.gradle.kts                # 应用构建配置
├── build.gradle.kts                    # 项目构建配置
├── settings.gradle.kts                 # 项目设置
└── gradle.properties                   # Gradle 属性
```

## Native 接口

应用通过 JNI 调用 C++ 代码：

```kotlin
// Kotlin 声明
external fun processTextFromNative(input: String): String
```

```cpp
// C++ 实现
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_testapp_MainActivity_processTextFromNative(
    JNIEnv* env, jobject /* this */, jstring input)
``` 