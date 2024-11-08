package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Parent}.api.dto.${entity}DTO;
import ${package.Parent}.api.dto.${entity}PageDTO;
import ${package.Parent}.api.vo.${entity}VO;
import ${package.Parent}.convert.${entity}Convert;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    <#list table.fields as field>
        <#if field.keyFlag>
            <#assign keyField = field>
        </#if>
    </#list>

    @Override
    public List<${entity}VO> page(${entity}PageDTO ${table.entityPath}PageDTO) {
        return baseMapper.page(${table.entityPath}PageDTO);
    }

    @Override
    public List<${entity}> list(${entity}DTO dto) {
        return list(new LambdaQueryWrapper<>(${entity}Convert.INSTANCE.convert(dto)));
    }

   @Override
   public ${entity}VO getDetail(${keyField.propertyType} id) {
       return ${entity}Convert.INSTANCE.convert(getById(id));
   }

   @Override
   public void add(${entity}DTO ${table.entityPath}DTO) {
        final ${entity} ${table.entityPath} = ${entity}Convert.INSTANCE.convert(${table.entityPath}DTO);
        save(${table.entityPath});
   }

   @Override
   public void update(${entity}DTO ${table.entityPath}DTO) {
        final ${entity} ${table.entityPath} = ${entity}Convert.INSTANCE.convert(${table.entityPath}DTO);
        final ${entity} old${entity} = getById(${table.entityPath}DTO.get${keyField.capitalName}());
        Assert.notNull(old${entity}, "对象不存在");
        updateById(${table.entityPath});
   }

   @Override
   public void delete(${keyField.propertyType} id) {
        removeById(id);
   }
}
</#if>
