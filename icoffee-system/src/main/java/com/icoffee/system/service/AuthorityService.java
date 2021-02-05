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
     * 批量保存授权信息
     * @param authorityList
     * @return
     */
    ResultDto batchSaveAuthorityList(List<Authority> authorityList);
}
