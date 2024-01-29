package com.ji.jichat.common.pojo;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * DownMessage 下发消息
 *
 * @author jisl
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DownMessage extends Message {

    public static void main(String[] args) {
        final DownMessage build = DownMessage.builder().type(1).build();
        System.out.println(JSON.toJSONString(build));
    }


}
