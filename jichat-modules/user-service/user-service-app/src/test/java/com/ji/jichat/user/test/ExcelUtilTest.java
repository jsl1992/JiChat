package com.ji.jichat.user.test;

import cn.hutool.core.util.RandomUtil;
import com.ji.jichat.excel.util.ExcelUtil;
import com.ji.jichat.user.api.dto.SystemSmsCodeDTO;
import com.ji.jichat.user.api.dto.SystemSmsCodePageDTO;
import com.ji.jichat.user.excel.SystemSmsCodeExcelVO;
import com.ji.jichat.user.service.ISystemSmsCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class ExcelUtilTest {


    @Autowired
    private ISystemSmsCodeService systemSmsCodeService;


    @Test
    public void exportDataToExcel() {
        final SystemSmsCodePageDTO dto = SystemSmsCodePageDTO.builder().build();
        ExcelUtil.exportToExcel("C:\\Newand\\file\\导出测试.xlsx", () -> systemSmsCodeService.page(dto), SystemSmsCodeExcelVO.class);
    }


    @Test
    public void add() {
        for (int i = 0; i < 100; i++) {
            final SystemSmsCodeDTO dto = SystemSmsCodeDTO.builder()
                    .mobile("1" + RandomUtil.randomNumbers(10)).code(RandomUtil.randomNumbers(6))
                    .createIp("192.168.137.88").scene(RandomUtil.randomInt(6)).todayIndex(1)
                    .used(1).usedTime(new Date()).usedIp("192.168.137.66")
                    .build();
            systemSmsCodeService.add(dto);
        }
    }


}