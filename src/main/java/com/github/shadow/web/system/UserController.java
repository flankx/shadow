package com.github.shadow.web.system;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.shadow.entity.SysUser;
import com.github.shadow.pojo.R;
import com.github.shadow.request.UserPageRequest;
import com.github.shadow.service.ISysUserService;
import com.github.shadow.util.ShiroUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "用户信息", tags = "用户信息接口")
public class UserController {

    private ISysUserService sysUserService;

    @ApiIgnore
    @GetMapping
    public String user() {
        return "/user";
    }

    @ApiOperation(value = "预览头像")
    @GetMapping("/avatar")
    @ResponseBody
    public String avatar() {
        SysUser user = sysUserService.getAvatarById(ShiroUtils.getCurrentUser());
        return user != null ? user.getAvatar() : null;
    }

    @ApiOperation(value = "上传头像")
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public R uploadAvatar(MultipartFile file) {
        SysUser user = sysUserService.getAvatarById(ShiroUtils.getCurrentUser());
        if (user == null) {
            return R.fail("用户不存在或者未登录！");
        }
        if (file == null || file.isEmpty()) {
            return R.fail("图片不能为空！");
        }
        try {
            String formatStr = String.format("data:%s;base64,%s", file.getContentType(),
                Base64.getEncoder().encodeToString(file.getBytes()));
            user.setAvatar(formatStr);
            user.setUpdateTime(new Date());
            return R.status(sysUserService.updateById(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.status(false);
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/detail")
    @ResponseBody
    public R<SysUser> detail(@RequestParam Integer userId) {
        return R.data(sysUserService.getById(userId));
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @ResponseBody
    public R<IPage<SysUser>> page(UserPageRequest request) {
        return R.data(sysUserService.userPage(request));
    }

    @ApiOperation(value = "用户删除")
    @DeleteMapping("/remove")
    @ResponseBody
    @CacheEvict(value = "sys-userCache", key = "#userId")
    public R remove(@RequestParam Integer userId) {
        return R.status(sysUserService.removeById(userId));
    }

}
