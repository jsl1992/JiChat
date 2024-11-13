package com.ji.jichat.excel.util;

import com.alibaba.fastjson2.JSON;
import com.ji.jichat.excel.util.dto.ExcelImportResult;
import com.ji.jichat.excel.util.dto.TestImportDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Excel工具
 *
 * @author jisl on 2024/11/12 15:12
 **/
@Slf4j
public class EasyExcelUtilsTest {

    @Test
    public void testDownload_withData() throws Exception {
        // Mock HttpServletResponse 和 OutputStream
        HttpServletResponse response = mock(HttpServletResponse.class);
        final List<TestImportDTO> testImportDTOS = importData();
        // 测试有数据时，应该生成并写入输出流
        // 调用 download 方法
        EasyExcelUtils.download(response, "testFile.csv", testImportDTOS);
    }

    @Test
    public void importDataTest() throws IOException {
        importData();
    }

    public List<TestImportDTO> importData() throws IOException {
        String filePath = "C:\\Users\\纪大侠\\Desktop\\导入测试.xlsx";
        Path path = Paths.get(filePath);
        // 使用 Files.newInputStream() 读取文件内容
        final InputStream inputStream = Files.newInputStream(path);
        MultipartFile file = new MockMultipartFile("ji", inputStream);
        final ExcelImportResult excelImportResult = EasyExcelUtils.readExcel(file, TestImportDTO.class);
        final List<TestImportDTO> successList = excelImportResult.getSuccessList();
        System.out.println(JSON.toJSONString(successList));
        return successList;
    }


}
