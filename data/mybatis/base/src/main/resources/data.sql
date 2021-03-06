DROP TABLE IF EXISTS `type`, `user`, `tree`;

CREATE TABLE `type`
(
    `id`     BIGINT AUTO_INCREMENT COMMENT '主键',
    `detail` VARCHAR(20) DEFAULT '' COMMENT '类型',
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`       BIGINT AUTO_INCREMENT COMMENT '主键',
    `type_id`  BIGINT COMMENT '用户类型',
    `name`     VARCHAR(20) DEFAULT '' COMMENT '名称',
    `password` VARCHAR(40) DEFAULT '' COMMENT '密码',
    `age`      TINYINT(1)  DEFAULT NULL COMMENT '年龄',
    `mail`     VARCHAR(40) DEFAULT '' COMMENT '邮箱',
    `birthday` DATETIME    DEFAULT NULL COMMENT '生日',
    `state`    TINYINT(1)  DEFAULT 0 COMMENT '标识 0正常(默认) 1删除',
    PRIMARY KEY (`id`)
);

CREATE TABLE `tree`
(
    `id`        BIGINT(20) AUTO_INCREMENT COMMENT '主键id',
    `parent_id` BIGINT(20)  DEFAULT 0 COMMENT '上级节点id',
    `name`      VARCHAR(20) DEFAULT '' COMMENT '名称',
    PRIMARY KEY (`id`)
);

INSERT INTO `type` (detail)
VALUES ('类型');
INSERT INTO `user` (type_id, name)
VALUES (1, '李磊');
INSERT INTO `user` (type_id, name)
VALUES (1, 'frank');

INSERT INTO tree (id, parent_id, name)
VALUES (1, 0, '1');
INSERT INTO tree (id, parent_id, name)
VALUES (2, 1, '1-1');
INSERT INTO tree (id, parent_id, name)
VALUES (3, 2, '1-1-1');
INSERT INTO tree (id, parent_id, name)
VALUES (4, 0, '2');
INSERT INTO tree (id, parent_id, name)
VALUES (5, 4, '2-1');
