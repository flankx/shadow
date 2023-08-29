package com.github.shadow.config.shiro;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.shadow.entity.SysUser;
import com.github.shadow.service.ISysUserService;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    ISysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Session session = SecurityUtils.getSubject().getSession();
        // 查询用户的权限
        SysUser user = (SysUser)session.getAttribute("userInfo");
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(Arrays.asList(user.getPermissions().split(",")));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String loginName = (String)token.getPrincipal();
        // 获取用户密码
        String password = new String((char[])token.getCredentials());
        SysUser user = sysUserService.getOne(
            Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, loginName).eq(SysUser::getPassword, password));
        if (user == null) {
            // 没找到帐号
            throw new UnknownAccountException();
        }
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo =
            new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(),
                // ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
                getName());
        // session中不需要保存密码
        user.setPassword("");
        // 将用户信息放入session中
        SecurityUtils.getSubject().getSession().setAttribute("userInfo", user);
        return authenticationInfo;
    }
}
