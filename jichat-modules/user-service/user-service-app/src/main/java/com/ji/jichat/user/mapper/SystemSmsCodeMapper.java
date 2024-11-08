package com.ji.jichat.user.mapper;

import com.ji.jichat.user.entity.SystemSmsCode;
import com.ji.jichat.user.api.dto.SystemSmsCodePageDTO;
import com.ji.jichat.user.api.vo.SystemSmsCodeVO;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 手机验证码 Mapper 接口
 * </p>
 *
 * @author jisl
 * @since 2024-11-08
 */
public interface SystemSmsCodeMapper extends BaseMapper<SystemSmsCode> {


    /**
    * 分页查询手机验证码
    *
    * @param systemSmsCodePageDTO 分页DTO
    * @return com.ji.jichat.user.vo.SystemSmsCodeVO
    */
    List<SystemSmsCodeVO> page(SystemSmsCodePageDTO systemSmsCodePageDTO);


    }
