package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.User;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description 业务
 */
public interface IUserService extends IService<User> {
    /**
     * @param entity 实体对象
     * @return 插入成功记录数
     * @description 插入一条记录
     */
    int insert(User entity);

    /**
     * @param id 主键id
     * @return 删除成功记录数
     * @description 根据id删除
     */
    int deleteById(Long id);

    /**
     * @param id 主键id
     * @return 实体
     * @description 根据id查询
     */
    User selectById(Long id);
}
