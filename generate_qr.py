#!/usr/bin/env python3
"""
生成TestApp介绍页面的二维码
"""

import qrcode
import http.server
import socketserver
import threading
import webbrowser
import os
import socket
from urllib.parse import urlparse

def get_local_ip():
    """获取本机IP地址"""
    try:
        # 创建一个UDP套接字
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        # 连接一个外部地址（不需要真实连接）
        s.connect(("8.8.8.8", 80))
        ip = s.getsockname()[0]
        s.close()
        return ip
    except Exception:
        return "127.0.0.1"

def start_server(port=8000):
    """启动HTTP服务器"""
    handler = http.server.SimpleHTTPRequestHandler
    
    with socketserver.TCPServer(("", port), handler) as httpd:
        print(f"🌐 服务器已启动在端口 {port}")
        print(f"📱 本地访问: http://localhost:{port}/app_intro.html")
        print(f"📱 局域网访问: http://{get_local_ip()}:{port}/app_intro.html")
        print("按 Ctrl+C 停止服务器")
        httpd.serve_forever()

def generate_qr_code(url, filename="testapp_qr.png"):
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
    print(f"📱 二维码已生成: {filename}")
    return filename

def main():
    print("🚀 TestApp 二维码生成器")
    print("=" * 50)
    
    # 获取本机IP
    local_ip = get_local_ip()
    port = 8000
    
    # 生成访问URL
    local_url = f"http://localhost:{port}/app_intro.html"
    network_url = f"http://{local_ip}:{port}/app_intro.html"
    
    print(f"📋 本地访问地址: {local_url}")
    print(f"📋 局域网访问地址: {network_url}")
    print()
    
    # 生成二维码（使用局域网地址，这样手机可以访问）
    qr_filename = generate_qr_code(network_url)
    
    print()
    print("📱 使用说明:")
    print("1. 用微信扫描生成的二维码")
    print("2. 在手机浏览器中打开网页")
    print("3. 查看TestApp的介绍页面")
    print("4. 可以通过下载链接获取应用")
    print()
    
    # 询问是否启动服务器
    try:
        choice = input("是否启动本地服务器？(y/n): ").lower().strip()
        if choice in ['y', 'yes', '是']:
            print("🚀 启动服务器...")
            # 在新线程中启动服务器
            server_thread = threading.Thread(target=start_server, args=(port,), daemon=True)
            server_thread.start()
            
            # 等待一秒让服务器启动
            import time
            time.sleep(1)
            
            # 自动打开浏览器
            try:
                webbrowser.open(local_url)
                print("🌐 已在浏览器中打开页面")
            except:
                print("无法自动打开浏览器，请手动访问上述地址")
            
            # 保持主线程运行
            try:
                while True:
                    time.sleep(1)
            except KeyboardInterrupt:
                print("\n👋 服务器已停止")
        else:
            print("💡 提示：你可以手动启动服务器:")
            print(f"   python -m http.server {port}")
            print("   然后在浏览器中访问上述地址")
    except KeyboardInterrupt:
        print("\n👋 程序已退出")

if __name__ == "__main__":
    main() 