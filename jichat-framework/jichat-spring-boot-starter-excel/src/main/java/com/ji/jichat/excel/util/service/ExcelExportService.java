package com.ji.jichat.excel.util.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.ISelect;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.mybatis.util.JiPageHelper;
import org.springframework.stereotype.Service;

@Service
public class ExcelExportService<T> {

    // 每次查询的默认分页大小
    private static final int DEFAULT_PAGE_SIZE = 2;

    // 通用的导出方法
    public void exportToExcel(String fileName, ISelect query, Class<T> dataModelClass) {
        // 获取总记录数
        final long totalRecords = JiPageHelper.doSelectPageInfo(new PageDTO(1, DEFAULT_PAGE_SIZE), query).getTotal();
        final long totalPages = (totalRecords + DEFAULT_PAGE_SIZE - 1) / DEFAULT_PAGE_SIZE;
        // 创建 ExcelWriter 对象
        final ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        // 分页查询并写入数据
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            // 分页查询数据
            final PageVO<T> pageVO = JiPageHelper.doSelectPageInfo(new PageDTO(currentPage, DEFAULT_PAGE_SIZE), query);
            // 写入数据到当前页的Sheet
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet " + currentPage).head(dataModelClass).build();
            excelWriter.write(pageVO.getList(), writeSheet);
        }
        // 完成写入
        excelWriter.finish();
    }
}
