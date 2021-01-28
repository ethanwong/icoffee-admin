package com.icoffee.security.model;

import com.icoffee.system.dto.PermissionDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Name LoginResultDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-28 15:25
 */
@Data
public class LoginResultDto {
    private String username;
    private String role;
    private String token;
    private String expireAt;
    private List<PermissionDto> permissionList;
}
