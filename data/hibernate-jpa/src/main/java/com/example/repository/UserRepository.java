package com.example.repository;

import com.example.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/16 22:50
 * @description
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findUsersByUserName(String userName, Pageable pageable);

    @Query("SELECT u FROM User u")
    List<User> findAll();

    /**
     * @Modifying用于使用@Query修改或删除数据时 使jpa将操作加入事务
     * 不使用@Query 直接用deleteBy+条件 并不需要使用@Modifying
     */
    @Modifying
    @Query("UPDATE User SET delFlag = true WHERE userName = ?1")
    int deleteByUserName(String userName);
}