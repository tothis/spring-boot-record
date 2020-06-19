package com.example.util;

import com.example.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }
}