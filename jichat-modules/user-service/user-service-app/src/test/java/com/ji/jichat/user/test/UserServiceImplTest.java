//package com.ji.jichat.user.test;
//
//import com.ji.jichat.user.api.dto.AuthLoginDTO;
//import com.ji.jichat.user.api.vo.AuthLoginVO;
//import com.ji.jichat.user.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Transactional
//class UserServiceImplTest {
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Test
//    void testLogin_Success() {
//        // 构造测试数据
//        AuthLoginDTO authLoginDTO = new AuthLoginDTO();
//        authLoginDTO.setUsername("jisl");
//        authLoginDTO.setPassword("12345678");
//
//        // 执行测试
//        AuthLoginVO authLoginVO = userService.login(authLoginDTO);
//        // 验证结果
//        assertNotNull(authLoginVO);
//        // 可以添加更多的验证逻辑
//    }
//
//    // 其他测试方法类似...
//}
