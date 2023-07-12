package com.github.shadow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author xujianrong@leateckgroup.com
 * @date 2023/7/12
 */
@Api(value = "系统信息", tags = "系统信息接口")
@RestController
@RequestMapping("/system")
public class SystemInfoController {

    @GetMapping("info")
    public Object info() {
        return null;
    }

}
