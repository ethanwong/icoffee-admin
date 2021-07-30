
package com.icoffee.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.icoffee.common.domain.BaseDomain;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.dto.TableSchemaDto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> entity泛型
 * @Name MpBaseService
 * @Description Service接口类基类
 * @Author huangyingfeng
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

    /**
     * 动态创建表
     *
     * @param tableName  表名
     * @param schemaList 字段描述列表
     */
    void createTableIfNotExists(String tableName, List<TableSchemaDto> schemaList);

    /**
     * 动态插入数据列表
     *
     * @param tableName  表名
     * @param columnList 字段数据List
     */
    void insertInto(String tableName, List<Map<String, Object>> columnList);

    /**
     * 动态插入数据
     *
     * @param tableName 表名
     * @param columnMap 字段数据Map
     */
    void insertInto(String tableName, Map<String, Object> columnMap);

    /**
     * 根据表名查询所有表字段
     *
     * @param tableName 表名
     * @return
     */
    List<String> selectColumnList(String tableName);

    /**
     * 根据字段列表查询数据
     *
     * @param tableName 表名
     * @param whereSql  条件SQL
     * @return
     */
    List<LinkedHashMap<String, Object>> select(String tableName, String whereSql);

    /**
     * 根据字段列表查询数据
     *
     * @param tableName  表名
     * @param columnList 字段列表
     * @param whereSql   条件SQL
     * @return
     */
    List<LinkedHashMap<String, Object>> select(String tableName, List<String> columnList, String whereSql);

    /**
     * 根据表名查询表是否存在
     *
     * @param tableName 表名
     * @return
     */
    boolean selectTableExists(String tableName);
}