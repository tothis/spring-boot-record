package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.Inner;
import com.example.model.Tree;
import com.example.model.User;
import com.example.service.UserService;
import com.example.type.State;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 李磊
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int insert(User entity) {
        return userMapper.insert(entity);
    }

    @Override
    public Optional<User> selectById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> findAll(User user) {
        return userMapper.findAll(user);
    }

    @Override
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Map<Long, User> findMap(Map params) {
        return userMapper.findMap(params);
    }

    @Override
    public String arrayTest(String[] values) {
        return userMapper.arrayTest(values);
    }

    @Override
    public List<Tree> tree(Long parentId) {
        // return userMapper.dbTree(parentId);
        return tree(parentId, userMapper.tree());
    }

    /**
     * 递归获取下级节点
     *
     * @param parentId -
     * @param trees    -
     * @return -
     */
    private List<Tree> tree(Long parentId, List<Tree> trees) {
        List<Tree> result = new ArrayList<>();
        for (Tree tree : trees) {
            if (Objects.equals(tree.getParentId(), parentId)) {
                tree.setTrees(tree(tree.getId(), trees));
                result.add(tree);
            }
        }
        return result;
    }

    @Override
    public int insertEnum(State state) {
        return userMapper.insertEnum(state);
    }

    @Override
    public State findEnum() {
        return userMapper.findEnum();
    }

    @Override
    public String inner(Inner inner) {
        return userMapper.inner(inner);
    }
}