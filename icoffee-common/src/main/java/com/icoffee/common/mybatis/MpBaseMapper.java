package com.icoffee.common.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icoffee.common.domain.BaseDomain;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
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


    /**
     * 执行原生更新sql
     *
     * @param sql SQL语句
     */
    @UpdateProvider(type = CustomSqlProvider.class, method = "executeSql")
    void executeUpdateSql(@Param(value = "sql") String sql);

    /**
     * 执行原生插入sql
     *
     * @param sql SQL语句
     */
    @InsertProvider(type = CustomSqlProvider.class, method = "executeSql")
    void executeInsertSql(@Param(value = "sql") String sql);

    /**
     * 执行原生查询sql
     *
     * @param sql SQL语句
     * @return 查询结果列表
     */
    @SelectProvider(type = CustomSqlProvider.class, method = "executeSql")
    @ResultType(LinkedHashMap.class)
    List<LinkedHashMap<String, Object>> executeSelectSql(@Param(value = "sql") String sql);

    /**
     * 执行原生查询sql
     *
     * @param sql
     * @return
     */
    @SelectProvider(type = CustomSqlProvider.class, method = "executeSql")
    @ResultType(Long.class)
    long executeSelectCount(@Param(value = "sql") String sql);
}