package com.ji.jichat.user.service;

import com.ji.jichat.user.entity.SystemSmsCode;
import com.ji.jichat.user.api.dto.SystemSmsCodeDTO;
import com.ji.jichat.user.api.dto.SystemSmsCodePageDTO;
import com.ji.jichat.user.api.vo.SystemSmsCodeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 手机验证码 服务类
 * </p>
 *
 * @author jisl
 * @since 2024-11-08
 */
public interface ISystemSmsCodeService extends IService<SystemSmsCode> {

    /**
    * 分页查询手机验证码
    *
    * @param systemSmsCodePageDTO 分页DTO
    * @return com.ji.jichat.user.vo.SystemSmsCodeVO
    */
    List<SystemSmsCodeVO> page(SystemSmsCodePageDTO systemSmsCodePageDTO);

    /**
    * 列表查询手机验证码
    *
    * @param systemSmsCodeDTO 列表查询DTO
    * @return com.ji.jichat.user.vo.SystemSmsCodeVO
    */
    List<SystemSmsCode> list(SystemSmsCodeDTO systemSmsCodeDTO);

    /**
    * 获取手机验证码详情
    *
    * @param id 主键
    * @return com.ji.jichat.user.vo.SystemSmsCodeVO
    */
    SystemSmsCodeVO getDetail(Long id);


    /**
    * 新增手机验证码
    *
    * @param systemSmsCodeDTO 新增DTO
    */
    void add(SystemSmsCodeDTO systemSmsCodeDTO);

    /**
    * 修改手机验证码
    *
    * @param systemSmsCodeDTO 修改DTO
    */
    void update(SystemSmsCodeDTO systemSmsCodeDTO);

    /**
    * 删除手机验证码信息
    *
    * @param  id 主键
    */
    void delete(Long id);
}
