package com.example.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/2/27 22:24
 * @description
 */
@RequestMapping("restful")
@Controller
public class RestfulController {

    private final RestTemplate restTemplate;

    public RestfulController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static List<User> users = new ArrayList();

    @GetMapping
    private String get(Model model) {
        model.addAttribute("users", users);
        model.addAttribute("city", restTemplate.getForEntity("http://pv.sohu.com/cityjson", String.class).getBody());
        return "restful";
    }

    @PostMapping("post")
    private String post(User user) {
        users.add(user);
        return "redirect:";
    }

    @PutMapping("put")
    private String put(User user) {
        for (User item : users) {
            if (item.getId().equals(user.getId()))
                item.setName(user.getName());
        }
        return "redirect:";
    }

    @DeleteMapping("delete/{id}")
    private String delete(@PathVariable Long id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
        return "redirect:/restful";
    }

    @Data
    static class User {
        private Long id;
        private String name;
    }
}