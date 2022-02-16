# iCoffee前后端分离管理系统
iCoffeeAdmin基于Spring Boot 2.4.2、Spring Security、Jwt、MyBatis Plus 3.4.2、Log4j2、Lombok、Vue、Element-UI开源的框架搭建起来的前后端分离管理系统，通过双Token（AccessToken、RefreshToken）进行登录，权限系统基于RBAC原理，用户成功登录后，通过后端获取“路由”和“授权”信息进行前端UI初始化。

- 后端项目：https://github.com/ethanwong/icoffee-admin
- 前端项目：https://github.com/ethanwong/icoffee-vue

## 主要特性
- 基于当前最新的开源技术，社区资源丰富；
- 简单、轻量级的代码封装，功能上无过渡设计；
- 完全开放源代码，允许自由修改和扩展；
- 模块化开发，无侵入性低耦合的开发模式；
- 实现用户鉴权和资源授权两个方面的安全控制；
- 基于注解自动生成WEB资源授权，实现零配置资源鉴权；

## 主要功能
- 首页：用户登录后展示的主页面；
- 用户登录、随机验证码、记住我、超时退出；
- 用户管理：用户CRUDL操作管理、包括用户锁定、角色分配；
- 角色管理：角色CRUDL操作管理，包括菜单和资源授权分配；
- 菜单管理：菜单CRUDL操作管理，包括多级菜单添加、图标分配，路由配置等功能；
- 授权管理：查看系统自动生成的资源信息，可以根据菜单分类进行检索查看；
- 系统配置：系统的基本参数配置；
- 个人中心：个人信息查看和修改；
- 开发指南：静态页面，展示系统开发指南和开发资源的链接；

> CRUDL:增加、检索、更新、删除、列表

## 项目结构
- icoffee-main:系统核心模块，底层框架在这里进行配置，系统从这里启动；
- icoffee-common:通用模块，存放基础封装类、工具等；
- icoffee-system:系统管理模块，业务模块；
- icoffee-generator:代码生成模块；
- icoffee-business:业务功能模块，新功能在此添加开发，也可以自行创建其他子模块；

## 部署打包
> cd icoffee-main
> 
> mvn clean package

## 启动工程
> java -jar icoffee-main-1.0.0.jar
