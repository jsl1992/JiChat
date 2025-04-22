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
import com.ji.jichat.user.service.ISystemDictDataService;
import com.ji.jichat.user.api.dto.SystemDictDataDTO;
import com.ji.jichat.user.api.dto.SystemDictDataPageDTO;
import com.ji.jichat.user.api.vo.SystemDictDataVO;
import com.ji.jichat.user.convert.SystemDictDataConvert;
import java.util.List;
import cn.hutool.core.lang.Assert;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@RestController
@RequestMapping("/systemDictData")
@Tag(name = "字典数据表管理")
public class SystemDictDataController {



    @Resource
    private ISystemDictDataService systemDictDataService;

    /**
    * 分页查询字典数据表
    */
    @Operation(summary ="分页查询字典数据表")
    @GetMapping("/page")
    public CommonResult<PageVO<SystemDictDataVO>> page(SystemDictDataPageDTO dto, PageDTO pageDTO) {
        final PageVO<SystemDictDataVO> pageVO = JiPageHelper.doSelectPageInfo(pageDTO, () -> systemDictDataService.page(dto));
        return CommonResult.success(pageVO);
    }

    /**
    * 列表查询字典数据表
    */
    @Operation(summary ="列表查询字典数据表")
    @GetMapping("/list")
    public CommonResult<List<SystemDictDataVO>> list(SystemDictDataDTO dto) {
        return CommonResult.success(SystemDictDataConvert.INSTANCE.convertList(systemDictDataService.list(dto)));
    }


    /**
    * 获取字典数据表详情
    */
    @Operation(summary ="获取字典数据表详情")
    @GetMapping(value = "/{id}")
    public CommonResult<SystemDictDataVO> getDetail(@PathVariable("id") Long id) {
        return CommonResult.success(systemDictDataService.getDetail(id));
    }

     /**
     * 新增字典数据表
     */
     @Operation(summary ="新增字典数据表")
     @PostMapping(value = "/add")
     public CommonResult<Void> add(@RequestBody @Validated SystemDictDataDTO systemDictDataDTO) {
        systemDictDataService.add(systemDictDataDTO);
        return CommonResult.success();
     }

    /**
    * 修改字典数据表
    */
    @Operation(summary ="修改字典数据表")
    @PutMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody @Validated SystemDictDataDTO systemDictDataDTO) {
         Assert.notNull(systemDictDataDTO.getId(), "id不能为空");
        systemDictDataService.update(systemDictDataDTO);
        return CommonResult.success();
    }

    /**
    * 删除字典数据表
    */
    @Operation(summary ="删除字典数据表")
    @DeleteMapping("/{id}")
    public CommonResult<Void> delete(@PathVariable Long id) {
        systemDictDataService.delete(id);
        return CommonResult.success();
    }


}
