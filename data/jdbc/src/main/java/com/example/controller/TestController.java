package com.example.controller;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:09
 * @description jdbcTemplate使用
 */
@RestController
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String select = "SELECT id, user_name FROM user";
    private final String insert = "INSERT INTO user (user_name) VALUES (?)";
    private final String update = "UPDATE user SET user_name = ? WHERE id = ?";
    private final String delete = "DELETE FROM user WHERE id = ?";

    @GetMapping("select")
    public List<User> select() {
        return jdbcTemplate.query(select
                , (ResultSet resultSet, int rowNum) ->
                        // 映射每行数据
                        new User() {{
                            setId(resultSet.getLong("id"));
                            setUserName(resultSet.getString("user_name"));
                        }}
        );
    }

    @GetMapping("insert/{userName}")
    public int insert(@PathVariable String userName) {
        return jdbcTemplate.update(insert
                , (PreparedStatement preparedStatement) ->
                        preparedStatement.setString(1, userName)
        );
    }

    @GetMapping("update")
    public int update(Long id, String userName) {
        return jdbcTemplate.update(update
                , (PreparedStatement preparedStatement) -> {
                    preparedStatement.setString(1, userName);
                    preparedStatement.setLong(2, id);
                }
        );
    }

    @GetMapping("delete/{id}")
    public int delete(@PathVariable Long id) {
        return jdbcTemplate.update(delete
                , (PreparedStatement preparedStatement) ->
                        preparedStatement.setLong(1, id)
        );
    }
}