DROP DATABASE IF EXISTS one;
DROP DATABASE IF EXISTS two;
DROP TABLE IF EXISTS center.`table`, center.`user`;

CREATE TABLE center.`table`
(
    `id`        varchar(50)  NOT NULL,
    `url`       varchar(255) NOT NULL,
    `user_name` varchar(50)  NOT NULL,
    `password`  varchar(50)  NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `center`.`table`(`id`, `url`, `user_name`, `password`)
VALUES ('db1', 'jdbc:mysql://192.168.92.134:3306/one?useUnicode=true&characterEncoding=utf8&useSSL=false', 'root',
        '123456');
INSERT INTO `center`.`table`(`id`, `url`, `user_name`, `password`)
VALUES ('db2', 'jdbc:mysql://192.168.92.134:3306/two?useUnicode=true&characterEncoding=utf8&useSSL=false', 'root',
        '123456');

CREATE TABLE center.`user`
(
    `id`        BIGINT(20) AUTO_INCREMENT COMMENT '主键',
    `user_name` VARCHAR(20) DEFAULT NULL COMMENT '用户名',
    PRIMARY KEY (`id`)
);

INSERT INTO center.`user`
VALUES (1, 'center');

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