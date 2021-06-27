package com.example.function;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.math.BigDecimal;

/**
 * 自定义beetl函数 toFixed转化浮点类型
 *
 * @author 李磊
 */
public class ToFixed implements Function {
    @Override
    public Object call(Object[] values, Context context) {
        if (values[0] == null) return null;
        BigDecimal bigDecimal = new BigDecimal(values[0].toString());
        return bigDecimal.setScale(Integer.parseInt(values[1].toString()), BigDecimal.ROUND_HALF_EVEN);
    }
}
