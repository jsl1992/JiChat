//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ji.jichat.common.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author jisl on 2024/1/28 16:17
 */
@ApiModel("分页VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private long total;
    private List<T> list;


    public PageVO(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }


    @Override
    public String toString() {
        return "PageSerializable{total=" + this.total + ", list=" + this.list + '}';
    }
}
