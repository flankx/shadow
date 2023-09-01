package com.github.shadow.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.github.shadow.entity.SysUser;

public class ShiroUtils {

    public static SysUser getCurrentUser() {
        SysUser currentUSer = null;
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            currentUSer = (SysUser)subject.getSession().getAttribute("userInfo");
        }
        return currentUSer;
    }
}
