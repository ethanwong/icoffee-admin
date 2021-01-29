package com.icoffee.system.service;


import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.Authority;

import java.util.List;

/**
 * @Name AuthorityService
 * @Description 权限事务接口
 * @Author lincy
 * @Create 2020-02-27 9:11
 */
public interface AuthorityService extends MpBaseService<Authority> {

    /**
     * 保存
     *
     * @param authority 实体
     * @return
     */
    ResultDto saveEntity(Authority authority);

    /**
     * 修改
     *
     * @param authority 实体
     * @return
     */
    ResultDto updateEntity(Authority authority);

    /**
     * 批量添加
     *
     * @param menuId
     * @return
     */
    ResultDto batchSave(String menuId);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    ResultDto batchDelete(List<String> ids);
}
