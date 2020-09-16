package com.example.controller;

import com.example.pojo.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李磊
 * @since 1.0
 */
@RequestMapping("user")
@RestController
public class UserController {

    private final MongoTemplate mongoTemplate;

    public UserController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 添加用户
     */
    @PostMapping
    public void save(@RequestBody User user) {
        Long id = user.getId();
        if (id == null) {
            mongoTemplate.save(user);
        } else {
            // 先确定要更新的对象
            Criteria criteria = Criteria.where("id").is(id);
            // 定义更新对象
            Update update = Update.update("userName", user.getUserName());
            // updateFirst 只更新第一个 updateMulti 更新匹配到的多个
            mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
        }
    }

    /**
     * 根据id获取用户
     */
    @GetMapping("{id}")
    public User get(@PathVariable Long id) {
        return mongoTemplate.findById(id, User.class);
    }

    /**
     * 根据条件查询符合条件的用户
     */
    @GetMapping("list")
    public List<User> list(
            @RequestParam(defaultValue = "null") String userName
            , @RequestParam(defaultValue = "0") Long skip
            , @RequestParam(defaultValue = "10") Integer limit) {

        Criteria criteria = Criteria
                // 模糊匹配
                .where("userName").regex(userName);
        // 构建查询条件 最多返回多少条记录 以及跳过多少条记录
        Query query = Query.query(criteria).limit(limit).skip(skip);
        return mongoTemplate.find(query, User.class);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("{id}")
    public DeleteResult deleteUser(@PathVariable Long id) {
        Criteria criteria = Criteria.where("id").is(id);
        return mongoTemplate.remove(Query.query(criteria), User.class);
    }
}