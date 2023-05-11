package com.denachina.shadow.dao;

import java.time.ZonedDateTime;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "delete_status")
    private Integer deleteStatus;
    @Column(name = "permissions")
    private String permissions;
    @Column(name = "extra")
    private String extra;
    @Column(name = "create_time")
    private ZonedDateTime createTime;
    @Column(name = "update_time")
    private ZonedDateTime updateTime;
}
