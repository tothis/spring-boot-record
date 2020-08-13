DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name` VARCHAR(20) DEFAULT '' COMMENT '用户名',
    PRIMARY KEY (`id`)
);

INSERT INTO `user` (user_name) VALUE ('李磊'), ('frank');