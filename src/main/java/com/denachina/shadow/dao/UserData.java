package com.denachina.shadow.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Data
public class UserData {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    private String userName;

    private String sexType;

    private LocalDate birthday;

    private String jobName;

    private Short interest;

    private String intro;

    private Short isFreeze;

    private Short userStatus;

    private Integer level;

    private String email;

    private String phoneNo;

    private ZonedDateTime createdOn;

    private ZonedDateTime updatedOn;

    public UserData(){}

    public UserData(String userName,String sexType, LocalDate birthday,String jobName,String intro,Integer level,String email,String phoneNo){
        this.userName = userName;
        this.sexType = sexType;
        this.birthday = birthday;
        this.jobName = jobName;
        this.interest = 0;
        this.intro = intro;
        this.isFreeze = 0;
        this.userStatus = 0;
        this.level = level;
        this.email = email;
        this.phoneNo = phoneNo;
        this.createdOn = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.updatedOn = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
    }

}
