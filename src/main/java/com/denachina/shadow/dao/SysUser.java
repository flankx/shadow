package com.denachina.shadow.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Data
public class SysUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    private String username;

    private String password;

    private String nickname;

    private Short deleteStatus;

    private String permissions;

    private String extra;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    public SysUser(){
    }
}
