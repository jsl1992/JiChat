package com.ji.jichat.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.ji.jichat.excel.util.dto.ExcelImportResult;
import com.ji.jichat.excel.util.listener.ExcelListener;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel工具
 *
 * @author jisl on 2024/11/12 15:12
 **/
@Slf4j
public class EasyExcelUtils {

    /**
     * 读取excel
     *
     * @param file                       导入文件
     * @param clazz                      返回类型
     * @param notAllowedRepeatProperties 不允许导入数据重复的字段(仅支持基本类型和String类型)
     * @return java.util.List<org.apache.poi.ss.formula.functions.T>
     * @author jishenglong on 2019/11/8 11:08
     **/
    public static <T> ExcelImportResult readExcel(MultipartFile file, @NonNull Class<T> clazz, String... notAllowedRepeatProperties) {
        final long startTimeMillis = System.currentTimeMillis();
        ExcelListener excelListener = new ExcelListener(new ExcelImportResult(), notAllowedRepeatProperties);
        try {
            EasyExcel.read(file.getInputStream(), clazz, excelListener).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException("导入excel错误");
        }
        log.info("EasyExcelUtils导入用时:{}ms", System.currentTimeMillis() - startTimeMillis);
        return excelListener.getResult();
    }

    /**
     * 导出
     *
     * @param response HttpServletResponse
     * @param filename 文件名
     * @param data     数据
     * @author jisl on 2024/11/12 15:11
     **/
    public static <T> void download(HttpServletResponse response, String filename, List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        download(response, (Class<T>) data.get(0).getClass(), filename, data);
    }

    public static <T> void download(HttpServletResponse response, @NonNull Class<T> head, String filename, List<T> data) {
        try {
            if (CollectionUtils.isEmpty(data)) {
                return;
            }
            String fileName = URLEncoder.encode(filename, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), head)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .autoCloseStream(Boolean.FALSE)
                    .sheet()
                    .doWrite(data);

            log.debug("[{}]导出[{}]条数据", filename, data.size());
        } catch (Exception e) {
            errorHandler(response, filename, e);
        }
    }


    private static void errorHandler(HttpServletResponse response, String filename, Exception e) {
        log.error("[{}]导出Excel错误,{}", filename, e.getMessage());

        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        throw new RuntimeException(e);
    }

}
