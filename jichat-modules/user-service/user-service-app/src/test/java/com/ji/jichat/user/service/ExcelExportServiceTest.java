package com.ji.jichat.user.service;

import com.ji.jichat.excel.util.service.ExcelExportService;
import com.ji.jichat.user.api.dto.SystemSmsCodePageDTO;
import com.ji.jichat.user.excel.SystemSmsCodeExcelVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExcelExportServiceTest {


    @Autowired
    private ISystemSmsCodeService systemSmsCodeService;

//    @Autowired
//    private ExcelExportService excelExportService;

    @Test
    public void exportDataToExcel() {
        ExcelExportService excelExportService=new ExcelExportService();
        final SystemSmsCodePageDTO dto = SystemSmsCodePageDTO.builder().build();
        excelExportService.exportToExcel("C:\\Newand\\file\\导出测试.xlsx", () -> systemSmsCodeService.page(dto), SystemSmsCodeExcelVO.class);
    }


}