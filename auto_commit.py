#!/usr/bin/env python3
"""
自动提交脚本 - 检测文件变化并自动提交
"""

import os
import subprocess
import time
import hashlib
from datetime import datetime

class AutoCommit:
    def __init__(self, project_dir="."):
        self.project_dir = os.path.abspath(project_dir)
        self.file_hashes = {}
        self.watch_files = [
            "app/src/main/java/com/example/testapp/MainActivity.kt",
            "app/src/main/res/layout/activity_main.xml",
            "app/src/main/cpp/native-lib.cpp",
            "app/build.gradle.kts",
            "build.gradle.kts"
        ]
        
    def get_file_hash(self, filepath):
        """获取文件的MD5哈希值"""
        try:
            with open(filepath, 'rb') as f:
                return hashlib.md5(f.read()).hexdigest()
        except FileNotFoundError:
            return None
    
    def check_changes(self):
        """检查文件是否有变化"""
        changes = []
        for filepath in self.watch_files:
            full_path = os.path.join(self.project_dir, filepath)
            current_hash = self.get_file_hash(full_path)
            
            if current_hash is None:
                continue
                
            if filepath not in self.file_hashes:
                self.file_hashes[filepath] = current_hash
                changes.append(filepath)
            elif self.file_hashes[filepath] != current_hash:
                self.file_hashes[filepath] = current_hash
                changes.append(filepath)
        
        return changes
    
    def get_commit_message(self, changed_files):
        """生成提交信息"""
        if not changed_files:
            return None
            
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        
        if len(changed_files) == 1:
            file_desc = changed_files[0]
        else:
            file_desc = f"{len(changed_files)}个文件"
        
        return f"[AI] 自动更新 {file_desc} - {timestamp}"
    
    def commit_changes(self, changed_files):
        """提交变化"""
        if not changed_files:
            return False
            
        try:
            # 添加变化的文件
            for filepath in changed_files:
                full_path = os.path.join(self.project_dir, filepath)
                if os.path.exists(full_path):
                    subprocess.run(["git", "add", filepath], 
                                 cwd=self.project_dir, check=True)
            
            # 生成提交信息
            commit_msg = self.get_commit_message(changed_files)
            
            # 提交
            subprocess.run(["git", "commit", "-m", commit_msg], 
                         cwd=self.project_dir, check=True)
            
            print(f"✅ 自动提交成功: {commit_msg}")
            print(f"📁 变化的文件: {', '.join(changed_files)}")
            return True
            
        except subprocess.CalledProcessError as e:
            print(f"❌ 提交失败: {e}")
            return False
    
    def run(self, interval=5):
        """运行自动提交监控"""
        print(f"🚀 自动提交监控已启动")
        print(f"📁 监控目录: {self.project_dir}")
        print(f"⏰ 检查间隔: {interval}秒")
        print(f"📋 监控文件: {', '.join(self.watch_files)}")
        print("按 Ctrl+C 停止监控\n")
        
        try:
            while True:
                changed_files = self.check_changes()
                
                if changed_files:
                    print(f"🔄 检测到文件变化: {', '.join(changed_files)}")
                    self.commit_changes(changed_files)
                
                time.sleep(interval)
                
        except KeyboardInterrupt:
            print("\n👋 自动提交监控已停止")

def main():
    import sys
    
    # 检查是否在git仓库中
    try:
        subprocess.run(["git", "status"], check=True, capture_output=True)
    except subprocess.CalledProcessError:
        print("❌ 错误: 当前目录不是git仓库")
        sys.exit(1)
    
    # 检查git配置
    try:
        result = subprocess.run(["git", "config", "user.name"], 
                              capture_output=True, text=True, check=True)
        if not result.stdout.strip():
            print("❌ 错误: 请先配置git用户名")
            print("运行: git config --global user.name '你的名字'")
            sys.exit(1)
    except subprocess.CalledProcessError:
        print("❌ 错误: 请先配置git用户名")
        print("运行: git config --global user.name '你的名字'")
        sys.exit(1)
    
    # 启动自动提交
    auto_commit = AutoCommit()
    auto_commit.run()

if __name__ == "__main__":
    main() 