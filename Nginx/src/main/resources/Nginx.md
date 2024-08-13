# Nginx简介
Nginx是一个高性能的Web服务器和反向代理的软件。
Web服务器：就是运行我们web服务的容器，提供web功能，还有tomcat也提供类似的功能。
代理是软件架构和网络设计中，非常重要的一个概念。有两种代理：正向代理和反向代理。
## 正向代理
用户端设置代理服务器。
所有的请求都由代理服务器发出，无法判断代理了多少用户端，叫正向代理。
## 反向代理
和正向代理相反：在服务端设置代理，所有请求，由服务端接受，然后再由代理服务器发到 后方的
服务器。这么一来，所有请求，都由一个服务器接收，无法判断代理了多少服务端。这就是反向代
理。
利用反向代理，就可以将请求分发到系统内部的多个节点上，从而减少每个节点的并发数。而这些节
点在外界看来，就是一个系统，表现出唯一的ip，也就是代理服务器的IP。
最初是由一个俄罗斯人（Igor Sysoev：伊戈尔 塞索耶夫）开发的。Nginx的第一个版本发布于
2004年，因其系统资源消耗低、运行稳定，且具有高性能的并发处理能力等特性，Nginx在互联网
企业中得到广泛应用。Nginx是互联网上最受欢迎的开源Web服务器之一，Netcraft公司2019年7月
的统计数据表明，Nginx为全球最繁忙网站中的25.42%提供了服务或代理。得益于近几年云计算和
微服务的快速发展，Nginx因在其中发挥了自身优势而得到广泛应用，且有望在未来占有更多的市场
份额。
2019年3月，著名硬件负载均衡厂商F5宣布收购Nginx，Nginx成为F5的一部分。
# Nginx安装
1. 安装：yum  
 [root@localhost /]# yum install yum-utils  
2. 切换目录：  
[root@localhost /]# cd /etc/yum.repos.d/  

3. 创建文件:  
[root@localhost yum.repos.d]# touch nginx.repo  

4. 修改文件内容:  
[nginx-stable]
 name=nginx stable repo
 baseurl=http://nginx.org/packages/centos/$releasever/$basearch/
 gpgcheck=1
 enabled=1
 gpgkey=https://nginx.org/keys/nginx_signing.key
 module_hotfixes=true
 [nginx-mainline]
 name=nginx mainline repo
 baseurl=http://nginx.org/packages/mainline/centos/$releasever/$basearch/
 gpgcheck=1
 enabled=0
 gpgkey=https://nginx.org/keys/nginx_signing.key
 module_hotfixes=true
 5. Nginx的安装:  
[root@localhost yum.repos.d]# yum install nginx

安装完成：我们的版本：nginx.x86_64 1:1.20.2-1.el7.ngx

# Nginx启动及验证
## 找到命令目录：
[root@localhost sbin]# pwd  
 /usr/sbin  
 [root@localhost sbin]#  
 
## 启动命令：
[root@localhost sbin]# ./nginx  
 [root@localhost sbin]# ps -ef | grep nginx  
 root      2536     1  0 09:28 ?        00:00:00 nginx: master process ./nginx
 nginx     2537  2536  0 09:28 ?        00:00:00 nginx: worker process
 root      2539  2053  0 09:28 pts/1    00:00:00 grep --color=auto nginx
## 验证nginx本机访问是否成功：
[root@localhost sbin]# curl localhost:80  
如果出现：Welcome to nginx!,证明nginx启动成功。  
## 关闭防火墙：
先查询防火墙的状态：[root@localhost sbin]# systemctl status firewalld  
关闭防火墙：[root@localhost sbin]# systemctl stop firewalld  
这样，在宿主机的浏览器中就可以访问了。  
## 开机禁用防火墙：
[root@localhost sbin]# systemctl disable firewall

# Nginx常用命令
## 查看nginx版本号：
[root@localhost sbin]# ./nginx -v  
 nginx version: nginx/1.20.2  
 [root@localhost sbin]#  
## 关闭Nginx命令：
[root@localhost sbin]# ./nginx -s stop  
 [root@localhost sbin]# ps -ef | grep nginx  
 root      2615  2053  0 09:39 pts/1    00:00:00 grep --color=auto nginx  
 [root@localhost sbin]#  
## 启动Nginx命令：
[root@localhost sbin]# ./nginx  
 [root@localhost sbin]#  

重载配置命令：  
备注：找到conf文件:  
[root@localhost sbin]# cd /etc/nginx/  
 [root@localhost nginx]# ll  
 total 24  
 drwxr-xr-x. 2 root root   26 Apr  6 05:13 conf.d  
 -rw-r--r--. 1 root root 1007 Nov 16 10:03 fastcgi_params  
 -rw-r--r--. 1 root root 5231 Nov 16 10:03 mime.types  
 lrwxrwxrwx. 1 root root   29 Apr  6 05:13 modules -> ../../usr/lib64/nginx/modules  
 -rw-r--r--. 1 root root  648 Nov 16 10:02 nginx.conf  
 -rw-r--r--. 1 root root  636 Nov 16 10:03 scgi_params  
 -rw-r--r--. 1 root root  664 Nov 16 10:03 uwsgi_params  
 [root@localhost nginx]#  
 ## 常用命令加餐：
### 关闭nginx：
### 找到pid  
[root@localhost run]# pwd  
 /var/run  
或者：  
[root@localhost run]# ps -ef | grep nginx  
 root      2617     1  0 09:40 ?        00:00:00 nginx: master process ./nginx  
 nginx     2622  2617  0 09:41 ?        00:00:00 nginx: worker process  
 root      2628  2053  0 09:48 pts/1    00:00:00 grep --color=auto nginx  
 ### 执行：
[root@localhost run]# kill -s QUIT 2617  

 ### 优雅停止nginx
 [root@localhost run]# /usr/sbin/nginx -s quit  
 [root@localhost run]# ps -ef | grep nginx  
 root      2638  2053  0 09:51 pts/1    00:00:00 grep --color=auto nginx  
 [root@localhost run]#  
