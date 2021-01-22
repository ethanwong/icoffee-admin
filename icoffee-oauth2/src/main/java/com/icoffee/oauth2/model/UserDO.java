package com.icoffee.oauth2.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.oauth2.common.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Name UserDO
 * @Description 用户实体类
 * @Author chenly
 * @Create 2019-11-29 16:50
 */
@Data
@Entity
@Table(name = "coffee_user")
@TableName("coffee_user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDO extends BaseDO implements Serializable {

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
}