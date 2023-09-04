package com.github.shadow.util;

import org.apache.shiro.SecurityUtils;
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
}
