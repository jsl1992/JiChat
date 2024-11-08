package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Parent}.api.dto.${entity}DTO;
import ${package.Parent}.api.dto.${entity}PageDTO;
import ${package.Parent}.api.vo.${entity}VO;
import ${superServiceClassPackage};
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
    <#list table.fields as field>
        <#if field.keyFlag>
            <#assign keyField = field>
        </#if>
    </#list>

    /**
    * 分页查询${table.comment}
    *
    * @param ${table.entityPath}PageDTO 分页DTO
    * @return ${package.Parent}.vo.${entity}VO
    */
    List<${entity}VO> page(${entity}PageDTO ${table.entityPath}PageDTO);

    /**
    * 列表查询${table.comment}
    *
    * @param ${table.entityPath}DTO 列表查询DTO
    * @return ${package.Parent}.vo.${entity}VO
    */
    List<${entity}> list(${entity}DTO ${table.entityPath}DTO);

    /**
    * 获取${table.comment}详情
    *
    * @param id 主键
    * @return ${package.Parent}.vo.${entity}VO
    */
    ${entity}VO getDetail(${keyField.propertyType} id);


    /**
    * 新增${table.comment}
    *
    * @param ${table.entityPath}DTO 新增DTO
    */
    void add(${entity}DTO ${table.entityPath}DTO);

    /**
    * 修改${table.comment}
    *
    * @param ${table.entityPath}DTO 修改DTO
    */
    void update(${entity}DTO ${table.entityPath}DTO);

    /**
    * 删除${table.comment}信息
    *
    * @param  id 主键
    */
    void delete(${keyField.propertyType} id);
}
</#if>
