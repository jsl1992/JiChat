package com.ji.jichat.user.controller;

import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.mybatis.util.JiPageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ji.jichat.user.service.ISystemDictTypeService;
import com.ji.jichat.user.api.dto.SystemDictTypeDTO;
import com.ji.jichat.user.api.dto.SystemDictTypePageDTO;
import com.ji.jichat.user.api.vo.SystemDictTypeVO;
import com.ji.jichat.user.convert.SystemDictTypeConvert;
import java.util.List;
import cn.hutool.core.lang.Assert;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@RestController
@RequestMapping("/systemDictType")
@Tag(name = "字典类型表管理")
public class SystemDictTypeController {



    @Resource
    private ISystemDictTypeService systemDictTypeService;

    /**
    * 分页查询字典类型表
    */
    @Operation(summary ="分页查询字典类型表")
    @GetMapping("/page")
    public CommonResult<PageVO<SystemDictTypeVO>> page(SystemDictTypePageDTO dto, PageDTO pageDTO) {
        final PageVO<SystemDictTypeVO> pageVO = JiPageHelper.doSelectPageInfo(pageDTO, () -> systemDictTypeService.page(dto));
        return CommonResult.success(pageVO);
    }

    /**
    * 列表查询字典类型表
    */
    @Operation(summary ="列表查询字典类型表")
    @GetMapping("/list")
    public CommonResult<List<SystemDictTypeVO>> list(SystemDictTypeDTO dto) {
        return CommonResult.success(SystemDictTypeConvert.INSTANCE.convertList(systemDictTypeService.list(dto)));
    }


    /**
    * 获取字典类型表详情
    */
    @Operation(summary ="获取字典类型表详情")
    @GetMapping(value = "/{id}")
    public CommonResult<SystemDictTypeVO> getDetail(@PathVariable("id") Long id) {
        return CommonResult.success(systemDictTypeService.getDetail(id));
    }

     /**
     * 新增字典类型表
     */
     @Operation(summary ="新增字典类型表")
     @PostMapping(value = "/add")
     public CommonResult<Void> add(@RequestBody @Validated SystemDictTypeDTO systemDictTypeDTO) {
        systemDictTypeService.add(systemDictTypeDTO);
        return CommonResult.success();
     }

    /**
    * 修改字典类型表
    */
    @Operation(summary ="修改字典类型表")
    @PutMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody @Validated SystemDictTypeDTO systemDictTypeDTO) {
         Assert.notNull(systemDictTypeDTO.getId(), "id不能为空");
        systemDictTypeService.update(systemDictTypeDTO);
        return CommonResult.success();
    }

    /**
    * 删除字典类型表
    */
    @Operation(summary ="删除字典类型表")
    @DeleteMapping("/{id}")
    public CommonResult<Void> delete(@PathVariable Long id) {
        systemDictTypeService.delete(id);
        return CommonResult.success();
    }


}
