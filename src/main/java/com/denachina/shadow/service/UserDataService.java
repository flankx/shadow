package com.denachina.shadow.service;

import com.denachina.shadow.dao.UserData;

public interface UserDataService {
    UserData getUserDataByJobName(String jobName);
}
