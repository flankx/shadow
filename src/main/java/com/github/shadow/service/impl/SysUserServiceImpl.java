package com.github.shadow.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shadow.entity.SysUser;
import com.github.shadow.exception.BizException;
import com.github.shadow.mapper.SysUserMapper;
import com.github.shadow.request.UserPageRequest;
import com.github.shadow.service.ISysUserService;
import com.github.shadow.util.ShiroUtils;

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
    @Cacheable(value = "sys-userCache", key = "#userId", condition = "#userId != null")
    public SysUser getUserById(Integer userId) {
        // 过滤密码字段
        List<String> excludeFields = Arrays.asList("password");
        return baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
            .select(SysUser.class, i -> !excludeFields.contains(i.getProperty())).eq(SysUser::getId, userId));
    }

    @Override
    public SysUser getAvatarById(Integer userId) {
        return baseMapper.selectOne(
            Wrappers.<SysUser>lambdaQuery().select(SysUser::getId, SysUser::getAvatar).eq(SysUser::getId, userId));
    }

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

    @Override
    public boolean submit(SysUser user) {
        String salt = ShiroUtils.randomSalt();
        // 生成默认密码
        SimpleHash hash = new SimpleHash("MD5", "123456", salt);
        user.setSalt(salt);
        user.setPassword(hash.toHex());
        if (user.getId() == null) {
            // 新增
            Long cnt =
                baseMapper.selectCount(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, user.getUserName()));
            if (cnt >= 1) {
                throw new BizException("重复的用户名！" + user.getUserName());
            }
        } else {
            // 编辑
            SysUser oldUser = baseMapper.selectById(user.getId());
            if (oldUser == null) {
                throw new BizException("用户不存在！" + user.getUserName());
            }
            if (!oldUser.getUserName().equals(user.getUserName())) {
                Long cnt = baseMapper
                    .selectCount(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, user.getUserName()));
                if (cnt >= 1) {
                    throw new BizException("重复的用户名！" + user.getUserName());
                }
            }
        }
        return saveOrUpdate(user);
    }

}
