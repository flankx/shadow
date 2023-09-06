package com.github.shadow.web.system;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.github.shadow.dto.EditPassDTO;
import com.github.shadow.entity.SysUser;
import com.github.shadow.pojo.R;
import com.github.shadow.service.ISysUserService;
import com.github.shadow.util.ShiroUtils;

import io.swagger.annotations.Api;
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
        Integer userId = ShiroUtils.getCurrentUser();
        modelMap.put("user", sysUserService.getById(userId));
        return "/profile";
    }

    @ApiOperation(value = "更新个人资料")
    @PostMapping
    @ResponseBody
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
        SysUser user = sysUserService.getById(userId);
        if (!user.getPassword().equals(editPass.getOldPassword())) {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (user.getPassword().equals(editPass.getPassword())) {
            return R.fail("新密码不能与旧密码相同");
        }
        user.setPassword(editPass.getPassword());
        return R.status(sysUserService.updateById(user));
    }

}
