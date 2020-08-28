package com.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description 业务实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Page<User> page(User entity) {
        Page<User> page = new Page<>(entity.getPageNum(), entity.getPageSize());
        // 排序
        page.addOrder(OrderItem.asc("id"));
        // 构建查询条件
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
                .like(StrUtil.isNotBlank(entity.getUserName()), User::getUserName, entity.getUserName())
                .eq(User::getState, 0);
        Page<User> result = super.baseMapper.selectPage(page, queryWrapper);
        return result;
    }
}