package com.denachina.shadow.service.impl;

import com.denachina.shadow.dao.UserData;
import com.denachina.shadow.dao.UserDataRepository;
import com.denachina.shadow.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDataServiceImpl implements UserDataService{

    private final UserDataRepository userDataRepository;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Transactional(value = "postgresqlTransactionManager", readOnly = true)
    UserData findUserDataByJobName(String jobName) {
        return userDataRepository.findTopByJobNameOrderByCreatedOnDesc(jobName);
    }

    @Override
    public UserData getUserDataByJobName(String jobName) {
        return findUserDataByJobName(jobName);
    }
}
