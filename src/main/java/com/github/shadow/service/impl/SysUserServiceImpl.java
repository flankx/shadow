package com.github.shadow.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shadow.entity.SysUser;
import com.github.shadow.mapper.SysUserMapper;
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

}
