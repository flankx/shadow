CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     varchar(64)  DEFAULT NULL COMMENT '用户ID',
    `user_name`   varchar(64)  DEFAULT NULL COMMENT '用户名',
    `password`    varchar(64)  DEFAULT NULL COMMENT '密码',
    `nick_name`   varchar(64)  DEFAULT NULL COMMENT '昵称',
    `avatar`      mediumtext   DEFAULT NULL COMMENT '用户头像',
    `sex_type`    tinyint(4)   DEFAULT '-1' COMMENT '性别 0=女 1=男',
    `email`       varchar(64)  DEFAULT NULL COMMENT '邮箱',
    `phone_no`    varchar(32)  DEFAULT NULL COMMENT '手机',
    `salt`        varchar(8)   DEFAULT NULL COMMENT '盐值',
    `permissions` varchar(255) DEFAULT NULL COMMENT '权限',
    `extra`       varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name_UK` (`user_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- CREATE TABLE IF NOT EXISTS `sys_role`
-- (
--     `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
--     `name`        varchar(64)  DEFAULT NULL COMMENT '名称',
--     `sort`        varchar(64)  DEFAULT NULL COMMENT '描述',
--     `status`      tinyint      DEFAULT NULL COMMENT '状态',
--     `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--     `update_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
--     PRIMARY KEY (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';
--
-- CREATE TABLE IF NOT EXISTS `sys_role_permission`
-- (
--     `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
--     `name`        varchar(64)  DEFAULT NULL COMMENT '名称',
--     `code`        varchar(64)  DEFAULT NULL COMMENT '编号',
--     `type`        tinyint      DEFAULT NULL COMMENT '类型',
--     `url`         varchar(255) DEFAULT NULL COMMENT '地址',
--     `sort`        int(11)      DEFAULT NULL COMMENT '排序',
--     `status`      tinyint      DEFAULT NULL COMMENT '状态',
--     `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--     `update_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
--     PRIMARY KEY (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = utf8mb4 COMMENT ='权限表';
