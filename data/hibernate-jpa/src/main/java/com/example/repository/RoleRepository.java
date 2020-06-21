package com.example.repository;

import com.example.model.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * 通过@EntityGraph来指定Role类中定义的NamedEntityGraph
     * value省略不写默认为`实体名+方法名` 如下为`Role.findAll`
     *
     * @return
     */
    // @EntityGraph(/*value = "role", */type = EntityGraph.EntityGraphType.FETCH)
    @EntityGraph(value = "Role", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    List<Role> findAll();
}