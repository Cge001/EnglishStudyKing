# TestApp 部署文件

这个文件夹包含了可以部署到公网的文件：

## 文件说明：
- `index.html` - 主页面（重命名自 app_intro.html）
- `TestApp-debug.apk` - Android应用安装包
- `README.md` - 本说明文件

## 部署选项：

### 1. GitHub Pages（推荐）
1. 创建GitHub仓库
2. 上传这些文件
3. 在仓库设置中启用GitHub Pages
4. 获得地址：`https://your-username.github.io/repo-name/`

### 2. Netlify
1. 访问 https://netlify.com
2. 拖拽这个文件夹上传
3. 获得地址：`https://random-name.netlify.app`

### 3. Vercel
1. 访问 https://vercel.com
2. 上传这个文件夹
3. 获得地址：`https://project-name.vercel.app`

## 部署后：
1. 使用新的公网地址重新生成二维码
2. 全世界的人都能通过二维码访问你的App介绍页面
3. 可以直接下载APK文件

## 注意事项：
- 确保APK文件大小在平台限制范围内
- 如果APK太大，可以考虑使用云存储服务（如阿里云OSS、腾讯云COS等）
- 建议使用HTTPS链接以确保安全性 