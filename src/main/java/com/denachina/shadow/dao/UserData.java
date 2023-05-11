package com.denachina.shadow.dao;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "sex_type")
    private String sexType;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    @Column(name = "is_freeze")
    private Integer isFreeze;
    @Column(name = "user_status")
    private Integer userStatus;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "create_time")
    private ZonedDateTime createTime;
    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    public UserData() {}

    public UserData(String userName, String sexType, LocalDate birthday, String email, String phoneNo) {
        this.userName = userName;
        this.sexType = sexType;
        this.birthDay = birthday;
        this.isFreeze = 0;
        this.userStatus = 0;
        this.email = email;
        this.phoneNo = phoneNo;
        this.createTime = ZonedDateTime.now();
        this.updateTime = ZonedDateTime.now();
    }

}
