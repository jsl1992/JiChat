package com.ji.jichat.mybatis.util;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;

/**
 * PageHelper 通过实现 MyBatis 的 Interceptor 接口，创建一个拦截器（PageInterceptor）。拦截器可以在执行 SQL 语句之前和之后进行拦截处理
 *
 * @author jisl on 2024/1/28 17:07
 */
public class JiPageHelper {

    /**
     * 封装PageHelper的分页（原来返回的PageInfo字段太多了，这边自定义返回PageVO）
     *
     * @param pageDTO 分页内容
     * @param select  接口中调用的查询方法
     * @return com.ji.jichat.common.pojo.PageVO<E>
     * @author jisl on 2024/1/28 18:11
     **/
    public static <E> PageVO<E> doSelectPageInfo(PageDTO pageDTO, ISelect select) {
        PageInfo<E> pageInfo = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize()).doSelectPageInfo(select);
        return new PageVO<>(pageInfo.getList(), pageInfo.getTotal());
    }
}
