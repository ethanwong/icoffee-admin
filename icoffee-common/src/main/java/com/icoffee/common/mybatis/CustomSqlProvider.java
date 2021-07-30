package com.icoffee.common.mybatis;

import java.util.Map;

/**
 * @Name CustomSqlProvider
 * @Description 自定义SQL适配器
 * @Author huangyingfeng
 * @Create 2020-05-21 17:34
 */
public class CustomSqlProvider {

    /**
     * 返回sql语句
     *
     * @param params
     * @return
     */
    public static String executeSql(Map params) {
        Object sql = params.get("sql");
        if (sql == null) {
            return "";
        }
        return sql.toString();
    }
}
