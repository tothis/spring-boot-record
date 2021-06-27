package com.example;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest(classes = ElasticsearchApplication.class)
class ElasticsearchApplicationTests {

    private UserRepository repository;
    private RestHighLevelClient client;

    @Test
    void contextLoads() throws IOException {
        repository.deleteAll();
        User user = new User();
        for (int i = 0; i < 10; i++) {
            user.setName("李磊" + i);
            user.setDate(new Date());
            user.setLocalDate(LocalDate.now());
            user.setLocalDateTime(LocalDateTime.now());

            repository.save(user);
            System.out.println(user);
            user.setDate(null);

            // 更新部分字段
            UpdateRequest updateRequest = new UpdateRequest("user", user.getId());
            updateRequest.doc(user, XContentType.SMILE);
            client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(repository.findById(user.getId()));
        }
        System.out.println("all user");
        // userRepository.findAll().forEach(System.out::println);
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }
}
