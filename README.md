## 项目名称
个人网站支付宝面对面支付接入方案

[测试地址](http://test.ggball.top:9002/view/index.html)

## 相关介绍
[相关介绍](https://www.ggball.top/pages/6bf2fe/#%E9%A1%B9%E7%9B%AE%E8%83%8C%E6%99%AF)

## 项目截图
![img.png](data/image/img.png)

## 项目简介：  
本项目是一套面向个人开发者和小型网站的支付宝支付接入解决方案。它基于支付宝“面对面支付”能力，无需企业资质、无需复杂审核，个人即可快速集成支付宝收款功能到自己的网站。项目采用 Spring Boot + SQLite 技术栈，支持 Docker 一键部署，配置简单，安全可靠，适合博客、工具站、内容付费等多种场景。

### 核心特点：  
- **零门槛**：无需企业认证，个人支付宝账号即可开通和使用。
- **简单易用**：只需配置支付宝公私钥和 AppId，即可完成支付集成。
- **本地化存储**：采用 SQLite 数据库，免运维，适合轻量级应用。
- **环境无关**：支持 Docker 部署，数据库文件可挂载，数据持久化。
- **安全合规**：支付参数通过环境变量注入，敏感信息不落盘。
- **可扩展性强**：基于 Spring Boot，易于二次开发和功能扩展。

### 应用场景：  
- 个人博客/内容付费
- 工具类网站/小程序
- 个人 SaaS 服务
- 线上打赏/小额收款

### 技术栈：  
- Spring Boot
- SQLite
- Docker & Docker Compose
- 支付宝开放平台（面对面支付）

### 部署方式：  
1. 克隆项目，配置支付宝相关参数（可通过 .env 文件或 docker-compose 环境变量注入）
注意.env文件需要和 docker-compose.yml 在同一目录下

env环境变量

| 参数 | 说明      | 示例                      |
|--|---------|-------------------------|
| ALIPAY_APP_ID | 支付宝应用ID | 2021000116666666        |
| ALI_PAY_PRIVATE_KEY | 支付宝私钥   | -----BEGIN PRIVATE KEY... |
| ALI_PAY_PUBLIC_KEY | 支付宝公钥   | -----BEGIN PUBLIC KEY... |
| ALIPAY_NOTIFY_URL | 支付宝异步通知地址 | http://yourdomain.com/alipay/notify |


2. 一键构建并运行 Docker 容器
```bash
docker-compose up -d 
```
3. 访问网站，体验支付宝扫码支付

访问地址：http://localhost:9002/view/index.html

### 适合人群：  
- 个人开发者
- 小型创业团队
- 需要快速上线收款功能的站长

---

