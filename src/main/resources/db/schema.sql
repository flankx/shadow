CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       varchar(64)  DEFAULT NULL COMMENT '用户ID',
    `user_name`     varchar(64)  DEFAULT NULL COMMENT '名称',
    `password`      varchar(64)  DEFAULT NULL COMMENT '数据源类型',
    `nick_name`     varchar(64)  DEFAULT NULL COMMENT '连接地址',
    `avatar`        text COMMENT '用户头像',
    `sex_type`      tinyint(4)   DEFAULT '-1' COMMENT '性别 0=女 1=男',
    `email`         varchar(64)  DEFAULT NULL COMMENT '邮箱',
    `phone_no`      varchar(32)  DEFAULT NULL COMMENT '手机',
    `delete_status` tinyint(4)   DEFAULT NULL COMMENT '备注',
    `permissions`   varchar(255) DEFAULT NULL COMMENT '创建人',
    `extra`         varchar(255) DEFAULT NULL COMMENT '创建部门',
    `create_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name_UK` (`user_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

