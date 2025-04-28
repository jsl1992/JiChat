package com.ji.jichat.user.controller;

import cn.hutool.core.lang.Assert;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.excel.util.ExcelUtil;
import com.ji.jichat.mybatis.util.JiPageHelper;
import com.ji.jichat.user.api.dto.SystemSmsCodeDTO;
import com.ji.jichat.user.api.dto.SystemSmsCodePageDTO;
import com.ji.jichat.user.api.vo.SystemSmsCodeVO;
import com.ji.jichat.user.convert.SystemSmsCodeConvert;
import com.ji.jichat.user.excel.SystemSmsCodeExcelVO;
import com.ji.jichat.user.service.ISystemSmsCodeService;
import com.ji.jichat.web.util.HttpContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 手机验证码 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2024-11-08
 */
@RestController
@RequestMapping("/systemSmsCode")
@Tag(name = "手机验证码管理")
public class SystemSmsCodeController {


    @Resource
    private ISystemSmsCodeService systemSmsCodeService;


    /**
     * 分页查询手机验证码
     */
    @Operation(summary = "分页查询手机验证码")
    @GetMapping("/page")
    public CommonResult<PageVO<SystemSmsCodeVO>> page(SystemSmsCodePageDTO dto, PageDTO pageDTO) {
        final PageVO<SystemSmsCodeVO> pageVO = JiPageHelper.doSelectPageInfo(pageDTO, () -> systemSmsCodeService.page(dto));
        return CommonResult.success(pageVO);
    }

    @Operation(summary = "导出")
    @GetMapping("/exportDataToExcel")
    public void exportDataToExcel() {
        final SystemSmsCodePageDTO dto = SystemSmsCodePageDTO.builder().build();
        ExcelUtil.exportToExcel(HttpContextUtil.getHttpServletResponse(), "手机验证码导出测试", () -> systemSmsCodeService.page(dto), SystemSmsCodeExcelVO.class);
    }


    /**
     * 列表查询手机验证码
     */
    @Operation(summary = "列表查询手机验证码")
    @GetMapping("/list")
    public CommonResult<List<SystemSmsCodeVO>> list(SystemSmsCodeDTO dto) {
        return CommonResult.success(SystemSmsCodeConvert.INSTANCE.convertList(systemSmsCodeService.list(dto)));
    }


    /**
     * 获取手机验证码详情
     */
    @Operation(summary = "获取手机验证码详情")
    @GetMapping(value = "/{id}")
    public CommonResult<SystemSmsCodeVO> getDetail(@PathVariable("id") Long id) {
        return CommonResult.success(systemSmsCodeService.getDetail(id));
    }

    /**
     * 新增手机验证码
     */
    @Operation(summary = "新增手机验证码")
    @PostMapping(value = "/add")
    public CommonResult<Void> add(@RequestBody @Validated SystemSmsCodeDTO systemSmsCodeDTO) {
        systemSmsCodeService.add(systemSmsCodeDTO);
        return CommonResult.success();
    }

    /**
     * 修改手机验证码
     */
    @Operation(summary = "修改手机验证码")
    @PutMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody @Validated SystemSmsCodeDTO systemSmsCodeDTO) {
        Assert.notNull(systemSmsCodeDTO.getId(), "id不能为空");
        systemSmsCodeService.update(systemSmsCodeDTO);
        return CommonResult.success();
    }

    /**
     * 删除手机验证码
     */
    @Operation(summary = "删除手机验证码")
    @DeleteMapping("/{id}")
    public CommonResult<Void> delete(@PathVariable Long id) {
        systemSmsCodeService.delete(id);
        return CommonResult.success();
    }


}
