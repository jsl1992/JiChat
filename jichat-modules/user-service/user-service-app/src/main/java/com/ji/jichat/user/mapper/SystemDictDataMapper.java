package com.ji.jichat.user.mapper;

import com.ji.jichat.user.entity.SystemDictData;
import com.ji.jichat.user.api.dto.SystemDictDataPageDTO;
import com.ji.jichat.user.api.vo.SystemDictDataVO;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
public interface SystemDictDataMapper extends BaseMapper<SystemDictData> {


    /**
    * 分页查询字典数据表
    *
    * @param systemDictDataPageDTO 分页DTO
    * @return com.ji.jichat.user.vo.SystemDictDataVO
    */
    List<SystemDictDataVO> page(SystemDictDataPageDTO systemDictDataPageDTO);


    }
