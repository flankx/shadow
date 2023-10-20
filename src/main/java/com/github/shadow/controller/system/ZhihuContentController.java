package com.github.shadow.controller.system;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.shadow.entity.ZhihuContent;
import com.github.shadow.pojo.R;
import com.github.shadow.request.ExportRequest;
import com.github.shadow.request.PageRequest;
import com.github.shadow.service.IExcelWriterService;
import com.github.shadow.service.IZhihuContentService;
import com.github.shadow.spider.ContentPageProcesser;
import com.github.shadow.spider.ContentPipeline;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import us.codecraft.webmagic.Spider;

/**
 * <p>
 * 内容 前端控制器
 * </p>
 *
 * @author xuwen
 * @since 2023-09-20
 */
@Controller
@RequestMapping("/content")
@AllArgsConstructor
@Api(value = "知乎问答", tags = "知乎问答接口")
public class ZhihuContentController {

    private IZhihuContentService contentService;
    private IExcelWriterService excelWriterService;

    @ApiIgnore
    @GetMapping
    public String content() {
        return "content";
    }

    @GetMapping("/runSpider")
    @ApiOperation(value = "抓取知乎内容")
    @ResponseBody
    public R runSpider() {
        Spider.create(new ContentPageProcesser()).addUrl("https://www.zhihu.com/explore")
            .addPipeline(new ContentPipeline(contentService)).setEmptySleepTime(1000).thread(8).start();
        return R.status(true);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @ResponseBody
    public R<IPage<ZhihuContent>> page(PageRequest request) {
        return R.data(contentService.page(new Page<>(request.getCurrent(), request.getSize())));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove")
    @ResponseBody
    public R remove(@RequestParam Long id) {
        return R.status(contentService.removeById(id));
    }

    @PostMapping(value = "export-excel")
    @ApiOperation(value = "导出EXCEL")
    public void exportAlarmHistoryV2(HttpServletResponse response, @RequestBody ExportRequest request) {
        // 按照默认最大分页数顺序写入EXCEL
        excelWriterService.writeExcel(request, response, ZhihuContent.class);
    }

}
