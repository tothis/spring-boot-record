package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@ToString(exclude = "userList")
@Entity
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String departmentName;

    // 序列化时忽略userList中的每个user的department属性
    @JsonIgnoreProperties("department")
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> userList;
}