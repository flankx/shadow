package com.denachina.shadow.controller;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.denachina.shadow.dao.UserData;
import com.denachina.shadow.service.UserDataService;

@RestController
@RequestMapping(value = "/userdata")
public class ShadowController {

    private final static Logger logger = LoggerFactory.getLogger(ShadowController.class);

    @Autowired
    UserDataService userDataService;

    @RequestMapping(value = "/list")
    public List<UserData> findAll(HttpServletRequest request) {
        String data = request.getQueryString();
        logger.info(data);

        Map<String, String[]> requestM = request.getParameterMap();

        Map<String, String> resultM = new HashMap<String, String>();

        for (String k : requestM.keySet()) {
            String v = requestM.get(k)[0];
            resultM.put(k, v);
        }
        logger.info(resultM.toString());
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String)headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        logger.info(map.toString());
        List<UserData> userDataList = userDataService.getAllUserData();

        // produceMessage.send("执行查询操作,UTC时间 "+Instant.now());
        return userDataList;
    }


    @RequestMapping(value = "/add")
    public String insertUser(HttpServletRequest request) {
        String userName = "劳拉";
        String sexType = "female";
        LocalDate birthday = LocalDate.parse("2007-12-24");
        String email = "laola@gmail.com";
        String phoneNo = "+90666666";
        UserData userData = new UserData(userName, sexType, birthday, email, phoneNo);
        logger.info("insert into table , data {}", userData);

        UserData userData1 = userDataService.insertUserData(userData);

        // produceMessage.send("执行新增操作,UTC时间 "+Instant.now() + " ; 插入对象 "+ userData1);

        return userData1.getUserId();
    }

    @RequestMapping(value = "/delete")
    public int deleteUser(HttpServletRequest request, @RequestParam("userId") Integer userId) {
        logger.info("delete record by userId {}", userId);

        // produceMessage.send("执行查询更新,UTC时间 "+Instant.now() + "; 更新的 userId "+ userId);

        return userDataService.deleteUserData(userId);
    }
}
