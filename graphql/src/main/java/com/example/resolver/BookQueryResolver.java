package com.example.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @time 2019/12/11 22:15
 * @description
 */
@Component
public class BookQueryResolver implements GraphQLQueryResolver {
    public List<Book> books() {
        Author author = Author.builder().id(1).name("李磊").build();
        return new ArrayList<Book>() {{
            for (int i = 0; i < 10; i++) {
                add(Book.builder().id(i).name("name-" + i).author(author).build());
            }
        }};
    }

    @Builder
    static class Author {
        private Integer id;
        private String name;
    }

    @Builder
    static class Book {
        private Integer id;
        private String name;
        private Author author;
    }
}