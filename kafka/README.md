kafka启动
1. 启动zookeeper
使用`./zkServer.sh status`查看zookeeper启动状态 `Node: standalone`为启动状态 否则执行`./zkServer.sh start`
2. 配置kafka运行配置
`vi server.properties` 修改listeners和advertised.listeners地址
3. 启动kafka
使用守护进程模式运行`nohup bin/kafka-server-start.sh config/server.properties`
使用jps检查kafka进程
4. 测试kafka
创建一个topic 基于topic可发送消息
`bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test`
打开一个消息发送者的窗口发送一条消息 输入如下命令会出现输入框 输入信息回车后发送信息
`bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test`
接收消息
`bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning`