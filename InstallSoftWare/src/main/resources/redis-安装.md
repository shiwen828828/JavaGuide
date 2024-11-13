# 安装 Redis 步骤
### 1 创建目标目录

```
mkdir -p /opt/software/redis
```

### 2 解压 Redis 压缩包

```
   tar -zxvf /opt/package/redis-7.2.0.tar.gz -C /opt/software/redis
```

### 3 编译 Redis

```
cd /opt/software/redis/redis-7.2.0
make
make install PREFIX=/opt/software/redis/redis-7.2.0
```

因为Redis的安装一般来说对于系统依赖很少，只依赖了Linux系统基本的类库，所以安装很少出问题

**安装常见问题**

如果执行make命令报错：cc 未找到命令，原因是虚拟机系统中缺少gcc，执行下面命令安装gcc：

```
yum -y install gcc automake autoconf libtool make
```

如果执行make命令报错：致命错误:jemalloc/jemalloc.h: 没有那个文件或目录，则需要在make指定分配器为libc。执行下面命令即可正常编译：

```
make MALLOC=libc
```

### 4 创建启动和停止脚本

进入centos bin 文件

```
cd /usr/local/bin
```

start_redis.sh

```
#!/bin/bash

# Redis 安装路径
REDIS_HOME=/opt/software/redis/redis-7.2.0/
# Redis 配置文件路径
REDIS_CONF=$REDIS_HOME/redis.conf

echo "Starting Redis..."
$REDIS_HOME/bin/redis-server $REDIS_CONF &

if [ $? -eq 0 ]; then
    echo "Redis started successfully in the backgroud."
else
    echo "Failed to start Redis."
fi
```

stop_redis.sh

```
#!/bin/bash

# Redis 安装路径
REDIS_HOME=/opt/software/redis/redis-7.2.0/

echo "Stopping Redis..."
$REDIS_HOME/bin/redis-cli shutdown

if [ $? -eq 0 ]; then
    echo "Redis stopped successfully."
else
    echo "Failed to stop Redis."
fi
```

赋予root 权限

```
chmod +x start_redis.sh stop_redis.sh
```

