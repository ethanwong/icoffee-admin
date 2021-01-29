
package com.icoffee.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.icoffee.common.domain.BaseDomain;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;

import java.io.Serializable;

/**
 * @param <T> entity泛型
 * @Name MpBaseService
 * @Description Service接口类基类
 * @Author chenly
 * @Create 2019-12-10 11:28
 */
public interface MpBaseService<T extends BaseDomain> extends IService<T> {

    /**
     * 根据条件分页查询
     *
     * @param queryWrapper 查询条件
     * @param pageNo       当前页数
     * @param pageSize     每页记录数
     * @return
     */
    PageDto<T> selectPage(QueryWrapper<T> queryWrapper, Integer pageNo, Integer pageSize);

    /**
     * 查询所有数据
     *
     * @return
     */
    ResultDto selectAll();

    /**
     * 根据ID删除实体
     *
     * @param id 实体主键
     * @return
     */
    ResultDto deleteById(Serializable id);

    /**
     * 根据条件删除
     *
     * @param queryWrapper 删除条件
     * @return
     */
    ResultDto deleteByWrapper(QueryWrapper<T> queryWrapper);

    /**
     * 删除所有数据
     *
     * @return
     */
    ResultDto deleteAll();
}