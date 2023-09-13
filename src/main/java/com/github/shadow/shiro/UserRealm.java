package com.github.shadow.shiro;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
        String permissions = (String)session.getAttribute("permissions");
        String roles = (String)session.getAttribute("roles");
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (StringUtils.isNotBlank(roles)) {
            authorizationInfo.addRoles(Arrays.asList(roles.split(",")));
        }
        if (StringUtils.isNotBlank(permissions)) {
            authorizationInfo.addStringPermissions(Arrays.asList(permissions.split(",")));
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String)token.getPrincipal();
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, userName));
        if (user == null) {
            // 没找到帐号
            throw new UnknownAccountException();
        }
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo =
            new SimpleAuthenticationInfo(userName, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        // 将用户信息放入session中
        SecurityUtils.getSubject().getSession().setAttribute("userId", user.getId());
        SecurityUtils.getSubject().getSession().setAttribute("userName", user.getUserName());
        SecurityUtils.getSubject().getSession().setAttribute("roles", null);
        SecurityUtils.getSubject().getSession().setAttribute("permissions", user.getPermissions());
        return authenticationInfo;
    }
}
