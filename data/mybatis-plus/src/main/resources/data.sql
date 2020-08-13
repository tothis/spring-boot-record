DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`        BIGINT AUTO_INCREMENT COMMENT '主键',
    `user_name` VARCHAR(20) DEFAULT '' COMMENT '用户名',
    `state`     BIT(1)      DEFAULT b'0' COMMENT '标识 0正常(默认) 1删除',
    PRIMARY KEY (`id`)
);

INSERT INTO `user` (user_name) VALUE ('李磊'), ('frank');