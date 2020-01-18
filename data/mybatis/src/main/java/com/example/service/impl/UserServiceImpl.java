package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description 业务实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @param entity 实体对象
     * @return 插入成功记录数
     * @description 插入一条记录
     */
    @Override
    public int insert(User entity) {
        return userMapper.insert(entity);
    }

    /**
     * @param id 主键id
     * @param id 主键id
     * @return 实体
     * @description 根据id查询
     */
    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> findAll(User user) {
        return userMapper.findAll(user);
    }

    /**
     * @param id 主键id
     * @return 删除成功记录数
     * @description 根据id删除
     */
    @Override
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Map<Long, User> findMap(Map params) {
        return userMapper.findMap(params);
    }
}