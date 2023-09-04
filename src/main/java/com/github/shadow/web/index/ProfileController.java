package com.github.shadow.web.index;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.github.shadow.entity.SysUser;
import com.github.shadow.pojo.R;
import com.github.shadow.service.ISysUserService;
import com.github.shadow.util.ShiroUtils;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private ISysUserService sysUserService;

    @GetMapping
    public String profile(ModelMap modelMap) {
        Integer userId = ShiroUtils.getCurrentUser();
        modelMap.put("user", sysUserService.getById(userId));
        return "/profile";
    }

    @PostMapping
    @ResponseBody
    public R updateProfile(@RequestBody SysUser user) {
        if (StringUtils.isBlank(user.getAvatar())) {
            user.setAvatar(null);
        }
        return R.status(sysUserService.updateById(user));
    }

}
