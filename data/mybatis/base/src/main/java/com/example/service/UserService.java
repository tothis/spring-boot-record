package com.example.service;

import com.example.model.Inner;
import com.example.model.Tree;
import com.example.model.User;
import com.example.type.State;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 李磊
 */
public interface UserService {
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return 插入成功记录数
     */
    int insert(User entity);

    /**
     * 根据id删除
     *
     * @param id 主键ID
     * @return 删除成功记录数
     */
    int deleteById(Long id);

    /**
     * 根据id查询
     *
     * @param id 主键ID
     * @return 实体
     */
    Optional<User> selectById(Long id);

    List<User> findAll(User User);

    Map<Long, User> findMap(Map params);

    String arrayTest(String[] values);

    List<Tree> tree(Long parentId);

    int insertEnum(State state);

    State findEnum();

    String inner(Inner inner);
}