package com.denachina.shadow.dao;

import lombok.Data;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Data
public class UserData /*implements Serializable*/ {

//    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "user_data_user_id_seq", sequenceName = "user_data_user_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_user_id_seq")
    private Integer userId;

    private String userName;

    private String sexType;

    private Date birthday;

    private String jobName;

    private Short interest;

    private String intro;

    private Short isFreeze;

    private Short userStatus;

    private Integer level;

    private String email;

    private String phoneNo;

    private Timestamp createdOn;

    private Timestamp updatedOn;

}
