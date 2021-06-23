# Kafka

1. 启动 ZooKeeper

   `./zkServer.sh status` 查看 ZooKeeper 启动状态，`Node: standalone`为已启动。 启动`./zkServer.sh start`。

2. 配置 Kafka 运行配置

   `vi server.properties` 修改 listeners 和 advertised.listeners 地址

3. 启动 Kafka

   使用守护进程模式运行 `nohup bin/kafka-server-start.sh config/server.properties`

4. 测试

- 创建一个 topic
  ：`bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test`
- 打开一个消息发送者的窗口发送一条消息（输入如下命令会出现输入框 输入信息回车后发送信息）：`bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test`
- 接收消息：`bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning`
