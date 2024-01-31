package com.ji.jichat.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表DTO
 * </p>
 *
 * @author jisl
 * @since 2024-01-24
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "EncryptDTO")
public class EncryptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("密钥")
    private String secretKey;

    @ApiModelProperty("加密算法版本")
    private String encryptVersion;

    @ApiModelProperty("错误信息")
    private String errorMessage;


}
