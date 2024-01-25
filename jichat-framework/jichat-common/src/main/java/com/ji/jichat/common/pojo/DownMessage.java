package com.ji.jichat.common.pojo;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 云端下发数据（车场）
 * 1001|客户端IP|3417EB9E5D45|SCA|车场GUID|功能|数据内容（回应是为消息ＩＤ）|云端消息表ID（下发）|ALAZ|
 * <p>
 * (1001|218.106.156.27|3417EB9E5D45|SCA|D5B02A77C78F4092B4255B79474FBE8D|1007|{"ID":0,"Region":"D5B02A77C78F4092B4255B79474FBE8D","VirtualCardNum":null,"CarNum":"闽ABCDEF","Duration":"2018-11-29T23:59:59","Operator":"22","OperatorTime":"2018-11-28T15:36:29.1589143+08:00","Remark":""}|F599BC7E639244A39E799D733408A5C4|ALAZ|)
 * <p>
 * 是心跳包的时候, 回复 content HeartBeat  msgId = 0；
 * <p>
 * 不是心跳包的时候   回复  content = 他发过来的msgId  msgId = msgId
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
