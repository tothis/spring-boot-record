package com.example.repository;

import com.example.model.User;
import com.example.model.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/16 22:50
 * @description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findUsersByUserName(String userName, Pageable pageable);

    @Query(value = "SELECT new com.example.model.UserVO(u, a) FROM User u, Address a WHERE u.addressId = a.id")
    List<UserVO> findAllUserVO();
}