package com.example.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author 李磊
 * @datetime 2020/1/12 15:40
 * @description
 */
@Controller
public class BaseController {

    @Getter // 将类对象转化成json需要get方法
    @Setter // 将json转化成类对象需要set方法
    @Builder
    static class User implements Serializable {
        private Long id;
        private String name;
        private Byte age;
    }

    @ModelAttribute
    /**
     * key为user
     * 直接返回类 相当于把类名转驼峰作为键名
     * 当前类的controller可直接在参数或model中获取
     */
    private User data(Long id, Byte age) {
        return User.builder()
                .id(id)
                .name("name" + id)
                .age(age)
                .build();
    }

    /**
     * key为author
     * 当前类的controller只可在model中获取
     */
    @ModelAttribute
    private void data(Model model, String name) {
        model.addAttribute("author", User.builder().name("name" + name).build());
    }

    @GetMapping("")
    public String index(Model model, User user) {
        User author = (User) model.getAttribute("author");
        model.addAttribute("data", new HashMap() {{
            for (int i = 0; i < 100; i++) {
                put(i, i);
            }
        }});
        return "index";
    }

    @GetMapping("model-and-view")
    public ModelAndView modelAndView(@RequestParam(defaultValue = "0") int flag) {
        // 使用双花括号语法初始化类 本质为匿名内部类 + 实例化代码块
        switch (flag) {
            case 0:
                // 返回页面方式1
                return new ModelAndView() {{
                    setViewName("index");
                    addObject("name", "name1");
                }};
            case 1:
                // 返回页面方式2
                return new ModelAndView("index") {{
                    addObject("name", "name2");
                }};
            case 2:
                // 返回页面方式3
                return new ModelAndView("index", new HashMap() {{
                    put("name", "name3");
                }});
            default:
                // 直接返回json方式
                return new ModelAndView(new MappingJackson2JsonView()) {{
                    addObject("name", "name4");
                }};
        }
    }

    @GetMapping("semantic")
    public String semantic() {
        return "semantic";
    }
}