spring-boot使用docker部署
***
+ 开启docker tcp功能
在ExecStart所在值后添加 -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375
vim /usr/lib/systemd/system/docker.service
+ 设置docker环境变量
在代码本机配置环境变量DOCKER_HOST值为tcp://192.168.1.2:2375
***
>镜像&容器就像java中的类与实例 镜像为类 而容器问镜像的实例
```shell
# 查看docker版本
$ docker -v

# 启动
$ systemctl start docker
# 守护进程重启
$ systemctl daemon-reload
# 重启docker服务
$ systemctl restart  docker 或 service docker restart
# 关闭docker
$ systemctl stop docker 或 service docker stop

# 查看镜像
$ docker images
# 查看容器 -a可显示未运行的容器
$ docker ps -a

# docker启动容器分两类三种 基于镜像(交互方式 守护态方式运行) 将终止状态下的容器启动(短暂方式)
# 交互方式 基于镜像创建容器并运行 -i让容器的标准输入保持打开 -t让Docker分配一个伪终端并绑定在容器的标准输入上
$ docker run -it 仓库名:标签名

# 使用镜像app:v1.0 以交互模式启动一个容器 命名为app_1.0 在容器内执行'/bin/bash' 将容器80端口映射至本地主机 127.0.0.1的8080端口
$ docker run -it -p 127.0.0.1:8080:80 --name app_1.0 app:v1.0 /bin/bash

# 守护态方式运行 daemon 如启动centos后台容器 每隔一秒打印当天的日历
$ docker run -d centos /bin/sh -c "while true;do echo hello docker;sleep 1;done"

# 短暂方式 将终止状态下的容器启动
$ docker start centos

# 关闭正在运行的容器
docker stop 容器名称|容器id
# 强制停止正在运行的容器 一般不用 除非卡了
$ docker kill 容器名称|容器id
# 删除容器 容器要处于关闭状态
$ docker rm 容器名称|容器id
# 删除镜像 依赖的容器要被删除
$ docker rmi 镜像名称|镜像id

# 启动已运行过的容器
docker start 容器名称|容器id
# 启动所有运行过的容器 注意反引号不要写错 docker ps -a -q为查询所有运行过的容器id
docker start `docker ps -a -q` # 或 docker start `docker ps -a -q`
# 停止所有在运行的容器
docker stop `docker ps -a -q` # 或docker stop $(docker ps -a -q)
# 删除所有容器
docker rm $(docker ps -a -q)
# 删除所有镜像
docker rmi $(docker images -q)

# 查看启动容器的输出
$ docker logs $CONTAINER_ID # 在容器外查看
$ docker attach $CONTAINER_ID # 连接容器实时查看
```
***
修改docker镜像地址
```
# vi /etc/docker/daemon.json
{
    "registry-mirrors": ["http://hub-mirror.c.163.com"]
}
# 重启docker
```