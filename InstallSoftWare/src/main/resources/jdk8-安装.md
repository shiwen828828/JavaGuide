### 一 下载JDK 压缩包上传 到Linux系统

百度网盘中有

###  二 解压压缩包到指定的java 目录
```
tar -zxvf jdk-8u411-linux-x64.tar.gz -C /opt/software/java/
```


### 三 配置环境变量
```
vim /etc/profile

export JAVA_HOME=/opt/software/java/jdk1.8.0_411

export PATH=$JAVA_HOME/bin:$PATH
```



### 四 重启环境并且验证
```
source /etc/profile

echo $JAVA_HOME

java -version
```