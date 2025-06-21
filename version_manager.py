#!/usr/bin/env python3
"""
TestApp 版本管理脚本
自动递增版本号并更新相关文件
"""

import re
import os
import sys
from datetime import datetime

class VersionManager:
    def __init__(self):
        self.build_gradle_path = "app/build.gradle.kts"
        self.version_file_path = "version.txt"
        self.html_path = "app_intro.html"
        self.deploy_html_path = "deploy_files/index.html"
        
    def read_current_version(self):
        """读取当前版本号"""
        try:
            with open(self.build_gradle_path, 'r', encoding='utf-8') as f:
                content = f.read()
                
            # 提取版本号
            version_code_match = re.search(r'versionCode\s*=\s*(\d+)', content)
            version_name_match = re.search(r'versionName\s*=\s*"([^"]+)"', content)
            
            if version_code_match and version_name_match:
                version_code = int(version_code_match.group(1))
                version_name = version_name_match.group(1)
                return version_code, version_name
            else:
                print("❌ 无法解析版本号")
                return None, None
        except Exception as e:
            print(f"❌ 读取版本号失败: {e}")
            return None, None
    
    def update_version(self, version_code, version_name):
        """更新build.gradle.kts中的版本号"""
        try:
            with open(self.build_gradle_path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # 更新versionCode
            content = re.sub(r'versionCode\s*=\s*\d+', f'versionCode = {version_code}', content)
            # 更新versionName
            content = re.sub(r'versionName\s*=\s*"[^"]*"', f'versionName = "{version_name}"', content)
            
            with open(self.build_gradle_path, 'w', encoding='utf-8') as f:
                f.write(content)
                
            print(f"✅ 已更新 build.gradle.kts: versionCode={version_code}, versionName={version_name}")
            return True
        except Exception as e:
            print(f"❌ 更新版本号失败: {e}")
            return False
    
    def update_html_version(self, version_name):
        """更新HTML文件中的版本信息"""
        html_files = [self.html_path, self.deploy_html_path]
        
        for html_file in html_files:
            if os.path.exists(html_file):
                try:
                    with open(html_file, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    # 更新版本号
                    content = re.sub(r'当前版本：v[^<]+', f'当前版本：v{version_name}', content)
                    
                    # 更新文件大小（如果需要）
                    apk_size = self.get_apk_size()
                    if apk_size:
                        content = re.sub(r'文件大小：约[^<]+', f'文件大小：约 {apk_size}', content)
                    
                    with open(html_file, 'w', encoding='utf-8') as f:
                        f.write(content)
                        
                    print(f"✅ 已更新 {html_file} 版本信息")
                except Exception as e:
                    print(f"⚠️ 更新 {html_file} 失败: {e}")
    
    def get_apk_size(self):
        """获取APK文件大小"""
        apk_path = "TestApp-debug.apk"
        if os.path.exists(apk_path):
            size_bytes = os.path.getsize(apk_path)
            if size_bytes < 1024 * 1024:
                return f"{size_bytes / 1024:.1f}KB"
            else:
                return f"{size_bytes / (1024 * 1024):.1f}MB"
        return None
    
    def save_version_info(self, version_code, version_name):
        """保存版本信息到文件"""
        try:
            version_info = {
                'version_code': version_code,
                'version_name': version_name,
                'build_time': datetime.now().strftime('%Y-%m-%d %H:%M:%S'),
                'build_date': datetime.now().strftime('%Y-%m-%d')
            }
            
            with open(self.version_file_path, 'w', encoding='utf-8') as f:
                f.write(f"版本号: {version_name}\n")
                f.write(f"版本代码: {version_code}\n")
                f.write(f"构建时间: {version_info['build_time']}\n")
                f.write(f"构建日期: {version_info['build_date']}\n")
            
            print(f"✅ 版本信息已保存到 {self.version_file_path}")
            return True
        except Exception as e:
            print(f"❌ 保存版本信息失败: {e}")
            return False
    
    def increment_version(self, increment_type='patch'):
        """递增版本号"""
        current_code, current_name = self.read_current_version()
        
        if current_code is None or current_name is None:
            print("❌ 无法读取当前版本号")
            return False
        
        # 解析版本名
        version_parts = current_name.split('.')
        if len(version_parts) != 3:
            print("❌ 版本号格式错误，应为 x.y.z 格式")
            return False
        
        major, minor, patch = map(int, version_parts)
        new_code = current_code + 1
        
        # 根据类型递增版本
        if increment_type == 'major':
            major += 1
            minor = 0
            patch = 0
        elif increment_type == 'minor':
            minor += 1
            patch = 0
        else:  # patch
            patch += 1
        
        new_name = f"{major}.{minor}.{patch}"
        
        print(f"📈 版本更新:")
        print(f"   当前: v{current_name} (code: {current_code})")
        print(f"   新版本: v{new_name} (code: {new_code})")
        print(f"   更新类型: {increment_type}")
        
        # 更新所有文件
        success = True
        success &= self.update_version(new_code, new_name)
        self.update_html_version(new_name)
        success &= self.save_version_info(new_code, new_name)
        
        return success
    
    def show_current_version(self):
        """显示当前版本信息"""
        version_code, version_name = self.read_current_version()
        if version_code and version_name:
            print(f"📱 当前版本: v{version_name} (code: {version_code})")
            
            apk_size = self.get_apk_size()
            if apk_size:
                print(f"📦 APK大小: {apk_size}")
            
            if os.path.exists(self.version_file_path):
                print(f"📄 版本信息文件: {self.version_file_path}")
        else:
            print("❌ 无法读取版本信息")

def main():
    manager = VersionManager()
    
    if len(sys.argv) < 2:
        print("🚀 TestApp 版本管理器")
        print("=" * 50)
        print("使用方法:")
        print("  python3 version_manager.py show          # 显示当前版本")
        print("  python3 version_manager.py patch         # 递增补丁版本 (1.0.0 -> 1.0.1)")
        print("  python3 version_manager.py minor         # 递增次要版本 (1.0.0 -> 1.1.0)")
        print("  python3 version_manager.py major         # 递增主要版本 (1.0.0 -> 2.0.0)")
        print()
        manager.show_current_version()
        return
    
    command = sys.argv[1].lower()
    
    if command == 'show':
        manager.show_current_version()
    elif command in ['patch', 'minor', 'major']:
        if manager.increment_version(command):
            print("\n🎉 版本更新完成！")
            print("💡 现在可以运行 ./gradlew assembleDebug 构建新版本")
        else:
            print("\n❌ 版本更新失败")
    else:
        print(f"❌ 未知命令: {command}")
        print("使用 'python3 version_manager.py' 查看帮助")

if __name__ == "__main__":
    main() 