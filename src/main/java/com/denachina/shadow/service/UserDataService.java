package com.denachina.shadow.service;

import com.denachina.shadow.dao.UserData;

import java.util.List;

public interface UserDataService {
    List<UserData> getAllUserData();
    UserData getUserDataByJobName(String jobName);
    int updateUserDataJobname(Integer UserId, String jobName);
    UserData insertUserData(UserData userData);
    int deleteUserData(Integer UserId);
}
