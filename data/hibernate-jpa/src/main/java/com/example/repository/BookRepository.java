package com.example.repository;

import com.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/2/10 21:21
 * @description
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    // nativeQuery = true 指定使用原生SQL进行查询
    @Query(nativeQuery = true
            , value = "SELECT" +
            " b.id" +
            ", b.name" +
            ", GROUP_CONCAT(u.user_name) as userName" +
            " from" +
            " book b, user u, book_user bu" +
            " where" +
            " b.id = bu.book_id" +
            " and u.id = bu.user_id" +
            " and b.name like ?1" +
            " group by b.id, b.name"
    )
    List<String[]> findByNameContent(String name);
}