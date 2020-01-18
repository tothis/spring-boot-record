CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `age` tinyint(1) DEFAULT NULL COMMENT '年龄',
  `mail` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `address` varchar(40) DEFAULT NULL COMMENT '地址',
  `is_del` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标识 0正常(默认) 1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;