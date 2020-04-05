package com.example.controller;

import com.example.config.MapConfig;
import com.example.type.HttpState;
import com.example.util.ArrayUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/12 15:40
 * @description
 */
@RequestMapping("base")
@Controller
public class BaseController {

    @Autowired
    private MapConfig config;

    @Getter // 将类对象转化成json需要get方法
    @Setter // 将json转化成类对象需要set方法
    @Builder
    static class User implements Serializable {
        private Long id;
        private String name;
        private Byte age;
    }

    @ModelAttribute("staff")
    /**
     * key为staff 未设置注解value值则key为user 相当于把类名转驼峰作为键名
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

    @GetMapping
    public String index(Model model, User user) {
        User author = (User) model.getAttribute("author");
        model.addAttribute("data", new HashMap() {{
            for (int i = 0; i < 100; i++) {
                put(i, i);
            }
        }});
        model.addAttribute("config", config);
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

    @GetMapping("forward1")
    public String forward1() {
        // 被forwoard的方法请求方法必须一致 GET不能转发POST
        return "forward:"; // => /base
        // return "forward:forward2";
        // return "forward:/base/forward2";
    }

    @ResponseBody
    @GetMapping("forward2")
    public String forward2() {
        return "forward";
    }

    @GetMapping("redirect1")
    public String redirect1() {
        // 被redirect的方法请求类型必须为GET
        return "redirect:"; // => /base/
        // return "redirect:redirect2";
        // return "redirect:/base/redirect2";
    }

    @ResponseBody
    @GetMapping("redirect2")
    public String redirect2() {
        return "redirect";
    }

    /**
     * 接收所有form表单name为array的值
     */
    @PostMapping("array")
    public String[] array(String[] array) {
        return array;
    }

    /**
     * 接收json并转为array 接收多个参数时 只能有一个参数可使用@RequestBody
     */
    @PostMapping("json-array")
    public String[] jsonArray(@RequestBody String[] array) {
        return array;
    }

    @PostMapping("list")
    public List<User> list(@RequestBody List<User> list) {
        return list;
    }

    @PostMapping("arrays")
    public String[] arrays(String[] array1, String[] array2) {
        return (String[]) ArrayUtil.arrayMerge1(array1, array2);
    }

    @ResponseBody
    @GetMapping("enum1")
    public HttpState enum1() {
        return HttpState.OK;
    }

    // http://localhost:8080/base/enum1?state=OK
    @ResponseBody
    @GetMapping("enum2")
    public HttpState enum2(HttpState state) {
        return state;
    }

    @GetMapping("semantic")
    public String semantic() {
        return "semantic";
    }
}