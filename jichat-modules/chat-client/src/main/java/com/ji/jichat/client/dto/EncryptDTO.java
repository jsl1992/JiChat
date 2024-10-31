package com.ji.jichat.client.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@Schema(description = "EncryptDTO")
public class EncryptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "密钥")
    private String secretKey;

    @Schema(description = "加密算法版本")
    private String encryptVersion;

    @Schema(description = "错误信息")
    private String errorMessage;


}
