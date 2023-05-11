package com.denachina.shadow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.denachina.shadow.dao.SysUser;
import com.denachina.shadow.dao.SysUserRepository;
import com.denachina.shadow.service.SysUserService;

@SpringBootTest
public class ShadowApplicationTests {
    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SysUserService sysUserService;

    @Test
    public void contextLoads() {
        SysUser sysUsers = sysUserService.getSysUserInfo("admin", "123456");
        System.out.println(sysUsers);
    }

}
