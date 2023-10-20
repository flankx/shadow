package com.github.shadow.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.LongFunction;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.shadow.entity.ZhihuContent;
import com.github.shadow.request.ExportRequest;
import com.github.shadow.service.IExcelWriterService;
import com.github.shadow.service.IZhihuContentService;
import com.github.shadow.util.ExcelWriterUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xujianrong
 * @date 2023/10/16
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExcelWriterServiceImpl implements IExcelWriterService {

    private IZhihuContentService contentService;

    @Override
    public <T> void writeExcel(ExportRequest request, HttpServletResponse response, Class<T> headClass) {

        try (ServletOutputStream os = response.getOutputStream()) {
            // 设置响应体headers
            String fileName = String.format("EXPORT_%s", UUID.randomUUID());
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 构建查询条件
            LambdaQueryWrapper<ZhihuContent> queryWrapper = Wrappers.lambdaQuery();
            // 查询报警发生时间区间
            String timeStart = request.getTimeStart();
            String timeEnd = request.getTimeEnd();
            if (StringUtils.isNoneBlank(timeStart, timeEnd)) {
                Date timeStartDate = DateUtils.parseDate(timeStart, "yyyy-MM-dd HH:mm:ss");
                Date timeEndDate = DateUtils.parseDate(timeEnd, "yyyy-MM-dd HH:mm:ss");
                // 检查时间区间是否正确
                if (timeStartDate.after(timeEndDate)) {
                    log.error("查询发生开始时间不能为结束时间之后的时间!");
                    return;
                }
                queryWrapper.between(ZhihuContent::getCreateTime, timeStartDate, timeEndDate);
            }

            // 查询总条数，通过设置页大小而获取总页数
            IPage<ZhihuContent> countPage =
                contentService.page(new Page<>(1, 0), queryWrapper).setSize(ExcelWriterUtil.PAGE_SIZE);

            LongFunction<List<ZhihuContent>> function = pageNo -> {
                // 更新分页参数
                Page<ZhihuContent> funcPage =
                    new Page<>(pageNo, ExcelWriterUtil.PAGE_SIZE, countPage.getTotal(), false);
                funcPage.setMaxLimit(ExcelWriterUtil.PAGE_SIZE);
                return contentService.page(funcPage, queryWrapper).getRecords();
            };

            // 修改cell的最大值
            ExcelWriterUtil.initCellMaxTextLength();

            ExcelWriter excelWriter =
                EasyExcel.write(os, headClass).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
            try {
                switch (request.getExportType()) {
                    case "EXCEL-1":
                        ExcelWriterUtil.pageExcelWriterOrder(excelWriter, countPage.getPages(), function);
                        break;
                    case "EXCEL-2":
                        ExcelWriterUtil.pageExcelWriterParallel(excelWriter, countPage.getPages(), function);
                        break;
                    case "EXCEL-3":
                        ExcelWriterUtil.pageExcelWriterSliding(excelWriter, countPage.getPages(), function);
                        break;
                    default:
                        break;
                }
            } finally {
                // 关闭写入流
                if (excelWriter != null) {
                    excelWriter.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("excel export error!");
        }
    }
}
