package com.github.shadow.service.impl;

import com.github.shadow.entity.UserData;
import com.github.shadow.mapper.UserDataMapper;
import com.github.shadow.service.IUserDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author xuwen
 * @since 2023-07-12
 */
@Service
public class UserDataServiceImpl extends ServiceImpl<UserDataMapper, UserData> implements IUserDataService {

}
