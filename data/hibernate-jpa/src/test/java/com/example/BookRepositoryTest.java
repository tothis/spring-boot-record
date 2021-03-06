package com.example;

import com.example.model.Book;
import com.example.model.BookUser;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.BookUserRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 多对多映射
 *
 * @author 李磊
 */
@SpringBootTest(classes = HibernateJpaApplication.class)
class BookRepositoryTest {

    private BookRepository bookRepository;

    private UserRepository userRepository;

    private BookUserRepository bookUserRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBookUserRepository(BookUserRepository bookUserRepository) {
        this.bookUserRepository = bookUserRepository;
    }

    @BeforeEach
    void before() {
        User user1 = new User();
        user1.setUserName("one");
        User user2 = new User();
        user2.setUserName("two");
        userRepository.save(user1);
        userRepository.save(user2);

        Book book1 = new Book("book1");
        Book book2 = new Book("book2");
        bookRepository.save(book1);
        bookRepository.save(book2);

        bookUserRepository.save(new BookUser(book1.getId(), user1.getId()));
        bookUserRepository.save(new BookUser(book2.getId(), user1.getId()));
        bookUserRepository.save(new BookUser(book1.getId(), user2.getId()));
        bookUserRepository.save(new BookUser(book2.getId(), user2.getId()));
    }

    @AfterEach
    void after() {
        bookUserRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void main() {
        assertEquals(bookRepository.findAll().size(), 2);
        assertEquals(userRepository.findAll().size(), 2);

        List<String[]> books = bookRepository.findByNameContent("book1");
        for (String[] book : books) {
            for (int i = 0; i < book.length; i++) {
                System.out.print(book[i] + (i == book.length - 1 ? " " : ""));
            }
            System.out.println();
        }
    }
}
