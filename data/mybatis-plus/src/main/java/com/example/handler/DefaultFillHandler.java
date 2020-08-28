package com.example.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 新增或修改时添加数据
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
public class DefaultFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject o) {
        log.info("开始填充插入数据");
        this.strictInsertFill(o, "state", () -> (byte) 0, Byte.class);
        // this.strictInsertFill(o, "createBy", () -> 1L, Long.class);
    }

    @Override
    public void updateFill(MetaObject o) {
        log.info("开始填充修改数据");
        // this.strictInsertFill(o, "updateBy", () -> 1L, Long.class);
    }
}