package ${package.Mapper};

import ${package.Entity}.${entity};
import ${package.Parent}.api.dto.${entity}PageDTO;
import ${package.Parent}.api.vo.${entity}VO;
import java.util.List;
import ${superMapperClassPackage};
<#if mapperAnnotation>
import org.apache.ibatis.annotations.Mapper;
</#if>

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if mapperAnnotation>
@Mapper
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {


    /**
    * 分页查询${table.comment}
    *
    * @param ${table.entityPath}PageDTO 分页DTO
    * @return ${package.Parent}.vo.${entity}VO
    */
    List<${entity}VO> page(${entity}PageDTO ${table.entityPath}PageDTO);


    }
</#if>
