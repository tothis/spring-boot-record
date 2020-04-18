package com.example.repository;

import com.example.model.Role;
import com.example.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/20 11:44
 * @description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH)
    User findByUserName(String userName);

    List<Role> findRolesById(Long userId);

    /**
     * @Modifying用于使用@Query修改或删除数据时 使jpa将操作加入事务
     * 不使用@Query 直接用deleteBy+条件 并不需要使用@Modifying
     */
    @Modifying
    @Query("UPDATE User SET state = 2 WHERE userName = ?1")
    int lockUser(String userName);

    @Modifying
    @Query("UPDATE User SET state = 0 WHERE userName = ?1")
    int unlockUser(String userName);
}