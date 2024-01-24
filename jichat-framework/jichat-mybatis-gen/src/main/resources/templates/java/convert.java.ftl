package ${package.Parent}.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ${package.Parent}.entity.${entity};
import ${package.Parent}.api.dto.${entity}DTO;
import ${package.Parent}.api.vo.${entity}VO;

/**
 * ${table.comment!} Convert
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${entity}Convert {

    ${entity}Convert INSTANCE = Mappers.getMapper(${entity}Convert.class);

    ${entity} convert(${entity}VO bean);

    ${entity} convert(${entity}DTO bean);

    ${entity}VO convert(${entity} bean);

    List<${entity}VO> convertList(List<${entity}> list);




}
