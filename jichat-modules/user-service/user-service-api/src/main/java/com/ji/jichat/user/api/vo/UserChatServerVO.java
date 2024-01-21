package com.ji.jichat.user.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户TCP服务")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChatServerVO {

    @ApiModelProperty(value = "用户id", required = true, example = "192.168.77.130")
    private Long userId;

    @ApiModelProperty(value = "设备类型", required = true, example = "1")
    private Integer deviceType;

    @ApiModelProperty(value = "外网ip", required = true, example = "192.168.77.130")
    private String ip;

    @ApiModelProperty(value = "tcp端口号", required = true, example = "18001")
    private Integer port;

    /**
     * 获取key （ip和port拼接）
     *
     * @author jisl on 2024/1/21 12:42
     **/
    public String getKey() {
        return this.ip + "_" + this.ip;
    }


}
