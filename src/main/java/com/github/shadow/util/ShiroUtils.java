package com.github.shadow.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {

    public static Integer getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        return subject != null ? (Integer)subject.getSession().getAttribute("userId") : null;
    }

    public static Subject getCurrentSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getCurrentSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject != null ? subject.getSession() : null;
    }

    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的4字节，字符串长度为8
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes(4).toHex();
    }

}
