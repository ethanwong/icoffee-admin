package com.icoffee.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.common.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Name RoleAuthority
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-29 18:06
 */
@Data
@Entity
@Table(name = "system_role_authority")
@TableName(value = "system_role_authority")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleAuthority extends BaseDomain implements Serializable {

    private String authorityId;
    private String roleId;


}
