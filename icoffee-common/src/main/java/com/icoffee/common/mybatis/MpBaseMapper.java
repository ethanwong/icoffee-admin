package com.icoffee.common.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icoffee.common.domain.BaseDomain;

import java.util.List;

/**
 * @Name MpBaseMapper
 * @Description Repository接口类基类
 * @Author chenly
 * @Create 2019-12-10 15:40
 */
public interface MpBaseMapper<T extends BaseDomain> extends BaseMapper<T> {

    /**
     * 删除所有数据
     */
    void deleteAll();

    /**
     * 查询所有数据
     */
    List<T> selectAll();
}