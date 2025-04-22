package com.ji.jichat.user.service.impl;

import com.ji.jichat.user.entity.SystemDictData;
import com.ji.jichat.user.api.dto.SystemDictDataDTO;
import com.ji.jichat.user.api.dto.SystemDictDataPageDTO;
import com.ji.jichat.user.api.vo.SystemDictDataVO;
import com.ji.jichat.user.convert.SystemDictDataConvert;
import com.ji.jichat.user.mapper.SystemDictDataMapper;
import com.ji.jichat.user.service.ISystemDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@Service
public class SystemDictDataServiceImpl extends ServiceImpl<SystemDictDataMapper, SystemDictData> implements ISystemDictDataService {

    @Override
    public List<SystemDictDataVO> page(SystemDictDataPageDTO systemDictDataPageDTO) {
        return baseMapper.page(systemDictDataPageDTO);
    }

    @Override
    public List<SystemDictData> list(SystemDictDataDTO dto) {
        return list(new LambdaQueryWrapper<>(SystemDictDataConvert.INSTANCE.convert(dto)));
    }

   @Override
   public SystemDictDataVO getDetail(Long id) {
       return SystemDictDataConvert.INSTANCE.convert(getById(id));
   }

   @Override
   public void add(SystemDictDataDTO systemDictDataDTO) {
        final SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.convert(systemDictDataDTO);
        save(systemDictData);
   }

   @Override
   public void update(SystemDictDataDTO systemDictDataDTO) {
        final SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.convert(systemDictDataDTO);
        final SystemDictData oldSystemDictData = getById(systemDictDataDTO.getId());
        Assert.notNull(oldSystemDictData, "对象不存在");
        updateById(systemDictData);
   }

   @Override
   public void delete(Long id) {
        removeById(id);
   }
}
