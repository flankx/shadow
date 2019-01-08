package com.denachina.shadow.service.impl;

import com.denachina.shadow.dao.UserData;
import com.denachina.shadow.dao.UserDataRepository;
import com.denachina.shadow.service.UserDataService;
import com.denachina.shadow.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(value = "postgresqlTransactionManager", readOnly = true)
    List<UserData> findAllUserData() {
        return userDataRepository.findAll();
    }

    @Transactional(value = "postgresqlTransactionManager")
    int updateJobname(Integer UserId, String jobName) {
        return userDataRepository.updateJobName(jobName,UserId);
    }

    @Transactional(value = "postgresqlTransactionManager", rollbackFor = Exception.class)
    UserData insertUserDataOnDuplicateKey(UserData userData) {
        return userDataRepository.save(userData);
    }

    @Transactional(value = "postgresqlTransactionManager", rollbackFor = Exception.class)
    void deleteUserDataByUserId(Integer UserId) {
        userDataRepository.deleteById(UserId);
    }

    @Override
    public List<UserData> getAllUserData() {
        return findAllUserData();
    }

    @Override
    public UserData getUserDataByJobName(String jobName) {
        return findUserDataByJobName(jobName);
    }

    @Override
    public int updateUserDataJobname(Integer UserId, String jobName) {
        DbUtil.setPostgresDbW();
        return updateJobname(UserId, jobName);
    }

    @Override
    public UserData insertUserData(UserData userData) {
        DbUtil.setPostgresDbW();
        return insertUserDataOnDuplicateKey(userData);
    }

    @Override
    public int deleteUserData(Integer UserId) {
        DbUtil.setPostgresDbW();
        try {
            deleteUserDataByUserId(UserId);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

}
