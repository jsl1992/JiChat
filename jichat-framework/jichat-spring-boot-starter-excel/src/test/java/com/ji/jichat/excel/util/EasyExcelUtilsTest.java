package com.ji.jichat.excel.util;

import com.alibaba.fastjson2.JSON;
import com.ji.jichat.excel.util.dto.ExcelImportResult;
import com.ji.jichat.excel.util.dto.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Excel工具
 *
 * @author jisl on 2024/11/12 15:12
 **/
@Slf4j
public class EasyExcelUtilsTest {


    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\纪大侠\\Desktop\\导入测试.xlsx";
        Path path = Paths.get(filePath);
        // 使用 Files.newInputStream() 读取文件内容
        final InputStream inputStream = Files.newInputStream(path);
        MultipartFile file = new MockMultipartFile("ji", inputStream);
        final ExcelImportResult<TestDTO> excelImportResult = EasyExcelUtils.readExcel(file, TestDTO.class);
        final List<TestDTO> successList = excelImportResult.getSuccessList();
        System.out.println(JSON.toJSONString(successList));
    }


}
