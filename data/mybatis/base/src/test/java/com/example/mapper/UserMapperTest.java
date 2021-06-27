package com.example.mapper;

import com.example.MyBatisBaseApplication;
import com.example.model.Tree;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest(classes = MyBatisBaseApplication.class)
class UserMapperTest {

    private UserMapper mapper;

    @Autowired
    public void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void inserts() {
        User[] users = new User[5];
        for (int i = 0; i < users.length; i++) {
            int item = i;
            users[i] = new User() {{
                setName("name" + item);
                setPassword("密码" + item);
            }};
        }
        mapper.inserts(users);
        // 数组或 list 新增或修改都可以返回 ID
        Arrays.stream(users).map(User::getId).forEach(System.out::println);
    }

    @Test
    void findPage() {
        // [[{password=密码0, user_name=name0}, {password=密码1, user_name=name1}], [10]]
        System.out.println(mapper.findPage());
    }

    @Test
    void run() {
        System.out.println(mapper.run());
    }

    @Test
    void ifTest() {
        mapper.ifTest("");
        mapper.ifTest("1");
    }

    @Test
    void booleanTest() {
        System.out.println(mapper.booleanTest("1")); // true
        System.out.println(mapper.booleanTest("0")); // false
    }

    /**
     * 递归获取下级节点
     *
     * @param parentId -
     * @param trees    -
     * @return -
     */
    private List<Tree> tree(Long parentId, List<Tree> trees) {
        List<Tree> result = new ArrayList<>();
        for (Tree tree : trees) {
            if (Objects.equals(tree.getParentId(), parentId)) {
                tree.setTrees(tree(tree.getId(), trees));
                result.add(tree);
            }
        }
        return result;
    }

    @Test
    void treeTest() {
        System.out.println(mapper.dbTree(0L));
        System.out.println(tree(0L, mapper.tree()));
    }

    @Test
    void selectInnerTest() {
        System.out.println(mapper.selectInner());
    }
}
