package com.github.shadow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.shadow.entity.SysUser;
import com.github.shadow.request.UserPageRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xuwen
 * @since 2023-07-12
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * User page page.
     *
     * @param request the request
     * @return the page
     */
    IPage<SysUser> userPage(UserPageRequest request);

}
