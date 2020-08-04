spring-boot使用[docker](https://tothis.gitee.io/docker/rong-qi/docker)部署
***
文档 https://tothis.gitee.io/docker/rong-qi/docker/dockerfile-maven-plugin
***
运行
```shell
docker run --name docker-test -p 8080:8080 -d docker:1.0
```
删除
```shell
docker stop docker-test && docker rm docker-test && docker rmi docker:1.0
```
