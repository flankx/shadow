package com.github.shadow.web.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.shadow.pojo.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "系统信息", tags = "系统信息接口")
@RestController
@RequestMapping("/api/system")
public class SystemInfoController {

    @GetMapping("info")
    @ApiOperation(value = "信息")
    public R info() {
        return R.status(true);
    }

}
