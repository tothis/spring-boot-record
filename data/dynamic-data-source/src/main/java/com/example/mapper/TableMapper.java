package com.example.mapper;

import com.example.model.Table;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TableMapper {

    @Select("SELECT id, url, user_name AS userName, password FROM `table`")
    List<Table> findAll();
}