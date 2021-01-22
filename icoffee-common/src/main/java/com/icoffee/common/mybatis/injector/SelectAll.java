package com.icoffee.common.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @Name SelectAll
 * @Description 自定义selectAll方法
 * @Author chenly
 * @Create 2019-12-11 13:56
 */
public class SelectAll extends AbstractMethod {

    /**
     * 注入自定义方法
     *
     * @param mapperClass baseMapper类
     * @param modelClass  实体类
     * @param tableInfo   表相关信息
     * @return
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        /* 执行 SQL ，动态 SQL 参考类 SqlMethod */
        String sql = "select * from " + tableInfo.getTableName();
        /* mapper 接口方法名一致 */
        String method = "selectAll";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, method, sqlSource, tableInfo);
    }
}