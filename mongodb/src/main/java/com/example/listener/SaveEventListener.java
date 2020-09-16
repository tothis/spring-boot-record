package com.example.listener;

import com.example.annotation.AutoIncKey;
import com.example.pojo.Sequence;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * 保存文档监听类
 *
 * @author 李磊
 * @datetime 2019/11/10 18:00
 * @description 保存对象时 通过反射方式为其生成id
 */
@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

    private final MongoTemplate mongoTemplate;

    public SaveEventListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(), field -> {
                ReflectionUtils.makeAccessible(field);
                // 如果字段添加了我们自定义的AutoIncKey注解
                if (field.isAnnotationPresent(AutoIncKey.class)) {
                    // 设置自增id
                    field.set(source, this.nextId(source.getClass().getName()));
                }
            });
        }
    }

    /**
     * 获取下一个自增id
     *
     * @param collName 全类名
     * @return 序列值
     */
    private long nextId(String collName) {
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