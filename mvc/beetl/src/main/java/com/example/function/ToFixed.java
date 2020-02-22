package com.example.function;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.math.BigDecimal;

/**
 * @author 李磊
 * @datetime 2020/2/18 23:13
 * @description 自定义beetl函数 toFixed转化浮点类型
 */
public class ToFixed implements Function {
    @Override
    public Object call(Object[] values, Context context) {
        if (values[0] == null) return null;
        BigDecimal bigDecimal = new BigDecimal(values[0].toString());
        return bigDecimal.setScale(Integer.parseInt(values[1].toString()), BigDecimal.ROUND_HALF_EVEN);
    }
}