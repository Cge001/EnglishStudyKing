#!/usr/bin/env python3
"""
生成TestApp介绍页面的公网二维码
"""

import qrcode
import sys
import os

def generate_qr_code(url, filename="testapp_public_qr.png"):
    """生成二维码"""
    # 创建二维码实例
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    
    # 添加数据
    qr.add_data(url)
    qr.make(fit=True)
    
    # 创建图像
    img = qr.make_image(fill_color="black", back_color="white")
    
    # 保存图像
    img.save(filename)
    print(f"📱 公网二维码已生成: {filename}")
    return filename

def main():
    print("🚀 TestApp 公网二维码生成器")
    print("=" * 50)
    
    # 检查是否提供了URL参数
    if len(sys.argv) > 1:
        public_url = sys.argv[1]
        print(f"📋 使用提供的公网地址: {public_url}")
    else:
        print("💡 使用方法:")
        print("   python3 generate_public_qr.py <公网地址>")
        print()
        print("📋 示例:")
        print("   python3 generate_public_qr.py https://your-username.github.io/testapp-intro/")
        print("   python3 generate_public_qr.py https://amazing-testapp-123456.netlify.app")
        print("   python3 generate_public_qr.py https://testapp-intro.vercel.app")
        print()
        print("🔗 部署选项:")
        print("1. GitHub Pages: https://pages.github.com/")
        print("2. Netlify: https://netlify.com")
        print("3. Vercel: https://vercel.com")
        return
    
    # 生成二维码
    qr_filename = generate_qr_code(public_url)
    
    print()
    print("🎉 完成！")
    print(f"📱 二维码文件: {qr_filename}")
    print("📱 现在全世界的人都可以扫描这个二维码访问你的App介绍页面了！")
    print()
    print("💡 提示:")
    print("- 可以将二维码分享给任何朋友")
    print("- 不需要在同一WiFi网络")
    print("- 支持微信、支付宝等所有扫码应用")

if __name__ == "__main__":
    main() 