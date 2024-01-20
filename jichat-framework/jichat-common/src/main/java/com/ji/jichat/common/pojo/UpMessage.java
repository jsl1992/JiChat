package com.ji.jichat.common.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 云端接收数据
 * 连接ID|客户端IP|客户端MAC|车场GUID|ParkSync（标记）|功能码|数据字符串|消息ID对应车场消息表ID（较早版本可能没有）|
 * <p>
 * 1|192.168.12.91:53135|4C:CC:6A:F5:9A:94|D5B02A77C78F4092B4255B79474FBE8D|ParkSync|3203|{"Region":"D5B02A77C78F4092B4255B79474FBE8D","ServerId":"22","ChannelId":"5","Success":"1","ErrMsg":"远程控制道闸：5-抬闸-成功"}|19298|
 *
 * @author lintengyue
 */
@Data
@NoArgsConstructor
public class UpMessage extends Message {


    /**
     * 版本号
     */
    private String version;

    /**
     * 内网IP
     */
    private String localIP;

    /**
     * 执行结果 0 失败 1 成功
     */
    private int errorCode = 1;

    /**
     * 执行失败内容
     */
    private String errorMsg;


    public boolean isMatch(Integer code) {
        return Objects.equals(this.getCode(), code);
    }

}
