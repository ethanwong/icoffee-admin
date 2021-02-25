package com.icoffee.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.icoffee.common.domain.BaseDomain;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.dto.TableSchemaDto;
import com.icoffee.common.mybatis.MpBaseMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Name MpBaseServiceImpl
 * @Description Service接口实现类基类
 * @Author chenly
 * @Create 2019-12-10 11:29
 */
@Log4j2
public class MpBaseServiceImpl<M extends MpBaseMapper<T>, T extends BaseDomain> extends ServiceImpl<M, T> implements MpBaseService<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    /**
     * 默认页数
     */
    private final Integer PAGE_NO = 1;

    /**
     * 默认每页记录数
     */
    private final Integer PAGE_SIZE = 10;

    /**
     * 根据条件分页查询
     *
     * @param queryWrapper 查询条件
     * @param pageNo       当前页数
     * @param pageSize     每页记录数
     * @return
     */
    @Override
    public PageDto<T> selectPage(QueryWrapper<T> queryWrapper, Integer pageNo, Integer pageSize) {
        PageDto<T> pageDTO = new PageDto<>();
        if (pageNo == null) {
            pageNo = PAGE_NO;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        //当前页数
        pageDTO.setPageNo(pageNo);
        //每页记录数
        pageDTO.setPageSize(pageSize);

        IPage<T> page = getBaseMapper().selectPage(new Page<T>(pageNo, pageSize), queryWrapper);

        //总页数
        pageDTO.setPages(page.getPages());

        //总记录数
        pageDTO.setTotal(page.getTotal());
        //分页数据
        pageDTO.setItems(page.getRecords());
        return pageDTO;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public ResultDto selectAll() {
        try {
            List<T> dataList = getBaseMapper().selectAll();
            return ResultDto.returnSuccessData(dataList);
        } catch (Exception exception) {
            log.error("删除所有数据出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * 根据ID删除实体
     *
     * @param id 实体主键
     * @return
     */
    @Override
    public ResultDto deleteById(Serializable id) {
        try {
            if (StringUtils.isBlank(id.toString())) {
                return ResultDto.returnFail("ID不可为空");
            }

            removeById(id);
            return ResultDto.returnSuccess();
        } catch (Exception exception) {
            log.error("根据ID删除实体出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * 根据条件删除
     *
     * @param queryWrapper 删除条件
     * @return
     */
    @Override
    public ResultDto deleteByWrapper(QueryWrapper<T> queryWrapper) {
        try {
            if (queryWrapper == null) {
                return ResultDto.returnFail("条件不可为空");
            }
            remove(queryWrapper);
            return ResultDto.returnSuccess();
        } catch (Exception exception) {
            log.error("根据条件删除实体出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * 删除所有数据
     *
     * @return
     */
    @Override
    public ResultDto deleteAll() {
        try {
            getBaseMapper().deleteAll();
            return ResultDto.returnSuccess();
        } catch (Exception exception) {
            log.error("删除所有数据出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * 动态创建表
     *
     * @param tableName  表名
     * @param schemaList 字段描述列表
     */
    @Override
    public void createTableIfNotExists(String tableName, List<TableSchemaDto> schemaList) {
        if (StringUtils.isBlank(tableName) || schemaList == null || schemaList.isEmpty()) {
            return;
        }
        StringBuilder createSql = new StringBuilder();
        StringBuilder column = new StringBuilder();
        StringBuilder index = new StringBuilder();
        //拼接表名
        createSql.append(" create table if not exists ").append(tableName).append("(");
        for (TableSchemaDto schema : schemaList) {
            //拼接字段名与数据类型
            column.append(",").append(schema.getColumnName()).append(" ").append(schema.getColumnType());
            //存在字段长度定义
            if (StringUtils.isNotBlank(schema.getColumnLen())) {
                column.append("(").append(schema.getColumnLen()).append(")");
            }
            //字段非空
            if (schema.isNotNull()) {
                column.append(" not null ");
            }
            //字段唯一
            if (schema.isUnique()) {
                column.append(" unique ");
            }
            //字段为主键
            if (schema.isPrimary()) {
                column.append(" primary key ");
            }
            //字段为索引
            if (schema.isIndex()) {
                if (StringUtils.isBlank(index)) {
                    index.append(" index(");
                } else {
                    index.append(",");
                }
                index.append(schema.getColumnName());
            }
        }
        //拼接字段定义
        createSql.append(column.toString().replaceFirst(",", ""));
        //拼接索引
        if (StringUtils.isNotBlank(index)) {
            createSql.append(",").append(index.toString()).append(")");
        }
        //拼接结束符
        createSql.append(")");
        getBaseMapper().executeUpdateSql(createSql.toString());
    }

    /**
     * 动态插入数据列表
     *
     * @param tableName  表名
     * @param columnList 字段数据List
     */
    @Override
    public void insertInto(String tableName, List<Map<String, Object>> columnList) {
        if (StringUtils.isBlank(tableName) || columnList == null || columnList.isEmpty()) {
            return;
        }
        columnList.forEach(columnMap -> insertInto(tableName, columnMap));
    }

    /**
     * 动态插入数据
     *
     * @param tableName 表名
     * @param columnMap 字段数据Map
     */
    @Override
    public void insertInto(String tableName, Map<String, Object> columnMap) {
        if (StringUtils.isBlank(tableName) || columnMap == null || columnMap.isEmpty()) {
            return;
        }
        //获取表中的所有列
        List<String> columns = selectColumnList(tableName);
        //查无表中的列,不执行插入
        if (columns == null || columns.isEmpty()) {
            return;
        }
        //表中的列名与字段数据Map的key的交集为有效的插入列
        Set<String> validColumns = Sets.intersection(Sets.newHashSet(columns), columnMap.keySet());
        //若字段数据Map的列不存在于有效插入列中,移除该字段数据
        columnMap.keySet().removeIf(key -> !validColumns.contains(key));
        //插入SQL语句
        StringBuilder insertSql = new StringBuilder();
        //列SQL语句
        StringBuilder columnSql = new StringBuilder();
        //值SQL语句
        StringBuilder valueSql = new StringBuilder();
        insertSql.append(" insert into ").append(tableName).append(" (");
        //构造列SQL与值SQL
        constructColAndVal(columnMap, columnSql, valueSql);
        //无字段不执行insert
        if (StringUtils.isBlank(columnSql) || StringUtils.isBlank(valueSql)) {
            return;
        }
        //拼接列
        insertSql.append(columnSql.toString().replaceFirst(",", "")).append(") ");
        insertSql.append("values (");
        //拼接值
        insertSql.append(valueSql.toString().replaceFirst(",", "")).append(") ");
        getBaseMapper().executeInsertSql(insertSql.toString());
    }

    /**
     * 根据表名查询所有表字段
     *
     * @param tableName 表名
     * @return
     */
    @Override
    public List<String> selectColumnList(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        String selectSql = "select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='" +
                tableName + "'";
        List<LinkedHashMap<String, Object>> columnList =
                getBaseMapper().executeSelectSql(selectSql);
        if (columnList == null || columnList.isEmpty()) {
            return null;
        }
        return columnList.stream().map(column -> (column.get("COLUMN_NAME") == null ? "" :
                column.get("COLUMN_NAME").toString())).collect(Collectors.toList());
    }

    /**
     * 构造列SQL和值SQL
     *
     * @param columnMap 字段数据Map
     */
    private void constructColAndVal(Map<String, Object> columnMap, StringBuilder columnSql, StringBuilder valueSql) {
        //处理数据
        for (String column : columnMap.keySet()) {
            //添加列
            columnSql.append(",").append(column);
            //添加值
            valueSql.append(",'").append(columnMap.get(column) != null
                    ? columnMap.get(column).toString() : "").append("'");
        }
    }

    @Override
    public List<LinkedHashMap<String, Object>> select(String tableName, String whereSql) {
        //获取表中的所有列
        List<String> columns = selectColumnList(tableName);
        //构建查询语句
        StringBuilder selectSql = new StringBuilder(" select ");
        //构建from语句
        StringBuilder fromSql = new StringBuilder(" from ").append(tableName).append(" ");
        //拼接语句
        selectSql.append(" * ").append(fromSql);
        if (StringUtils.isNotBlank(whereSql)) {
            selectSql.append(whereSql);
        }
        //查询数据
        List<LinkedHashMap<String, Object>> dataList = getBaseMapper().executeSelectSql(selectSql.toString());
        if (dataList == null || dataList.isEmpty()) {
            return null;
        }
        return dataList;
    }

    /**
     * 根据字段列表查询数据
     *
     * @param tableName  表名
     * @param columnList 字段列表
     * @param whereSql   条件SQL
     * @return
     */
    @Override
    public List<LinkedHashMap<String, Object>> select(String tableName, List<String> columnList, String whereSql) {
        if (StringUtils.isBlank(tableName) || columnList == null || columnList.isEmpty()) {
            return null;
        }
        //获取表中的所有列
        List<String> columns = selectColumnList(tableName);
        //查无表中的列
        if (columns == null || columns.isEmpty()) {
            return null;
        }
        //表中的列名与字段列表的交集为有效的查询列
        Set<String> validColumns = Sets.intersection(Sets.newHashSet(columns), Sets.newHashSet(columnList));
        //没有有效的查询列
        if (validColumns.isEmpty()) {
            return null;
        }
        //构建查询语句
        StringBuilder selectSql = new StringBuilder(" select ");
        //构建column语句
        StringBuilder columnSql = new StringBuilder();
        //构建from语句
        StringBuilder fromSql = new StringBuilder(" from ").append(tableName).append(" ");
        //拼接查询列
        for (String column : validColumns) {
            columnSql.append(",").append(column);
        }
        //拼接语句
        selectSql.append(columnSql.toString().replaceFirst(",", "")).append(fromSql);
        if (StringUtils.isNotBlank(whereSql)) {
            selectSql.append(whereSql);
        }
        //查询数据
        List<LinkedHashMap<String, Object>> dataList = getBaseMapper().executeSelectSql(selectSql.toString());
        if (dataList == null || dataList.isEmpty()) {
            return null;
        }
        return dataList;
    }

    /**
     * 根据表名查询表是否存在
     *
     * @param tableName 表名
     * @return
     */
    @Override
    public boolean selectTableExists(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return false;
        }
        String selectSql = "select * from INFORMATION_SCHEMA.TABLES where table_name='" + tableName + "'";
        List<LinkedHashMap<String, Object>> tableList = getBaseMapper().executeSelectSql(selectSql);
        if (tableList == null || tableList.isEmpty()) {
            return false;
        }
        return true;
    }
}