package com.ji.jichat.user.service;

import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.entity.SystemDictData;
import com.ji.jichat.user.api.dto.SystemDictDataDTO;
import com.ji.jichat.user.api.dto.SystemDictDataPageDTO;
import com.ji.jichat.user.api.vo.SystemDictDataVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
public interface ISystemDictDataService extends IService<SystemDictData> {

    /**
    * 分页查询字典数据表
    *
    * @param systemDictDataPageDTO 分页DTO
    * @return com.ji.jichat.user.vo.SystemDictDataVO
    */
    List<SystemDictDataVO> page(SystemDictDataPageDTO systemDictDataPageDTO);

    /**
    * 列表查询字典数据表
    *
    * @param systemDictDataDTO 列表查询DTO
    * @return com.ji.jichat.user.vo.SystemDictDataVO
    */
    List<SystemDictData> list(SystemDictDataDTO systemDictDataDTO);

    /**
    * 获取字典数据表详情
    *
    * @param id 主键
    * @return com.ji.jichat.user.vo.SystemDictDataVO
    */
    SystemDictDataVO getDetail(Long id);


    /**
    * 新增字典数据表
    *
    * @param systemDictDataDTO 新增DTO
    */
    void add(SystemDictDataDTO systemDictDataDTO);

    /**
    * 修改字典数据表
    *
    * @param systemDictDataDTO 修改DTO
    */
    void update(SystemDictDataDTO systemDictDataDTO);

    /**
    * 删除字典数据表信息
    *
    * @param  id 主键
    */
    void delete(Long id);

   SystemDictDataVO getDictData(String dictType, String value);
}
