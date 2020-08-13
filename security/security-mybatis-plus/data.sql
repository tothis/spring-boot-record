# user
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`               bigint(20) AUTO_INCREMENT COMMENT '主键',
    `user_name`        varchar(50) DEFAULT NULL COMMENT '用户名称',
    `password`         char(60)    DEFAULT NULL COMMENT '用户密码',
    `create_by`        bigint(20)  DEFAULT NULL COMMENT '数据创建者',
    `create_date_time` datetime(0) DEFAULT NULL COMMENT '数据创建时间',
    `update_by`        bigint(20)  DEFAULT NULL COMMENT '数据更新者',
    `update_date_time` datetime(0) DEFAULT NULL COMMENT '数据更新时间',
    `state`            tinyint(1)  DEFAULT 0 COMMENT '数据状态 0正常(默认) 1删除 2禁用 3锁定',
    PRIMARY KEY (`id`)
) COMMENT = '用户';

# role
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`               bigint(20) AUTO_INCREMENT COMMENT '主键',
    `role_name`        varchar(50) DEFAULT NULL COMMENT '角色名称',
    `create_by`        bigint(20)  DEFAULT NULL COMMENT '数据创建者',
    `create_date_time` datetime(0) DEFAULT NULL COMMENT '数据创建时间',
    `update_by`        bigint(20)  DEFAULT NULL COMMENT '数据更新者',
    `update_date_time` datetime(0) DEFAULT NULL COMMENT '数据更新时间',
    `state`            tinyint(1)  DEFAULT 0 COMMENT '数据状态 0正常(默认) 1删除 2禁用 3锁定',
    PRIMARY KEY (`id`)
) COMMENT = '角色';

# permission
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `id`               bigint(20) AUTO_INCREMENT COMMENT '主键',
    `permission_name`  varchar(50)  DEFAULT NULL COMMENT '权限名称',
    `permission_flag`  varchar(100) DEFAULT NULL COMMENT '权限标识',
    `url`              varchar(200) DEFAULT NULL COMMENT '接口地址',
    `create_by`        bigint(20)   DEFAULT NULL COMMENT '数据创建者',
    `create_date_time` datetime(0)  DEFAULT NULL COMMENT '数据创建时间',
    `update_by`        bigint(20)   DEFAULT NULL COMMENT '数据更新者',
    `update_date_time` datetime(0)  DEFAULT NULL COMMENT '数据更新时间',
    `state`            tinyint(1)   DEFAULT 0 COMMENT '数据状态 0正常(默认) 1删除 2禁用 3锁定',
    PRIMARY KEY (`id`)
) COMMENT = '权限';

# user_role
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`      bigint(20) AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
    `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
    PRIMARY KEY (`id`)
) COMMENT = '用户角色';

# role_permission
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`
(
    `id`            bigint(20) AUTO_INCREMENT COMMENT '主键',
    `role_id`       bigint(20) DEFAULT NULL COMMENT '角色id',
    `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
    PRIMARY KEY (`id`)
) COMMENT = '角色权限';