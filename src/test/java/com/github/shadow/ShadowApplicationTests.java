//package com.denachina.shadow;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.github.shadow.entity.SysUser;
//import com.github.shadow.service.ISysUserService;
//
//@SpringBootTest
//public class ShadowApplicationTests {
//
//    @Autowired
//    ISysUserService sysUserService;
//
//    @Test
//    public void contextLoads() {
//        SysUser user = sysUserService.getOne(
//            Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, "admin").eq(SysUser::getPassword, "123456"));
//        System.out.println(user);
//    }
//
//}
