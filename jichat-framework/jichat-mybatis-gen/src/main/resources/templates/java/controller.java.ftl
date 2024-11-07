package ${package.Controller};

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Parent}.dto.${entity}DTO;
import ${package.Parent}.dto.${entity}PageDTO;
import ${package.Parent}.vo.${entity}VO;
import ${package.Parent}.convert.${entity}Convert;
<#--TODO 这个R每个路径都不一样还不好调整,先写死-->
import com.platform.common.core.domain.R;
import ${package.Parent}.common.page.TableDataInfo;
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
@Api(value = "${table.comment}", tags = {"${table.comment}管理"})
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
    @ApiOperation("分页查询${table.comment}")
    @GetMapping("/page")
    public TableDataInfo<${entity}VO> page(${entity}PageDTO dto,
                                            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        PageInfo<${entity}VO> pageInfo = PageHelper.startPage(pageNum, pageSize).
                doSelectPageInfo(() -> ${table.entityPath}Service.page(dto));
        return new TableDataInfo(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
    * 列表查询${table.comment}
    */
    @ApiOperation("列表查询${table.comment}")
    @GetMapping("/list")
    public R<List<${entity}VO>> list(${entity}DTO dto) {
        return R.ok(${entity}Convert.INSTANCE.convertList(${table.entityPath}Service.list(dto)));
    }


    /**
    * 获取${table.comment}详情
    */
    @ApiOperation("获取${table.comment}详情")
    @GetMapping(value = "/{id}")
    public R<${entity}VO> getDetail(@PathVariable("id") ${keyField.propertyType} id) {
        return R.ok(${table.entityPath}Service.getDetail(id));
    }

     /**
     * 新增${table.comment}
     */
     @ApiOperation("新增${table.comment}")
     @PostMapping(value = "/add")
     public R<Boolean> add(@RequestBody @Validated ${entity}DTO ${table.entityPath}DTO) {
        ${table.entityPath}Service.add(${table.entityPath}DTO);
        return R.ok();
     }

    /**
    * 修改${table.comment}
    */
    @ApiOperation("修改${table.comment}")
    @PutMapping(value = "/update")
    public R<Boolean> update(@RequestBody @Validated ${entity}DTO ${table.entityPath}DTO) {
         Assert.notNull(${table.entityPath}DTO.get${keyField.capitalName}(), "${keyField.propertyName}不能为空");
        ${table.entityPath}Service.update(${table.entityPath}DTO);
        return R.ok();
    }

    /**
    * 删除${table.comment}
    */
    @ApiOperation("删除${table.comment}")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable ${keyField.propertyType} id) {
        ${table.entityPath}Service.delete(id);
        return R.ok();
    }


}
</#if>
