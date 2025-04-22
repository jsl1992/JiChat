package com.ji.jichat.user.mapper;

import com.ji.jichat.user.entity.SystemDictType;
import com.ji.jichat.user.api.dto.SystemDictTypePageDTO;
import com.ji.jichat.user.api.vo.SystemDictTypeVO;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
public interface SystemDictTypeMapper extends BaseMapper<SystemDictType> {


    /**
    * 分页查询字典类型表
    *
    * @param systemDictTypePageDTO 分页DTO
    * @return com.ji.jichat.user.vo.SystemDictTypeVO
    */
    List<SystemDictTypeVO> page(SystemDictTypePageDTO systemDictTypePageDTO);


    }
