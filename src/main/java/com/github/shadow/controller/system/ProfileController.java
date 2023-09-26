package com.github.shadow.controller.system;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.shadow.dto.EditPassDTO;
import com.github.shadow.entity.SysUser;
import com.github.shadow.pojo.R;
import com.github.shadow.service.ISysUserService;
import com.github.shadow.util.ShiroUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
@Api(value = "个人资料", tags = "个人资料接口")
public class ProfileController {

    private ISysUserService sysUserService;

    @ApiIgnore
    @GetMapping
    public String profile(ModelMap modelMap) {
        modelMap.put("user", sysUserService.getUserById(ShiroUtils.getCurrentUser()));
        return "profile";
    }

    @ApiOperation(value = "更新个人资料")
    @PostMapping
    @ResponseBody
    @CacheEvict(value = "sys-userCache", key = "#user.id", condition = "#user.id != null")
    public R updateProfile(@RequestBody SysUser user) {
        if (StringUtils.isBlank(user.getAvatar())) {
            user.setAvatar(null);
        }
        return R.status(sysUserService.updateById(user));
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/editPassword")
    @ResponseBody
    public R updatePassword(@RequestBody EditPassDTO editPass) {
        Integer userId = ShiroUtils.getCurrentUser();
        SysUser user = sysUserService.getOne(
            Wrappers.<SysUser>lambdaQuery().select(SysUser::getId, SysUser::getPassword).eq(SysUser::getId, userId));
        if (user == null) {
            return R.fail("用户不存在！");
        }
        String oldPass = new Md5Hash(editPass.getOldPassword(), user.getSalt(), 2).toHex();
        String newPass = new Md5Hash(editPass.getPassword(), user.getSalt(), 2).toHex();
        if (!user.getPassword().equals(oldPass)) {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (user.getPassword().equals(newPass)) {
            return R.fail("新密码不能与旧密码相同");
        }
        user.setPassword(newPass);
        user.setUpdateTime(new Date());
        return R.status(sysUserService.updateById(user));
    }

}
