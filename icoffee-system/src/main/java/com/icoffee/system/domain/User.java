package com.icoffee.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.common.domain.BaseDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Name User
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-29 18:06
 */
@Data
@Entity
@Table(name = "system_user")
@TableName("system_user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseDomain implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String realname;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    /**
     * 是否活动，0-否，1-是
     */
    @ApiModelProperty(value = "是否活动，0-否，1-是")
    private Integer active = 1;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件")
    private String email;

    /**
     * 创建时间（时间戳）
     */
    @ApiModelProperty(value = "创建时间（时间戳）")
    private Long createAt = System.currentTimeMillis();

    /**
     * 修改时间（时间戳）
     */
    @ApiModelProperty(value = "修改时间（时间戳）")
    private Long updateAt;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private String roleId;

    /**
     * 是否锁定，0-否，1-是
     */
    private int locked = 0;
}