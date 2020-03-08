package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.Tree;
import com.example.model.User;
import com.example.service.UserService;
import com.example.type.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public boolean booleanString(String value) {
        return userMapper.booleanString(value);
    }

    @Override
    public boolean booleanInt(int value) {
        return userMapper.booleanInt(value);
    }

    @Override
    public String arrayTest(String[] values) {
        return userMapper.arrayTest(values);
    }

    @Override
    public List<Tree> findAllTreeByParentId1(Long parentId) {
        return userMapper.findAllTreeByParentId(parentId);
    }

    @Override
    public List<Tree> findAllTreeByParentId2(Long parentId) {
        return trees(parentId, userMapper.findAllTree());
    }

    // 获取顶层节点
    private List<Tree> trees(Long parentId, List<Tree> trees) {
        List<Tree> treeList = new ArrayList<>();
        for (Tree tree : trees) {
            if (tree.getParentId().equals(parentId)) {
                tree.setTrees(tree(tree.getId(), trees));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    // 递归获取下级节点
    private List<Tree> tree(Long parentId, List<Tree> trees) {
        List<Tree> treeList = new ArrayList<>();
        for (Tree tree : trees) {
            if (tree.getParentId().equals(parentId)) {
                tree.setTrees(tree(tree.getId(), trees));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    @Override
    public int insertEnum(State state) {
        return userMapper.insertEnum(state);
    }

    @Override
    public State findEnum() {
        return userMapper.findEnum();
    }
}