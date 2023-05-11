package com.denachina.shadow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denachina.shadow.dao.UserData;
import com.denachina.shadow.dao.UserDataRepository;
import com.denachina.shadow.service.UserDataService;
import com.denachina.shadow.util.DbUtil;

@Service
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    UserDataRepository userDataRepository;

    @Transactional(value = "customTransactionManager", readOnly = true)
    List<UserData> findAllUserData() {
        return userDataRepository.findAll();
    }

    @Transactional(value = "customTransactionManager", rollbackFor = Exception.class)
    UserData insertUserDataOnDuplicateKey(UserData userData) {
        return userDataRepository.save(userData);
    }

    @Transactional(value = "customTransactionManager", rollbackFor = Exception.class)
    void deleteUserDataByUserId(Integer UserId) {
        userDataRepository.deleteById(UserId);
    }

    @Override
    public List<UserData> getAllUserData() {
        return findAllUserData();
    }

    @Override
    public UserData insertUserData(UserData userData) {
        DbUtil.setDbW();
        return insertUserDataOnDuplicateKey(userData);
    }

    @Override
    public int deleteUserData(Integer UserId) {
        DbUtil.setDbW();
        try {
            deleteUserDataByUserId(UserId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

}
