CREATE DATABASE IF NOT EXISTS shadow;

CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`            int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       varchar(64)  DEFAULT NULL COMMENT '用户ID',
    `user_name`     varchar(64)  DEFAULT NULL COMMENT '名称',
    `password`      varchar(64)  DEFAULT NULL COMMENT '数据源类型',
    `nick_name`     varchar(64)  DEFAULT NULL COMMENT '连接地址',
    `delete_status` int          DEFAULT NULL COMMENT '备注',
    `permissions`   varchar(255) DEFAULT NULL COMMENT '创建人',
    `extra`         varchar(255) DEFAULT NULL COMMENT '创建部门',
    `create_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name_UK` (`user_name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

CREATE TABLE IF NOT EXISTS `user_data`
(
    `id`          int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     varchar(64)  DEFAULT NULL COMMENT '用户ID',
    `user_name`   varchar(64)  DEFAULT NULL COMMENT '名称',
    `sex_type`    varchar(8)   DEFAULT NULL COMMENT '性别',
    `birth_day`   date         DEFAULT NULL COMMENT '生日',
    `is_freeze`   int          DEFAULT NULL COMMENT '是否冻结',
    `user_status` int          DEFAULT NULL COMMENT '用户状态',
    `email`       varchar(255) DEFAULT NULL COMMENT '邮箱',
    `phone_no`    varchar(255) DEFAULT NULL COMMENT '手机',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name_UK` (`user_name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户信息表';

