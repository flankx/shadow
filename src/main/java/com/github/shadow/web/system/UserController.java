package com.github.shadow.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.shadow.entity.SysUser;
import com.github.shadow.pojo.R;
import com.github.shadow.request.UserPageRequest;
import com.github.shadow.service.ISysUserService;
import com.github.shadow.util.JsonUtils;
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
        SysUser user = sysUserService.getOne(
            Wrappers.<SysUser>lambdaQuery().select(SysUser::getAvatar).eq(SysUser::getId, ShiroUtils.getCurrentUser()));
        return user.getAvatar();
    }

    @ApiOperation(value = "上传头像")
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public R uploadAvatar(MultipartFile file) {
        SysUser user = sysUserService.getOne(
            Wrappers.<SysUser>lambdaQuery().select(SysUser::getAvatar).eq(SysUser::getId, ShiroUtils.getCurrentUser()));
        // TODO
        return R.status(true);
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/detail")
    @ResponseBody
    public R<SysUser> detail(Integer userId) {
        if (userId == null) {
            return R.fail("用户不存在！");
        }
        return R.data(sysUserService.getById(userId));
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @ResponseBody
    public R<IPage<SysUser>> page(UserPageRequest request) {
        log.info(JsonUtils.toJson(request));
        return R.data(sysUserService.userPage(request));
    }

}
