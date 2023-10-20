package com.github.shadow.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongFunction;

import org.apache.poi.ss.SpreadsheetVersion;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

public class ExcelWriterUtil {

    public static final long PAGE_SIZE = 10000L;
    public static final int EXCEL_SHEET_ROW_MAX_SIZE = 50001; // excel sheet最大行数(算标题)
    private static final int DEF_PARALLEL_NUM = Math.min(Runtime.getRuntime().availableProcessors(), 4);

    private ExcelWriterUtil() {}

    /**
     * 顺序写入Excel
     */
    public static <T> void pageExcelWriterOrder(ExcelWriter excelWriter, long totalPage,
        LongFunction<List<T>> pageListFunction) {
        if (excelWriter != null) {
            // 如果无待写入的数据则写入标题
            if (totalPage <= 0) {
                excelWriter.write(Collections.emptyList(), EasyExcel.writerSheet().build());
                return;
            }
            AtomicLong count = new AtomicLong();
            WriteSheet writeSheet = EasyExcel.writerSheet(1, "Sheet1").build();
            for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
                List<T> pageList = pageListFunction.apply(pageNo);
                writeSheet.setSheetNo((int)(count.addAndGet(pageList.size()) / EXCEL_SHEET_ROW_MAX_SIZE + 1));
                writeSheet.setSheetName("Sheet" + writeSheet.getSheetNo());
                // excel写入数据
                excelWriter.write(pageList, writeSheet);
            }
        }
    }

    /**
     * 写入Excel（并发查询数据，有序串行写入数据）
     */
    public static <T> void pageExcelWriterParallel(ExcelWriter excelWriter, long totalPage,
        LongFunction<List<T>> pageListFunction) throws InterruptedException {
        if (excelWriter != null) {
            // 如果无待写入的数据则写入标题
            if (totalPage <= 0) {
                excelWriter.write(Collections.emptyList(), EasyExcel.writerSheet().build());
                return;
            }
            AtomicLong count = new AtomicLong();
            WriteSheet writeSheet = EasyExcel.writerSheet(1, "Sheet1").build();
            ParallelUtil.parallel(List.class, DEF_PARALLEL_NUM, totalPage).asyncProducer(pageListFunction::apply)
                .syncConsumer(pageList -> {
                    writeSheet.setSheetNo((int)(count.addAndGet(pageList.size()) / EXCEL_SHEET_ROW_MAX_SIZE + 1));
                    writeSheet.setSheetName("Sheet" + writeSheet.getSheetNo());
                    excelWriter.write(pageList, writeSheet);
                }).start();
        }
    }

    /**
     * 写入Excel[写入速度可能更快]（并发查询数据，有序串行写入数据）
     */
    public static <T> void pageExcelWriterSliding(ExcelWriter excelWriter, long totalPage,
        LongFunction<List<T>> pageListFunction) throws InterruptedException, ExecutionException {
        if (excelWriter != null) {
            // 如果无待写入的数据则写入标题
            if (totalPage <= 0) {
                excelWriter.write(Collections.emptyList(), EasyExcel.writerSheet().build());
                return;
            }
            AtomicLong count = new AtomicLong();
            WriteSheet writeSheet = EasyExcel.writerSheet(1, "Sheet1").build();
            SlidingWindow.create(List.class, DEF_PARALLEL_NUM, totalPage).sendWindow(pageListFunction::apply)
                .receiveWindow(result -> {
                    writeSheet.setSheetNo((int)(count.addAndGet(result.size()) / EXCEL_SHEET_ROW_MAX_SIZE + 1));
                    writeSheet.setSheetName("Sheet" + writeSheet.getSheetNo());
                    excelWriter.write(result, writeSheet);
                }).start();
        }
    }

    public static void initCellMaxTextLength() {
        SpreadsheetVersion excel2007 = SpreadsheetVersion.EXCEL2007;
        if (Integer.MAX_VALUE != excel2007.getMaxTextLength()) {
            Field field;
            try {
                field = excel2007.getClass().getDeclaredField("_maxTextLength");
                field.setAccessible(true);
                field.set(excel2007, Integer.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
