# Nunumao-Boot
Nunumao-Boot 是一个基于 SpringBoot的免费开源的，为了简化企业应用开发而设计的敏捷开发框架。设计的初衷是将软件应用开发中的常见功能、通用功能抽象为组件或依赖库，提高开发中的复用性，降低软件应用的开发复杂度，缩短软件应用的开发周期。

![图片](https://raw.githubusercontent.com/nunumao/nunumao-boot/main/assets/1.png)

## 相关特性
- 前后端分离
- OAuth2鉴权
- RBAC权限管理
- 部门权限管理
- 部门权限继承
- 数据权限
- 静态资源文件管理
- 代码生成
- 常用业务组件
- 接口文档生成
- 接口版本控制
- WebSocket推送

## 演示体验
演示站地址：https://nunumao.365d1.com/

体验账号: nunu

体验密码: 123456

## 快速开始

新建数据库 `nunumao`，编码选择`utf-8`，并导入数据库初始化数据。
> database/nunumao.sql

修改`app`模块中的`application.yml`数据库连接配置。
```yaml
...
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/nunumao?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: 
...
```
在IDEA中或命令中运行项目即可。

进入`manager`目录，安装前端项目依赖(请提前安装yarn)。
```shell
yarn
```
运行前端项目。
```shell
yarn dev
```
浏览器访问 `http://localhost:3000`。

## 文档教程
详细文档与教程 [文档链接](https://thoughts.aliyun.com/sharespace/61775109996708001a43703e/docs/617755c004c388001a3edc69)
