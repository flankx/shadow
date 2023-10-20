package com.github.shadow.service;

import javax.servlet.http.HttpServletResponse;

import com.github.shadow.request.ExportRequest;

/**
 * @author xujianrong
 * @date 2023/10/16
 */
public interface IExcelWriterService {
    /**
     * 写入Excel
     *
     * @param <T> the type parameter
     * @param request the request
     * @param response the response
     * @param headClass the head class
     */
    <T> void writeExcel(ExportRequest request, HttpServletResponse response, Class<T> headClass);

}
