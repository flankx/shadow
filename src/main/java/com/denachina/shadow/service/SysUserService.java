package com.denachina.shadow.service;

import com.denachina.shadow.dao.SysUser;

public interface SysUserService {

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    SysUser getSysUserInfo(String username, String password);

}

