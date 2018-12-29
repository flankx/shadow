package com.denachina.shadow.controller;

import com.denachina.shadow.dao.UserData;

import com.denachina.shadow.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ShadowController {

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
    public UserData findAll(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return userDataService.getUserDataByJobName("出租车");
    }

    @RequestMapping(value = "/update")
    public int updateJob(HttpServletRequest request, @RequestParam("userId") Integer userId, @RequestParam("jobName") String jobName){
        return userDataService.updateUserDataJobname(userId, jobName);
    }
}
