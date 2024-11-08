package com.ji.jichat.user.service.impl;

import com.ji.jichat.user.entity.SystemSmsCode;
import com.ji.jichat.user.api.dto.SystemSmsCodeDTO;
import com.ji.jichat.user.api.dto.SystemSmsCodePageDTO;
import com.ji.jichat.user.api.vo.SystemSmsCodeVO;
import com.ji.jichat.user.convert.SystemSmsCodeConvert;
import com.ji.jichat.user.mapper.SystemSmsCodeMapper;
import com.ji.jichat.user.service.ISystemSmsCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
/**
 * <p>
 * 手机验证码 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2024-11-08
 */
@Service
public class SystemSmsCodeServiceImpl extends ServiceImpl<SystemSmsCodeMapper, SystemSmsCode> implements ISystemSmsCodeService {

    @Override
    public List<SystemSmsCodeVO> page(SystemSmsCodePageDTO systemSmsCodePageDTO) {
        return baseMapper.page(systemSmsCodePageDTO);
    }

    @Override
    public List<SystemSmsCode> list(SystemSmsCodeDTO dto) {
        return list(new LambdaQueryWrapper<>(SystemSmsCodeConvert.INSTANCE.convert(dto)));
    }

   @Override
   public SystemSmsCodeVO getDetail(Long id) {
       return SystemSmsCodeConvert.INSTANCE.convert(getById(id));
   }

   @Override
   public void add(SystemSmsCodeDTO systemSmsCodeDTO) {
        final SystemSmsCode systemSmsCode = SystemSmsCodeConvert.INSTANCE.convert(systemSmsCodeDTO);
        save(systemSmsCode);
   }

   @Override
   public void update(SystemSmsCodeDTO systemSmsCodeDTO) {
        final SystemSmsCode systemSmsCode = SystemSmsCodeConvert.INSTANCE.convert(systemSmsCodeDTO);
        final SystemSmsCode oldSystemSmsCode = getById(systemSmsCodeDTO.getId());
        Assert.notNull(oldSystemSmsCode, "对象不存在");
        updateById(systemSmsCode);
   }

   @Override
   public void delete(Long id) {
        removeById(id);
   }
}
