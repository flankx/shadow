package com.denachina.shadow.service;

import java.util.List;

import com.denachina.shadow.dao.UserData;

public interface UserDataService {

    List<UserData> getAllUserData();

    UserData insertUserData(UserData userData);

    int deleteUserData(Integer UserId);

}
