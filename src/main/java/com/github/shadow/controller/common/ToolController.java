package com.github.shadow.controller.common;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.shadow.pojo.R;
import com.github.shadow.request.EvaluateRequest;
import com.github.shadow.util.Jexl3Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "通用工具", tags = "通用工具接口")
@RestController
@RequestMapping("/api/tool")
public class ToolController {

    @PostMapping("/math/evaluate")
    @ApiOperation(value = "函数计算", notes = "传入请求参数")
    public R evaluate(@RequestBody EvaluateRequest request) {
        Map<String, Object> source = request.getSource();
        String expression = request.getExpression();
        Jexl3Utils.checkExpression(new ArrayList<>(source.keySet()), expression);
        return R.data(Jexl3Utils.mathEvaluate(source, expression));
    }

}
