package com.ji.jichat.excel.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.StopWatch;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.ISelect;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.mybatis.util.JiPageHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 每次查询的默认分页大小
     **/
    private static final int DEFAULT_PAGE_SIZE = 5000;

    private ExcelUtil() {
        // 私有构造方法，防止实例化
    }

    /**
     * 导出excel（无 response，直接生成文件）
     *
     * @param fileName       导出文件名
     * @param query          导出的查询方法
     * @param dataModelClass 导出返回实体类
     */
    public static <T> void exportToExcel(String fileName, ISelect query, Class<T> dataModelClass) {
        exportToExcel(null, fileName, query, dataModelClass);
    }

    /**
     * 导出excel（支持 HttpServletResponse 输出）
     *
     * @param response       响应体，可空
     * @param fileName       导出文件名
     * @param query          导出的查询方法
     * @param dataModelClass 导出返回实体类
     */
    public static <T> void exportToExcel(HttpServletResponse response, String fileName, ISelect query, Class<T> dataModelClass) {

        StopWatch stopWatch = new StopWatch("导出开始: " + fileName);
        stopWatch.start("总数查询");

        final long totalRecords = JiPageHelper.doSelectPageInfo(new PageDTO(1, 10), query).getTotal();
        final long totalPages = (totalRecords + DEFAULT_PAGE_SIZE - 1) / DEFAULT_PAGE_SIZE;

        log.info("导出 totalRecords={}, totalPages={}", totalRecords, totalPages);

        stopWatch.stop();
        stopWatch.start("分页导出");

        ExcelWriter excelWriter = getExcelWriter(response, fileName);

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            PageVO<T> pageVO = JiPageHelper.doSelectPageInfo(new PageDTO(currentPage, DEFAULT_PAGE_SIZE), query);
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet " + currentPage).head(dataModelClass).build();
            excelWriter.write(BeanUtil.copyToList(pageVO.getList(), dataModelClass), writeSheet);
        }

        excelWriter.finish();
        stopWatch.stop();

        log.info("导出用时: {}", stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @SneakyThrows
    private static ExcelWriter getExcelWriter(HttpServletResponse response, String fileName) {
        if (Objects.nonNull(response)) {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            return EasyExcel.write(response.getOutputStream()).build();
        } else {
            return EasyExcel.write(fileName).build();
        }
    }
}
