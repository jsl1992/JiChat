package com.ji.jichat.user.test;

import cn.hutool.core.util.RandomUtil;
import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.dto.UserRegisterDTO;
import com.ji.jichat.user.api.dto.UserRelationDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import com.ji.jichat.user.entity.User;
import com.ji.jichat.user.service.IUserRelationService;
import com.ji.jichat.user.service.IUserService;
import com.ji.jichat.user.service.impl.UserServiceImpl;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRelationService userRelationService;


    private static final Faker fakerChina = new Faker(new Locale("zh-CN"));
    private static final Faker faker = new Faker();

    @Test
    void testLogin_Success() {
        // 构造测试数据
        AuthLoginDTO authLoginDTO = new AuthLoginDTO();
        authLoginDTO.setUsername("jisl");
        authLoginDTO.setPassword("12345678");

        // 执行测试
        AuthLoginVO authLoginVO = userService.login(authLoginDTO);
        // 验证结果
        assertNotNull(authLoginVO);
        // 可以添加更多的验证逻辑
    }

    @Test
    @Rollback(false)
        // 禁用回滚
    void testRegister() {
        // 构造测试数据
        for (int i = 0; i < 100; i++) {
            final UserRegisterDTO registerDTO = UserRegisterDTO.builder()
                    .username(faker.name().firstName())
                    .nickname(fakerChina.name().name()).password("12345678").mobile("180" + RandomUtil.randomNumbers(8))
                    .build();
            // 执行测试
            try {
                userService.register(registerDTO);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(registerDTO);
        }
    }

    @Test
    @Rollback(false)
        // 禁用回滚
    void testAddFriend() {
        final List<User> list = userService.list();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                try {
                    final int randomInt = RandomUtil.randomInt(100);
                    if (randomInt<60){
                        userRelationService.addFriend(UserRelationDTO.builder().userId(list.get(i).getId()).relationId(list.get(j).getId()).build());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
