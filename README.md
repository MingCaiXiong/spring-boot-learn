# 工程简介

# 延伸阅读

跳过测试直接打包

```shell
mvn clean  package -D maven.test.skip=true
```

源码启动命令

```shell
 mvn spring-boot:run
```

```shell
nohup java -jar -Dserver.port=8081 -Dspring.profiles.active=prod /root/mall-0.0.1-SNAPSHOT.jar > /root/null 2>&1 &
```

2>&1 解释：

将标准错误 2 重定向到标准输出 &1 ，标准输出 &1 再被重定向输入到 runoob.log 文件中。 查看项目否启动成功

* 0 – stdin (standard input，标准输入)
* 1 – stdout (standard output，标准输出)
* 2 – stderr (standard error，标准错误输出)

```shell
lsof -i:8080
```