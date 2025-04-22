package com.ji.jichat.user.service.impl;

import com.ji.jichat.user.entity.SystemDictType;
import com.ji.jichat.user.api.dto.SystemDictTypeDTO;
import com.ji.jichat.user.api.dto.SystemDictTypePageDTO;
import com.ji.jichat.user.api.vo.SystemDictTypeVO;
import com.ji.jichat.user.convert.SystemDictTypeConvert;
import com.ji.jichat.user.mapper.SystemDictTypeMapper;
import com.ji.jichat.user.service.ISystemDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@Service
public class SystemDictTypeServiceImpl extends ServiceImpl<SystemDictTypeMapper, SystemDictType> implements ISystemDictTypeService {

    @Override
    public List<SystemDictTypeVO> page(SystemDictTypePageDTO systemDictTypePageDTO) {
        return baseMapper.page(systemDictTypePageDTO);
    }

    @Override
    public List<SystemDictType> list(SystemDictTypeDTO dto) {
        return list(new LambdaQueryWrapper<>(SystemDictTypeConvert.INSTANCE.convert(dto)));
    }

   @Override
   public SystemDictTypeVO getDetail(Long id) {
       return SystemDictTypeConvert.INSTANCE.convert(getById(id));
   }

   @Override
   public void add(SystemDictTypeDTO systemDictTypeDTO) {
        final SystemDictType systemDictType = SystemDictTypeConvert.INSTANCE.convert(systemDictTypeDTO);
        save(systemDictType);
   }

   @Override
   public void update(SystemDictTypeDTO systemDictTypeDTO) {
        final SystemDictType systemDictType = SystemDictTypeConvert.INSTANCE.convert(systemDictTypeDTO);
        final SystemDictType oldSystemDictType = getById(systemDictTypeDTO.getId());
        Assert.notNull(oldSystemDictType, "对象不存在");
        updateById(systemDictType);
   }

   @Override
   public void delete(Long id) {
        removeById(id);
   }
}
