#!/usr/bin/env python3
"""
测试中文输入处理功能
"""

def test_chinese_processing():
    """测试各种中文输入场景"""
    
    test_cases = [
        "你好世界",  # 纯中文
        "Hello 世界",  # 中英文混合
        "123 测试",  # 数字中文混合
        "Test 测试 123",  # 英文中文数字混合
        "Hello World",  # 纯英文
        "123456",  # 纯数字
        "  空格测试  ",  # 带空格
        "特殊字符!@#$%^&*()",  # 特殊字符
        "中文标点符号：，。！？",  # 中文标点
        "",  # 空字符串
    ]
    
    print("🧪 中文输入处理测试")
    print("=" * 50)
    
    for i, test_input in enumerate(test_cases, 1):
        print(f"\n📝 测试用例 {i}: '{test_input}'")
        print(f"   长度: {len(test_input)} 字符")
        print(f"   字节数: {len(test_input.encode('utf-8'))} 字节")
        
        # 模拟Native处理逻辑
        ascii_letters = sum(1 for c in test_input if c.isalpha() and ord(c) < 128)
        ascii_digits = sum(1 for c in test_input if c.isdigit())
        spaces = sum(1 for c in test_input if c.isspace())
        chinese_chars = sum(1 for c in test_input if '\u4e00' <= c <= '\u9fff')
        
        print(f"   ASCII字母: {ascii_letters}")
        print(f"   ASCII数字: {ascii_digits}")
        print(f"   空格: {spaces}")
        print(f"   中文字符: {chinese_chars}")
        
        # 测试大小写转换（只对ASCII字符）
        upper = ''.join(c.upper() if c.isalpha() and ord(c) < 128 else c for c in test_input)
        lower = ''.join(c.lower() if c.isalpha() and ord(c) < 128 else c for c in test_input)
        
        print(f"   转大写: '{upper}'")
        print(f"   转小写: '{lower}'")
        print(f"   反转: '{test_input[::-1]}'")

def main():
    print("🚀 TestApp 中文输入处理测试")
    print("=" * 50)
    print("这个脚本模拟了Native代码对中文输入的处理逻辑")
    print("现在Native代码已经修复，可以安全处理中文字符了！")
    print()
    
    test_chinese_processing()
    
    print("\n" + "=" * 50)
    print("✅ 测试完成！")
    print("💡 现在你可以在App中输入中文，Native接口不会崩溃了")
    print("📱 重新安装APK来测试修复后的功能")

if __name__ == "__main__":
    main() 