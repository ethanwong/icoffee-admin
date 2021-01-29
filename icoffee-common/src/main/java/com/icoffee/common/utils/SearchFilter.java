package com.icoffee.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * @Name SearchFilter
 * @Description 条件查询封装类
 * @Author lincy
 * @Create 2020-02-20 10:16
 */
public class SearchFilter {
    //搜索前缀
    public static String prefix = "search_";

    public enum Operator {
        EQ, NE, LIKE, GT, LT, GE, LE, IN, NOTIN, OR,
    }

    public static <T> QueryWrapper<T> buildByHttpRequestList(final ServletRequest request) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        // 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, prefix);

        // 分析参数Map,构造SearchFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            String[] names = StringUtils.split(key, ".");
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);
            // 如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                switch (operator) {
                    case EQ:
                        queryWrapper.eq(filedName, value);
                        break;
                    case NE:
                        queryWrapper.ne(filedName, value);
                        break;
                    case LIKE:
                        queryWrapper.like(filedName, value);
                        break;
                    case GT:
                        queryWrapper.gt(filedName, value);
                        break;
                    case LT:
                        queryWrapper.lt(filedName, value);
                        break;
                    case GE:
                        queryWrapper.ge(filedName, value);
                        break;
                    case LE:
                        queryWrapper.le(filedName, value);
                        break;
                    case IN:
                        queryWrapper.in(filedName, value);
                        break;
                }
            }
        }

        return queryWrapper;
    }
}
