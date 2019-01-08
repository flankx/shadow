package com.denachina.shadow.controller;

import com.denachina.shadow.dao.UserData;

import com.denachina.shadow.service.UserDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShadowController {

    private final static Logger logger = LoggerFactory.getLogger(ShadowController.class);

    private final UserDataService userDataService;

    @Autowired
    public ShadowController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @RequestMapping(value = "/find",
            method = {RequestMethod.POST,RequestMethod.GET},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
            )
    public List<UserData> findAll(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        logger.info(map.toString());
        return userDataService.getAllUserData();
    }

    @RequestMapping(value = "/update")
    public int updateJob(HttpServletRequest request, @RequestParam("userId") Integer userId, @RequestParam("jobName") String jobName){
        return userDataService.updateUserDataJobname(userId, jobName);
    }

    @RequestMapping(value = "/insert")
    public int insertUser(HttpServletRequest request){
        String userName = "劳拉";
        String sexType = "female";
        LocalDate birthday = LocalDate.parse("2007-12-24");
        String jobName = "tomb raider";
        String intro = "Life lies in adventure!";
        Integer level = 100;
        String email = "laola@gmail.com";
        String phoneNo = "+90666666";
        UserData userData = new UserData(userName,sexType,birthday,jobName,intro,level,email,phoneNo);
        logger.info("insert into table , data {}", userData);

        UserData userData1= userDataService.insertUserData(userData);
        return userData1.getUserId();
    }
    @RequestMapping(value = "/delete")
    public int deleteUser(HttpServletRequest request, @RequestParam("userId") Integer userId){
        logger.info("delete record by userId {}", userId);
        return userDataService.deleteUserData(userId);
    }
}
