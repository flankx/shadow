package com.denachina.shadow.service.impl;

import com.denachina.shadow.dao.SysUser;
import com.denachina.shadow.dao.SysUserRepository;
import com.denachina.shadow.service.SysUserService;
import com.denachina.shadow.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserRepository sysUserRepository;

    @Transactional(value = "postgresqlTransactionManager", readOnly = true)
    SysUser getSysUserByUsernameAndPassword(String username, String password) {
        return sysUserRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public SysUser getSysUserInfo(String username, String password) {
        DbUtil.setPostgresDbW();
        return getSysUserByUsernameAndPassword(username, password);
    }

}
