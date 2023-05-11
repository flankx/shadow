package com.denachina.shadow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denachina.shadow.dao.SysUser;
import com.denachina.shadow.dao.SysUserRepository;
import com.denachina.shadow.service.SysUserService;
import com.denachina.shadow.util.DbUtil;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserRepository sysUserRepository;

    @Transactional(value = "customTransactionManager", readOnly = true)
    public SysUser getSysUserByUsernameAndPassword(String userName, String password) {
        return sysUserRepository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public SysUser getSysUserInfo(String username, String password) {
        DbUtil.setDbW();
        return getSysUserByUsernameAndPassword(username, password);
    }

}
