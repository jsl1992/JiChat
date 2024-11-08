package ${package.Controller};

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
import ${package.Service}.${table.serviceName};
import ${package.Parent}.api.dto.${entity}DTO;
import ${package.Parent}.api.dto.${entity}PageDTO;
import ${package.Parent}.api.vo.${entity}VO;
import ${package.Parent}.convert.${entity}Convert;
import java.util.List;
import cn.hutool.core.lang.Assert;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Tag(name = "${table.comment}管理")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

<#list table.fields as field>
  <#if field.keyFlag>
      <#assign keyField = field>
  </#if>
</#list>


    @Resource
    private ${table.serviceName} ${table.entityPath}Service;

    /**
    * 分页查询${table.comment}
    */
    @Operation(summary ="分页查询${table.comment}")
    @GetMapping("/page")
    public CommonResult<PageVO<${entity}VO>> page(${entity}PageDTO dto, PageDTO pageDTO) {
        final PageVO<${entity}VO> pageVO = JiPageHelper.doSelectPageInfo(pageDTO, () -> ${table.entityPath}Service.page(dto));
        return CommonResult.success(pageVO);
    }

    /**
    * 列表查询${table.comment}
    */
    @Operation(summary ="列表查询${table.comment}")
    @GetMapping("/list")
    public CommonResult<List<${entity}VO>> list(${entity}DTO dto) {
        return CommonResult.success(${entity}Convert.INSTANCE.convertList(${table.entityPath}Service.list(dto)));
    }


    /**
    * 获取${table.comment}详情
    */
    @Operation(summary ="获取${table.comment}详情")
    @GetMapping(value = "/{id}")
    public CommonResult<${entity}VO> getDetail(@PathVariable("id") ${keyField.propertyType} id) {
        return CommonResult.success(${table.entityPath}Service.getDetail(id));
    }

     /**
     * 新增${table.comment}
     */
     @Operation(summary ="新增${table.comment}")
     @PostMapping(value = "/add")
     public CommonResult<Boolean> add(@RequestBody @Validated ${entity}DTO ${table.entityPath}DTO) {
        ${table.entityPath}Service.add(${table.entityPath}DTO);
        return CommonResult.success();
     }

    /**
    * 修改${table.comment}
    */
    @Operation(summary ="修改${table.comment}")
    @PutMapping(value = "/update")
    public CommonResult<Boolean> update(@RequestBody @Validated ${entity}DTO ${table.entityPath}DTO) {
         Assert.notNull(${table.entityPath}DTO.get${keyField.capitalName}(), "${keyField.propertyName}不能为空");
        ${table.entityPath}Service.update(${table.entityPath}DTO);
        return CommonResult.success();
    }

    /**
    * 删除${table.comment}
    */
    @Operation(summary ="删除${table.comment}")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@PathVariable ${keyField.propertyType} id) {
        ${table.entityPath}Service.delete(id);
        return CommonResult.success();
    }


}
</#if>
