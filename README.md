# 高仿 知乎 Android客户端

学习 Android 开发已有一段时日，出于学以致用的目的开始开发此项目，希望能通过一个相对比较完整以及具备一定开发难度的项目进一步熟悉 Android 开发。
目前已经完成主要的几个核心功能。

[下载 Demo](http://cloud.mail.163.com/dfs/service/zhihu.apk?op=downloadFile&uid=zhanfusu@163.com&file=eyJzIjoiZnM6Y2xvdWRzdG9yYWdlLm1haWwuMTYzOjQ4NDI2OTA1NzA0MyIsInNmIjoiNDg1NTM3NjkzOTI5OjQ4NDI2OTA1NzA0MyIsImkiOjcyNjY2NzIzNDc5ODg0ODUsImNzIjp7InQiOjEsInYiOiJiYjJiMzQ1ZmRlMWI0ZTFkM2E5NmJkNTViZjIzMTI2YyJ9LCJzeiI6MzIyMjIzNCwiY3QiOjE0MzE5NTIxNTI4MTV9&callback=http://wp.163.com:80/filehub/html/downloadCallback.jsp)

![login_gif](http://7xilr7.com1.z0.glb.clouddn.com/zhihulogin.gif)


##项目简介

+ 基于 MVP 模式开发

	项目开始前，刚好接触到一篇讲述以一种新思路在Android中实现 MVP 模式的文章，通过它提供的 demo 稍微熟悉之后觉得比较可行，于是便利用这个项目进一步熟悉这个比较新奇的 MVP 模式。
	文章地址：
		https://github.com/bboyfeiyu/android-tech-frontier/tree/master/androidweekly/%E4%B8%80%E7%A7%8D%E5%9C%A8android%E4%B8%AD%E5%AE%9E%E7%8E%B0MVP%E6%A8%A1%E5%BC%8F%E7%9A%84%E6%96%B0%E6%80%9D%E8%B7%AF

+ 通过 HTTP 模拟登录 知乎

	通过 HTTP 模拟登录 的重点在于 如何持有 cookie、管理cookie。考虑到 **DefaultHttpClient** 能够自动管理 cookie ，于是定制了基于 **DefaultHttpClient** 的 Volley 用来实现项目中的 HTTP 通信，包括 模拟登录 知乎。

+ 通过 Jsoup 解析 html 获取数据

	[知乎] 没有开放 API ，所以要获取到 知乎 的数据只能通过解析 网页版知乎 的html来获得。Jsoup 很好的完成这一部分需求。

+ 最大程度还原 官方版客户端 的UI设计

	知乎官方版客户端 的 UI 还原起来难度并不会太大，但是细节处颇多，在项目开发过程中只能尽可能地去实现。目前已经实现了 启动页视差滚动效果、答案详情页滚动动画等。



##项目进展

项目开始至今半个月，目前还处于持续开发过程中。部分已经实现的功能罗列如下：

+ **启动页视差滚动效果；**
+ **用户登录；**
+ **首页 Feed 流信息展示，支持顶部下拉刷新、底部上拉加载，同时支持 [新版首页]**
+ **答案展示，并实现了滚动过程中顶部和底部的动画效果**
+ **支持专栏文章阅读**
+ **支持查看问题详情，并列出答案列表**

