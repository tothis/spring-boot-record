package com.example.repository;

import com.example.model.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookUserRepository extends JpaRepository<BookUser, Long> {
}