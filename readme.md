# Nodejs+MongoDB搭建安卓应用服务器
服务器环境：Linux18.04

 - 安装Nodejs

   ```
   apt install nodejs
   ```

 - 安装MongoDB
    在这里我们需要从官网下载一个安装包 [官网](https://www.mongodb.com/download-center#community)
     我选择的是tgz后缀的压缩包，

     - 解压

      ```
      tar -zxvf xx.tgz                                   # 解压
      
      mv  xx/ /usr/local/mongodb 		#拷贝到指定目录
      ```

     - 添加路径

       ```
       export PATH=/usr/local/mongodb/bin:$PATH
       ```

     - 创建数据库目录
        /data/db是MongoDB的默认启动数据库路径

        ```
        mkdir -p /data/db
        ```

     - 运行MongoDB服务
        通过以下命令运行服务

        ```
        mongod
        ```

     - 后台管理Shell运行
         需要进入到MongoDB的安装目录然后运行

         ```
         cd /usr/local/mongodb/bin
         mongo
         ```

     倘若我们的服务器是只有Shell界面的，按照以上办法运行了后台服务以后就不能启动MongoDB的Shell服务了，在这里我想到了操作系统的多进程的知识，初步的想法是将MongoDB后台服务运行在后台进程，然后再运行MongoDB的Shell，具体实现如下：

 - 开启MongoDB后台服务

   ```
   mongod --fork --logpath <1> --dbpath <2>
   ```

​	这里<1>代表MongoDB日志文件的位置，<2>表示数据的存储目录，在运行命令之前最好把这两个路径的文件先建好。

 - 关闭MongoDB后台服务

   ```
   mongo
   use admin
   db.shutdownServer()
   ```

先通过mongo命令进入mongod命令行，然后切换到管理员模式，最后关闭服务器。

关闭服务的第二种方式是指定数据存储目录

```
mongod --shutdown --dbpath <数据存储目录>
```

