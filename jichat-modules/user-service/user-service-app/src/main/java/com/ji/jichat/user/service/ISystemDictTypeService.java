package com.ji.jichat.user.service;

import com.ji.jichat.user.entity.SystemDictType;
import com.ji.jichat.user.api.dto.SystemDictTypeDTO;
import com.ji.jichat.user.api.dto.SystemDictTypePageDTO;
import com.ji.jichat.user.api.vo.SystemDictTypeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
public interface ISystemDictTypeService extends IService<SystemDictType> {

    /**
    * 分页查询字典类型表
    *
    * @param systemDictTypePageDTO 分页DTO
    * @return com.ji.jichat.user.vo.SystemDictTypeVO
    */
    List<SystemDictTypeVO> page(SystemDictTypePageDTO systemDictTypePageDTO);

    /**
    * 列表查询字典类型表
    *
    * @param systemDictTypeDTO 列表查询DTO
    * @return com.ji.jichat.user.vo.SystemDictTypeVO
    */
    List<SystemDictType> list(SystemDictTypeDTO systemDictTypeDTO);

    /**
    * 获取字典类型表详情
    *
    * @param id 主键
    * @return com.ji.jichat.user.vo.SystemDictTypeVO
    */
    SystemDictTypeVO getDetail(Long id);


    /**
    * 新增字典类型表
    *
    * @param systemDictTypeDTO 新增DTO
    */
    void add(SystemDictTypeDTO systemDictTypeDTO);

    /**
    * 修改字典类型表
    *
    * @param systemDictTypeDTO 修改DTO
    */
    void update(SystemDictTypeDTO systemDictTypeDTO);

    /**
    * 删除字典类型表信息
    *
    * @param  id 主键
    */
    void delete(Long id);
}
