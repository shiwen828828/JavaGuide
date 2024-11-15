### 1. 下载并解压 MySQL 压缩包
首先，确保你已经下载了 MySQL 的压缩包。假设压缩包名为 mysql-8.0.26-linux-glibc2.12-x86_64.tar.xz，并且位于 /opt/software/mysql/ 目录下。

```
cd /opt/software/mysql
tar -xf mysql-8.0.26-linux-glibc2.12-x86_64.tar.xz
```

### 2. 创建 MySQL 目录结构
创建 MySQL 的安装目录和数据目录，并设置适当的权限。

```
sudo mkdir -p /opt/software/mysql/data
sudo chown -R root:root /opt/software/mysql
```



### 3. 移动 MySQL 文件
将解压后的 MySQL 文件移动到 /opt/software/mysql 目录下，并创建符号链接到 /usr/local/mysql。

```
sudo mv mysql-8.0.26-linux-glibc2.12-x86_64/* /opt/software/mysql/
sudo ln -s /opt/software/mysql /usr/local/mysql
```

### 4. 配置 my.cnf 文件
编辑或创建 my.cnf 文件，确保配置正确。

```
sudo nano /etc/my.cnf
```

添加以下内容：

```
[mysqld]
basedir = /opt/software/mysql
datadir = /opt/software/mysql/data
socket = /opt/software/mysql/mysql.sock
pid-file = /opt/software/mysql/mysql.pid
log-error = /opt/software/mysql/error.log
lc-messages-dir = /opt/software/mysql/share

symbolic-links=0

[mysqld_safe]
log-error=/opt/software/mysql/error.log
pid-file=/opt/software/mysql/mysql.pid

[client]
socket = /opt/software/mysql/mysql.sock

!includedir /etc/my.cnf.d

```



### 5. 初始化 MySQL 数据目录

使用 mysqld 命令初始化 MySQL 数据目录。这会生成初始的数据库文件和临时密码。

```
cd /usr/local/mysql/bin
sudo ./mysqld --initialize --user=root --basedir=/opt/software/mysql --datadir=/opt/software/mysql/data
```

初始化完成后，你会看到类似以下的输出，记录下临时密码：

```
2024-11-12T17:47:31.454480Z 5 [Note] [MY-010454] [Server] A temporary password is generated for root@localhost: <临时密码>
```



### 6. 启动 MySQL 服务

使用 mysqld_safe 启动 MySQL 服务。

```
cd /usr/local/mysql/bin
sudo ./mysqld_safe --user=root &
```

### 7. 检查日志文件

查看日志文件，确认 MySQL 是否成功启动。

```
cat /opt/software/mysql/error.log
```

#### 注意 也可以通过查看进程看是否服务启动成功

```
ps aux | grep mysqld
```

#### 注意 如果启动时候报错  找不到errmsg 文件 执行这个命令 然后重新启动MYSQL 服务

```
sudo touch /opt/software/mysql/share/errmsg.sys
sudo chown root:root /opt/software/mysql/share/errmsg.sys
sudo chmod 644 /opt/software/mysql/share/errmsg.sys
```



### 8. 验证安装
尝试连接到 MySQL 服务器，使用初始化过程中生成的临时密码。

```
mysql -u root -p
```

输入临时密码后，你应该能够成功连接到 MySQL 服务器。

#### 注意 如果socket 连接有问题 可以使用 mysql -u root -p -h 127.0.0.1

#### 注意: 为了能在所有目录中启动MYSQL 需要配置环境变量 在/etc/profile

```
export PATH=$PATH:/opt/software/mysql/mysql-8.0.26-linux-glibc2.12-x86_64/bin
```

#### 注意 如果socket 方式连接不上 要检查服务端 和客户端的文件是否一致

```
ls /etc/my.cnf /etc/mysql/my.cnf /opt/software/mysql/mysql-8.0.26-linux-glibc2.12-x86_64/my.cnf
```



### 9. 修改 root 密码（可选）
为了安全起见，建议立即修改 root 用户的密码。

```
ALTER USER 'root'@'localhost' IDENTIFIED BY '220554';
FLUSH PRIVILEGES;
EXIT;
```



### 10. 关闭 MySQL 服务
首先，关闭正在运行的 MySQL 服务。

```
cd /opt/software/mysql/mysql-8.0.26-linux-glibc2.12-x86_64/bin
sudo ./mysqladmin shutdown -u root -p
```

###  11. 创建启动 MySQL 服务的脚本 start_mysql.sh

```
#!/bin/bash

# MySQL 安装路径
MYSQL_HOME="/opt/software/mysql/mysql-8.0.26-linux-glibc2.12-x86_64"

# MySQL 二进制文件路径
MYSQL_BIN="${MYSQL_HOME}/bin"

# 启动 MySQL 服务
${MYSQL_BIN}/mysqld_safe --user=root &

# 等待一段时间，让 MySQL 服务完全启动
sleep 10

# 检查 MySQL 服务是否启动成功
if pgrep -x "mysqld" > /dev/null; then
    echo "MySQL 服务已成功启动。"
else
    echo "MySQL 服务启动失败。"
fi
```

###  12. 创建停止 MySQL 服务的脚本 stop_mysql.sh

```
#!/bin/bash

# MySQL 安装路径
MYSQL_HOME="/opt/software/mysql/mysql-8.0.26-linux-glibc2.12-x86_64"

# MySQL 二进制文件路径
MYSQL_BIN="${MYSQL_HOME}/bin"

# 停止 MySQL 服务
${MYSQL_BIN}/mysqladmin shutdown -u root -p

# 等待一段时间，让 MySQL 服务完全停止
sleep 10

# 检查 MySQL 服务是否停止成功
if ! pgrep -x "mysqld" > /dev/null; then
    echo "MySQL 服务已成功停止。"
else
    echo "MySQL 服务停止失败。"
fi
```

### 13. 赋予脚本执行权限
确保这两个脚本具有执行权限。

```
chmod +x /usr/local/bin/start_mysql.sh
chmod +x /usr/local/bin/stop_mysql.sh
```

### 14. 使用脚本启动和停止 MySQL 服务
启动 MySQL 服务

停止MYSQL 服务

### 15 配置MYSQL 客户端全局登录

```
vi /etc/profile

export PATH=$PATH:/opt/software/mysql/mysql-8.0.26-linux-glibc2.12-x86_64/bin

source /etc/profile
```

### 16 如果远程连接无法成功 要查询一下表;

```
mysql -uroot -p
use mysql;
select host,user from user;
# 如果user 是root 的host 的值是localhost 要update 成%
update user set host ='%' where user='root';
FLUSH PRIVILEGES;
```

