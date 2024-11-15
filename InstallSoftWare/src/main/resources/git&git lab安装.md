## Git 篇

### 1 下载GIT

```
   wget https://mirrors.edge.kernel.org/pub/software/scm/git/git-2.34.1.tar.gz
```

### 2 解压GIT 到指定安装包

```
tar -zxvf git-2.34.1.tar.gz -C ../software/git/
```

### 3 编译和安装

#### 如果是编译在自己定义好的位置 需要配置环境变量 如果是编译在/usr/local 则不需要

```
   make prefix=/usr/local all
   sudo make prefix=/usr/local install
```

```
make prefix=/opt/software/git/git-2.34.1 all
sudo make prefix=/opt/software/git/git-2.34.1 install
```

配置环境变量并刷新

```
export PATH=/opt/software/git/git-2.34.1/bin:$PATH
```

#### 注意 编译的过程中可能遇到缺少依赖项的报错 可以先安装依赖项 再编译

curl 依赖项

```
   sudo yum install curl-devel openssl-devel -y
```

OpenSSL 依赖项

```
   sudo yum install expat-devel gettext-devel zlib-devel perl-ExtUtils-MakeMaker -y
```

### 4 验证git version

```
git --version
```



### 5 git 入门 命令

#### 5.1为什么建议使用命令行的方式操作git？ 

1.命令行会了，图形界面的操作时完全没问题的，反之，则不然

 2.有些地方如linux服务器，没有图形界面， 如果碰到问题需要使用git，不会命令行操作啥都干不了 

#### 5.2 git 本地操作 

git --help 调出Git的帮助文档 

git +命令 --help 查看某个具体命令的帮助文档 

git --version 查看git的版本 

git init 生成空的本地仓库 

git add 将文件添加到暂存区

初次commit之前，需要配置用户邮箱及用户名，使用以下命令 

git config --global user.email "you@example.com" 

git config --global --list 查看配置

git commit 将暂存区里的文件提交到本地仓库

git remote 用于管理远程仓库 

git push -u origin master  往名字为origin的远程仓库的master分支上提交本地Master分支的变更

git push -u origin local_branch:remot_branch 如果本地和远程的分支不一样 可以写成

git fetch 拉取远程仓库的变更到本地仓库 

git merge origin/master 将远程的变更，合并到本地仓库的master分支 

git pull 不建议使用 等同于fetch之后merge

git remote add origin https://github.com/username/repo.git 添加远程仓库

git remote -v 查看当前的远程仓库信息

git remote set-url origin https://github.com/username/new-repo.git  修改远程仓库的 URL

git remote rename origin upstream 重命名远程仓库

git remote remove origin  删除远程仓库

### 6 生成一个SSH 密钥方便提交远程代码

#### 6.1 使用 ssh-keygen 命令生成 SSH 密钥对。默认情况下，密钥对会保存在 ~/.ssh 目录下。

-t rsa：指定生成的密钥类型为 RSA。
-b 4096：指定密钥长度为 4096 位。
-C "your_email@example.com"：添加一个标签，通常是你的电子邮件地址，方便识别。

```
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
```

```
ssh-keygen -t ed25519 -C "your_email@example.com"
```

#### 6.2 运行上述命令后，你会看到一些提示信息：

保存位置：默认保存位置是 ~/.ssh/id_rsa（RSA 密钥）。按回车键接受默认位置。

#### 6.3 设置密码：你可以选择为私钥设置一个密码（推荐），或者直接按回车键跳过。

```
   Enter passphrase (empty for no passphrase):
   Enter same passphrase again:
```

#### 6.4  查看生成的密钥对
生成的密钥对文件会保存在 ~/.ssh 目录下：
私钥文件：id_rsa 或 id_ed25519
公钥文件：id_rsa.pub 或 id_ed25519.pub

#### 6.5  将公钥添加到远程服务器(适用于普通远程服务器 不适用与GITHUB)
将生成的公钥添加到远程服务器的 ~/.ssh/authorized_keys 文件中。你可以使用 ssh-copy-id 命令来完成这一操作。
使用 ssh-copy-id 命令

```
ssh-copy-id -i ~/.ssh/id_rsa.pub username@remote_host
```

#### 6.6  测试 SSH 连接
使用 SSH 连接到远程服务器，验证是否可以无密码登录：

```
ssh username@remote_host
```

## GIT Lab 篇

### 1 gitlab的简介 

#### gitlab是什么 

是一个用于仓库管理系统的开源项目，使用Git作为代码管理工具，并在此基础上搭建起来的web服务。 基础 功能免费，高级功能收费

#### 为什么要使用gitlab

基础功能开源，可自行搭建 可以进行权限控制，使得代码对部分人可见 gitlab使用方便，非常适合企业内部 使用

### gitlab的安装

#### 添加gitlab仓库，并安装

```
   wget https://packages.gitlab.com/gitlab/gitlab-ce/packages/el/7/gitlab-ce-<version>.ce.x86_64.rpm/download.rpm
   
```

#### 启动gitlab

