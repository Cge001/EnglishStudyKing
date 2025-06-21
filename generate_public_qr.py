#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
GitHub Pages 公网二维码生成器
为 https://cge001.github.io/EnglishStudyKing/app_intro.html 生成二维码
"""

import qrcode
import os
from datetime import datetime

def generate_public_qr():
    """生成指向GitHub Pages的公网二维码"""
    
    # GitHub Pages 公网地址
    public_url = "https://cge001.github.io/EnglishStudyKing/app_intro.html"
    
    print("🚀 GitHub Pages 公网二维码生成器")
    print("=" * 50)
    print(f"📋 公网访问地址: {public_url}")
    
    # 创建二维码
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    
    qr.add_data(public_url)
    qr.make(fit=True)
    
    # 创建图片
    img = qr.make_image(fill_color="black", back_color="white")
    
    # 保存二维码
    qr_filename = "english_study_king_public_qr.png"
    img.save(qr_filename)
    
    print(f"📱 公网二维码已生成: {qr_filename}")
    print(f"📅 生成时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print()
    print("📱 使用说明:")
    print("1. 用微信/支付宝/浏览器扫描生成的二维码")
    print("2. 直接访问 GitHub Pages 网页")
    print("3. 查看 TestApp 的介绍页面")
    print("4. 可以通过下载链接获取应用")
    print("5. 任何人都可以访问，无需在同一网络")
    print()
    print("✅ 二维码生成完成！")
    print(f"📁 文件位置: {os.path.abspath(qr_filename)}")
    
    return qr_filename

if __name__ == "__main__":
    generate_public_qr() 