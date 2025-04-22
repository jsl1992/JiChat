package com.ji.jichat.excel.util.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ExcelExportService<T> {

    /**
     * 每次查询的默认分页大小
     **/
    private static final int DEFAULT_PAGE_SIZE = 5000;

    /**
     * 导出excel
     *
     * @param fileName       导出文件名
     * @param query          导出的查询方法
     * @param dataModelClass 导出返回实体类
     * @author jisl on 2025/2/6 14:58
     **/
    public void exportToExcel(String fileName, ISelect query, Class<T> dataModelClass) {
        exportToExcel(null, fileName, query, dataModelClass);
    }


    public void exportToExcel(HttpServletResponse response, String fileName, ISelect query, Class<T> dataModelClass) {

        // 获取总记录数
        final StopWatch stopWatch = new StopWatch("导出开始:" + fileName);
        stopWatch.start("总数查询");
        final long totalRecords = JiPageHelper.doSelectPageInfo(new PageDTO(1, 10), query).getTotal();
        final long totalPages = (totalRecords + DEFAULT_PAGE_SIZE - 1) / DEFAULT_PAGE_SIZE;
        log.info("导出totalRecords=%s,totalPages=%s", totalRecords, totalPages);
        stopWatch.stop();
        stopWatch.start("分页导出");
        // 创建 ExcelWriter 对象
        final ExcelWriter excelWriter = getExcelWriter(response, fileName);
        // 分页查询并写入数据
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            // 分页查询数据
            final PageVO<T> pageVO = JiPageHelper.doSelectPageInfo(new PageDTO(currentPage, DEFAULT_PAGE_SIZE), query);
            // 写入数据到当前页的Sheet
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet " + currentPage).head(dataModelClass).build();
            final List<T> list = BeanUtil.copyToList(pageVO.getList(), dataModelClass);
            excelWriter.write(list, writeSheet);
        }
        // 完成写入
        excelWriter.finish();
        stopWatch.stop();
        log.info("导出用时:" + stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @SneakyThrows
    private ExcelWriter getExcelWriter(HttpServletResponse response, String fileName) {
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
