# ifblog个人博客----vue+springboot个人博客系统

## 介绍
项目demo：https://www.ifyyf.com
ifblog个人博客----vue+springboot个人博客系统
前端技术栈：HTML+CSS+JavaScript+Vue+ElementUi+Axios
后端技术栈：Java+SpringBoot+Mybatis+Mysql

## 软件架构
饿了么ui是针对pc的ui框架，我前端的技术也学得不好，所以在开发的时候也是尽量适配了手机端
有一套完整的后台管理系统(因为本人是后端开发)
下面会放项目demo截图

## 安装教程

1.  保证vue框架，elementui框架，springboot框架的开发环境
2.  可以直接下载jar包然后进行jar启动（springboot内置了tomcat）
3.  需要修改源代码时可以下载源代码包（分别包含前端代码和后端代码）

## 使用说明

1.  博客采用ssl证书达到https访问，需要自己去获取对应的ssl证书并修改springboot的application.properties配置
2.  修改application.properties的DataSource连接等配置
3.  jar包启动时默认C:/Program Files/apps/project/uploads路径保存文件(图片等文件)
4.  点击头像登录后台（需要数据库配置admin表）

## 数据库建库建表
1.  默认使用blog库
2.  建表语句如下
CREATE TABLE `admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `identity1` varchar(255) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `auto` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `body` varchar(20000) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `about` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `about` varchar(20000) DEFAULT NULL COMMENT '关于我',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `comments` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `identity1` varchar(255) DEFAULT NULL COMMENT '评论者',
  `email` varchar(255) DEFAULT NULL COMMENT '邮件地址',
  `body` varchar(20000) DEFAULT NULL COMMENT '评论内容',
  `blog_id` int(10) DEFAULT NULL COMMENT '评论的博客id',
  `time1` datetime DEFAULT NULL COMMENT '评论时间',
  `if_show` int(255) NOT NULL COMMENT '是否展示',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `identity1` varchar(255) DEFAULT NULL COMMENT '留言者',
  `email` varchar(255) DEFAULT NULL COMMENT '邮件地址',
  `body` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `time1` datetime DEFAULT NULL COMMENT '评论时间',
  `if_show` int(255) DEFAULT NULL COMMENT '是否展示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

CREATE TABLE `statement` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `statement` varchar(20000) DEFAULT NULL COMMENT '网站声明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `blogs` (
  `blog_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `body` longtext COMMENT '内容',
  `identity1` varchar(20) DEFAULT NULL COMMENT '作者',
  `time1` datetime DEFAULT NULL COMMENT '时间',
  `category` varchar(255) DEFAULT NULL COMMENT '博客类别',
  `top` int(255) DEFAULT NULL COMMENT '是否置顶',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

## 项目截图
### 项目代码结构一览
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000100_d44fd72d_7545785.png "屏幕截图.png")
### 主页
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000128_f7de0294_7545785.png "主页.png")
### 备案说明
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000138_ee6eaee2_7545785.png "备案说明.png")
### 博客详细
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000149_c06f8d99_7545785.png "博客详细.png")
### 评论
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000304_f4395fc7_7545785.png "评论.png")
### 留言板
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000238_008d183a_7545785.png "留言板.png")
### 博客管理
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000320_d196c1b4_7545785.png "博客管理.png")
### 留言管理
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000335_07644340_7545785.png "留言管理.png")
### 评论管理
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000400_f3f184cb_7545785.png "评论管理.png")
### 写博客
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000430_be421592_7545785.png "写博客.png")
### 修改关于我的
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000438_5603636d_7545785.png "修改关于我的.png")
### 修改网站声明
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/000456_49854b81_7545785.png "修改网站说明.png")



