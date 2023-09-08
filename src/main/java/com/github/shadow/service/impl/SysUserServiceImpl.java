package com.github.shadow.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shadow.entity.SysUser;
import com.github.shadow.mapper.SysUserMapper;
import com.github.shadow.request.UserPageRequest;
import com.github.shadow.service.ISysUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xuwen
 * @since 2023-07-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public IPage<SysUser> userPage(UserPageRequest request) {
        // 分页查询过滤头像和密码字段
        List<String> excludeFields = Arrays.asList("password", "avatar");
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SysUser.class, i -> !excludeFields.contains(i.getProperty()));
        if (StringUtils.isNotBlank(request.getNickName())) {
            queryWrapper.eq(SysUser::getNickName, request.getNickName());
        }
        if (StringUtils.isNotBlank(request.getPhoneNo())) {
            queryWrapper.like(SysUser::getPhoneNo, request.getPhoneNo());
        }
        if (StringUtils.isNotBlank(request.getEmail())) {
            queryWrapper.like(SysUser::getEmail, request.getNickName());
        }
        return this.page(new Page<>(request.getCurrent(), request.getSize()), queryWrapper);
    }

}
