# CentOS7-安装MySQL

CentOS7的安装，采用的是yum的方式安装。

yum方式安装，就类似在Windows下不停的下一步，下一步。

根据官方的文档去安装MySQL。

## 一、MySQL5.7

直接跳转到这个路径，这个路径后期MySQL可能会变，最好是根据咱们的视频去官网找到指定路径

https://dev.mysql.com/downloads/repo/yum/

### 1.1 安装wget

直接执行yum的方式安装

```powershell
yum -y install wget
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/9818d9810c04416488c326a8ecf7e08a.png)

### 1.2 下载&安装MySQL的rpm源

直接执行下述命令，下载rpm源，同理最好是去官网找，因为下载路径可能会变

```powershell
wget https://dev.mysql.com/get/mysql80-community-release-el7-10.noarch.rpm
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/8f0c33a8f4854e5b9814fb58617606f7.png)

安装rpm源

```powershell
rpm -Uvh mysql80-community-release-el7-10.noarch.rpm
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/a9e8477acc99413586015f441e99edc5.png)

查看yum源中，关于MySQL的内容

```powershell
yum repolist all | grep mysql
```

发现默认安装的版本是8.0，需改修改为5.7

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/4dd3bc53f49d4f9d9c1065d79a23df0f.png)

### 1.3 修改MySQL安装版本

按照官方的形式，修改 `/etc/yum.repos.d/mysql-community.repo`文件

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/5129754693024c5e8db36e40f5a37a38.png)

需要将5.7的enabled设置为1，将8.0的enabled设置为0

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/743cc8198b3c4fd9992211ad55548ab1.png)

保存并退出之后，再次执行yum命令，查看当前安装的版本

```powershell
yum repolist all | grep mysql
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/61c3736a263144b4be8806a35bbc2a91.png)

### 1.4 下载并启动MySQL

直接安装MySQL社区版服务即可，执行下述指令

```powershell
yum -y install mysql-community-server
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/4c4441fc554144dd9540967b783242b4.png)

当上述安装完毕之后，默认MySQL服务是关闭，需要启动MySQL服务

```powershell
# 启动MySQL服务
systemctl start mysqld
# 开机自动启动MySQL
systemctl enable mysqld
```

登录MySQL服务前，需要找到yum方式安装后，生成的随机密码

```powershell
grep 'temporary password' /var/log/mysqld.log
```

找到密码后，直接登录

```powershell
mysql -u root -p
回车后，输入密码
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/c138ade38efd481385fc7370ef1a33a3.png)

登录之后，第一件事情，一定是修改密码

直接下述命令，密码有强度校验，至少8位，需要有小写字母，大写字母，数字和特殊符号

```powershell
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Zxcvb1.0';
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/1b3f9b9cc7d74cc7a5f185c2aac31f8e.png)

### 1.5 开启MySQL远程连接用户

直接基于grant命令，构建一个远程连接用户

```powershell
# 构建一个远程连接用户
GRANT ALL PRIVILEGES ON *.* TO 'zjw'@'%'  IDENTIFIED BY 'Zxcvb1.0' WITH GRANT OPTION;
# 刷新权限
FLUSH PRIVILEGES;
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/d175a1c45cad4c76ada1b8007ef613ba.png)

想使用其他服务的工具连接MySQL，需要让CentOS关闭防火墙，或者单独开放3306端口。

如果你用的是云服务器，需要在云服务器的控制台里开放安全组，释放3306

我这里就直接关闭防火墙，禁止防火墙开机自启

```powershell
# 关闭
systemctl stop firewalld
# 禁止开机自启
systemctl disable firewalld
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/6988fe4df88547ec89d77ac6c46e6136.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1693316507091/22eeb968fd9841ddab876a536ed08552.png)

需要注意一个点，有的同学，构建了远程连接用户，也关闭了防火墙，但是Navicat始终连接不上CentOS里的MySQL。如果有这个情况，你就先start防火墙，再关闭防火墙。

```powershell
systemctl start firewalld
systemctl stop firewalld
```

## 二、MySQL8.0

安装MySQL8.0和前面操作基本一模一样。

需要注意点的：

1. 跳过将MySQL8.0安装修改为5.7的过程。
2. 再远程连接时，发现无法连接，需要以这种方式修改密码更换命令，需要指定一下加密方式

```powershell
# 8.0修改密码
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Zxcvb1.0';
# 刷新一下
FLUSH PRIVILEGES;
```

3 . 如果远程连接无法成功 要查询一下表;

```powershell
mysql -uroot -p
use mysql;
select host;user from host;
# 如果user 是root 的host 的值是localhost 要update 成%
update user set host ='%' where user='root';
FLUSH PRIVILEGES;
```

