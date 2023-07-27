package com.github.shadow.web.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.shadow.util.Jexl3Utils;
import com.github.shadow.web.request.EvaluateRequest;
import com.github.shadow.web.response.Result;

import io.swagger.annotations.Api;

@Api(value = "通用工具", tags = "通用工具接口")
@RestController
@RequestMapping("/tool")
public class ToolController {

    @PostMapping("/math/evaluate")
    public Result<Object> evaluate(@RequestBody EvaluateRequest request) {
        Map<String, Object> source = request.getSource();
        String expression = request.getExpression();
        Jexl3Utils.checkExpression(new ArrayList<>(source.keySet()), expression);
        return Result.success(Jexl3Utils.mathEvaluate(source, expression));
    }

}
