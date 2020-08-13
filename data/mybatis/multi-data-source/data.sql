DROP DATABASE IF EXISTS one;
DROP DATABASE IF EXISTS two;

CREATE DATABASE one CHARSET utf8mb4;

CREATE TABLE one.`user`
(
    `id`        BIGINT(20) AUTO_INCREMENT COMMENT '主键',
    `user_name` VARCHAR(20) DEFAULT NULL COMMENT '用户名',
    PRIMARY KEY (`id`)
);

INSERT INTO one.`user`
VALUES (1, '李磊');

CREATE DATABASE two CHARSET utf8mb4;

CREATE TABLE two.`user`
(
    `id`        BIGINT(20) AUTO_INCREMENT COMMENT '主键',
    `user_name` VARCHAR(20) DEFAULT NULL COMMENT '用户名',
    PRIMARY KEY (`id`)
);

INSERT INTO two.`user`
VALUES (1, 'frank');