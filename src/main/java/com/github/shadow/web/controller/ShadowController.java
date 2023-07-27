package com.github.shadow.web.controller;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.shadow.entity.UserData;
import com.github.shadow.service.IUserDataService;
import com.github.shadow.util.JsonUtils;

@Api(value = "用户信息", tags = "用户信息接口")
@RestController
@RequestMapping(value = "/userdata")
public class ShadowController {

    private final static Logger logger = LoggerFactory.getLogger(ShadowController.class);

    @Autowired
    IUserDataService userDataService;

    @GetMapping(value = "/list")
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
        List<UserData> userDataList = userDataService.list();

        return userDataList;
    }

    @PostMapping(value = "/add")
    public String insertUser(HttpServletRequest request) {
        UserData userData = new UserData();
        userData.setUserName("劳拉");
        userData.setSexType("female");
        userData.setBirthDay(LocalDate.parse("2007-12-24"));
        userData.setEmail("laola@gmail.com");
        userData.setPhoneNo("+90666666");
        logger.info("insert into table , data {}", JsonUtils.toJsonString(userData));

        userDataService.save(userData);

        return String.valueOf(userData.getId());
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteUser(HttpServletRequest request, @RequestParam("userId") Integer userId) {
        logger.info("delete record by userId {}", userId);

        return userDataService.removeById(userId);
    }
}
