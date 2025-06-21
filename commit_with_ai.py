#!/usr/bin/env python3
import subprocess
import sys
from datetime import datetime

def commit_with_ai(message=None):
    try:
        subprocess.run(["git", "add", "."], check=True)
        if not message:
            timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            message = f"[AI] 自动更新 - {timestamp}"
        else:
            message = f"[AI] {message}"
        subprocess.run(["git", "commit", "-m", message], check=True)
        print(f"✅ 提交成功: {message}")
        return True
    except subprocess.CalledProcessError as e:
        print(f"❌ 提交失败: {e}")
        return False

if __name__ == "__main__":
    custom_message = " ".join(sys.argv[1:]) if len(sys.argv) > 1 else None
    commit_with_ai(custom_message)
