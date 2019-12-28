package com.example.listener;

import com.example.annotation.AutoIncKey;
import com.example.pojo.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 保存文档监听类<br>
 * 在保存对象时 通过反射方式为其生成id
 * <p>
 * ClassName: SaveEventListener
 * </p>
 * <p>
 * Copyright: (c)2019 lilei,All rights reserved.
 * </p>
 *
 * @author 李磊
 * @time 2019/11/10 18:00
 */
@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
                @Override
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    ReflectionUtils.makeAccessible(field);
                    // 如果字段添加了我们自定义的AutoIncKey注解
                    if (field.isAnnotationPresent(AutoIncKey.class)) {
                        // 设置自增id
                        field.set(source, String.valueOf(getNextId(source.getClass().getSimpleName())));
                    }
                }
            });
        }
    }

    /**
     * 获取下一个自增id
     *
     * @param collName 集合这里用类名 就唯一性来说最好还是存放类全名
     * @return 序列值
     */
    private long getNextId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("sequenceId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        Sequence sequence = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        return sequence.getSequenceId();
    }
}