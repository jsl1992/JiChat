<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="${cacheClassName}"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.columnName},
</#list>
        ${table.fieldNames}
    </sql>

</#if>
    <#list table.fields as field>
        <#if field.keyFlag>
            <#assign keyField = field>
        </#if>
    </#list>
    <select id="page" parameterType="${package.Parent}.api.dto.${entity}PageDTO" resultType="${package.Parent}.api.vo.${entity}VO">
        SELECT
    <#list table.fields as field>
        <#if field.propertyName == "createTime1"
        || field.propertyName == "createBy"
        || field.propertyName == "updateTime"
        || field.propertyName == "updateBy"
        || field.propertyName == "delFlag">
        <#-- 跳过特定字段 -->
            <#continue>
        </#if>
        ${field.columnName}<#if field_index < table.fields?size - 1>,</#if>
    </#list>
        FROM ${table.name}
        <where>
        <#list table.fields as field>
            <#if field.propertyName == "createTime1"
            || field.propertyName == "createBy"
            || field.propertyName == "updateTime"
            || field.propertyName == "updateBy"
            || field.propertyName == "delFlag">
            <#-- 跳过特定字段 -->
                <#continue>
            </#if>
        <#if field.propertyType == 'Date'>
            <if test="${field.propertyName}Start != null and ${field.propertyName}End != null">
                <#--      这边需要生成 #{},只能通过 ${"#{'"} 表达式转义          -->
                AND ${field.annotationColumnName} BETWEEN ${"#{"}${field.propertyName}Start} AND ${"#{"}${field.propertyName}End}
            </if>
        <#elseif  field.propertyType == 'String'>
            <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                AND ${field.annotationColumnName} LIKE concat('%', ${"#{"}${field.propertyName}}, '%')
            </if>
         <#else >
            <if test="${field.propertyName} != null">
                AND ${field.annotationColumnName}= ${"#{"}${field.propertyName}}
            </if>
         </#if>
            </#list>
        </where>
        ORDER BY ${keyField.annotationColumnName} DESC
    </select>
</mapper>
