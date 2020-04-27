CREATE TABLE `user` (
    `id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name` VARCHAR ( 20 ) DEFAULT NULL COMMENT '用户名',
    `password` VARCHAR ( 40 ) DEFAULT NULL COMMENT '密码',
    `age` TINYINT ( 1 ) DEFAULT NULL COMMENT '年龄',
    `mail` VARCHAR ( 40 ) DEFAULT NULL COMMENT '邮箱',
    `birthday` datetime DEFAULT NULL COMMENT '生日',
    `is_del` BIT ( 1 ) NOT NULL DEFAULT b'0' COMMENT '删除标识 0正常(默认) 1删除',
    PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `tree` (
    `id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `parent_id` BIGINT ( 20 ) NOT NULL COMMENT '上级节点id',
    `name` VARCHAR ( 20 ) DEFAULT NULL COMMENT '名称',
    PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;

INSERT INTO tree(id, parent_id, name) VALUES (1, 0, '1');
INSERT INTO tree(id, parent_id, name) VALUES (2, 1, '1-1');
INSERT INTO tree(id, parent_id, name) VALUES (3, 2, '1-1-1');
INSERT INTO tree(id, parent_id, name) VALUES (4, 0, '2');
INSERT INTO tree(id, parent_id, name) VALUES (5, 4, '2-1');