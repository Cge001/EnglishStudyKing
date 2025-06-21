#!/usr/bin/env python3
"""
TestApp 自动构建脚本
包含版本管理、APK构建和自动提交功能
"""

import os
import sys
import subprocess
import shutil
from datetime import datetime

def run_command(command, description=""):
    """运行命令并处理错误"""
    print(f"🔄 {description}")
    try:
        result = subprocess.run(command, shell=True, check=True, capture_output=True, text=True)
        print(f"✅ {description} 成功")
        return result.stdout
    except subprocess.CalledProcessError as e:
        print(f"❌ {description} 失败: {e}")
        print(f"错误输出: {e.stderr}")
        return None

def increment_version():
    """递增版本号"""
    print("📈 递增版本号...")
    
    # 读取当前版本
    try:
        with open("version.txt", "r") as f:
            current_version = f.read().strip()
    except FileNotFoundError:
        current_version = "1.0.0"
    
    # 解析版本号
    parts = current_version.split(".")
    if len(parts) == 3:
        major, minor, patch = parts
        new_patch = str(int(patch) + 1)
        new_version = f"{major}.{minor}.{new_patch}"
    else:
        new_version = "1.0.1"
    
    # 更新版本文件
    with open("version.txt", "w") as f:
        f.write(new_version)
    
    print(f"📋 版本号已更新: {current_version} -> {new_version}")
    return new_version

def update_build_gradle(version):
    """更新build.gradle.kts中的版本号"""
    print("📝 更新build.gradle.kts...")
    
    # 更新app/build.gradle.kts
    gradle_file = "app/build.gradle.kts"
    if os.path.exists(gradle_file):
        with open(gradle_file, "r") as f:
            content = f.read()
        
        # 更新versionCode和versionName
        import re
        content = re.sub(r'versionCode\s+\d+', f'versionCode {int(version.replace(".", ""))}', content)
        content = re.sub(r'versionName\s+"[^"]*"', f'versionName "{version}"', content)
        
        with open(gradle_file, "w") as f:
            f.write(content)
        
        print(f"✅ build.gradle.kts 已更新")

def update_html_version(version):
    """更新HTML文件中的版本号"""
    print("📝 更新HTML文件...")
    
    html_file = "app_intro.html"
    if os.path.exists(html_file):
        with open(html_file, "r", encoding="utf-8") as f:
            content = f.read()
        
        # 更新版本号
        import re
        content = re.sub(r'版本：v[\d.]+', f'版本：v{version}', content)
        content = re.sub(r'当前版本：v[\d.]+', f'当前版本：v{version}', content)
        
        with open(html_file, "w", encoding="utf-8") as f:
            f.write(content)
        
        print(f"✅ HTML文件已更新")

def build_apk():
    """构建APK"""
    print("🔨 开始构建APK...")
    
    # 清理构建
    run_command("./gradlew clean", "清理构建文件")
    
    # 构建debug APK
    result = run_command("./gradlew assembleDebug", "构建APK")
    
    if result is None:
        print("❌ APK构建失败")
        return False
    
    # 复制APK到根目录
    apk_source = "app/build/outputs/apk/debug/app-debug.apk"
    apk_dest = "TestApp-debug.apk"
    
    if os.path.exists(apk_source):
        shutil.copy2(apk_source, apk_dest)
        print(f"✅ APK已复制到: {apk_dest}")
        return True
    else:
        print("❌ 未找到生成的APK文件")
        return False

def auto_commit(version):
    """自动提交代码"""
    print("📝 自动提交代码...")
    
    try:
        # 添加所有文件
        subprocess.run(["git", "add", "."], check=True)
        
        # 生成提交信息
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        commit_msg = f"[AI] 自动构建 v{version} - {timestamp}"
        
        # 提交
        subprocess.run(["git", "commit", "-m", commit_msg], check=True)
        
        print(f"✅ 自动提交成功: {commit_msg}")
        return True
        
    except subprocess.CalledProcessError as e:
        print(f"❌ 自动提交失败: {e}")
        return False

def main():
    """主函数"""
    print("🚀 TestApp 自动构建脚本")
    print("=" * 50)
    
    # 检查是否在git仓库中
    if not os.path.exists(".git"):
        print("❌ 错误: 当前目录不是git仓库")
        sys.exit(1)
    
    # 1. 递增版本号
    new_version = increment_version()
    
    # 2. 更新build.gradle.kts
    update_build_gradle(new_version)
    
    # 3. 更新HTML文件
    update_html_version(new_version)
    
    # 4. 构建APK
    if build_apk():
        print("🎉 APK构建成功!")
        
        # 5. 自动提交
        auto_commit(new_version)
        
        print("\n📱 构建完成!")
        print(f"📦 APK文件: TestApp-debug.apk")
        print(f"📋 版本号: v{new_version}")
    else:
        print("❌ 构建失败")

if __name__ == "__main__":
    main() 