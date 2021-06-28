package com.example.controller;

import com.example.config.MapConfig;
import com.example.util.ArrayUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 */
@RequestMapping("test")
@Controller
public class TestController extends BaseController {

    private final MapConfig config;

    public TestController(MapConfig config) {
        this.config = config;
    }

    @ModelAttribute("staff")
    /**
     * key 为 staff，未设置注解 value 值则 key 为 user 相当于把类名转驼峰作为键名
     * 当前类的 controller 可直接在参数或 model 中获取
     */
    private User data(Long id, Byte age) {
        return User.builder()
                .id(id)
                .name("name" + id)
                .age(age)
                .build();
    }

    /**
     * key 为 author
     * 当前类的 controller 只可在 model 中获取
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
                // 直接返回 JSON 方式
                return new ModelAndView(new MappingJackson2JsonView()) {{
                    addObject("name", "name4");
                }};
        }
    }

    @GetMapping("forward1")
    public String forward1() {
        // 被 forwoard 的方法请求方法必须一致，get 不能转发 post
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
     * 接收所有 form 表单 name 为 array 的值
     */
    @PostMapping("array")
    public String[] array(String[] array) {
        return array;
    }

    /**
     * 接收 JSON 并转为数组（接收多个参数时，只能有一个参数可使用 @RequestBody）
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
    public HttpStatus enum1() {
        return HttpStatus.OK;
    }

    // http://localhost:8080/test/enum2?state=404
    @ResponseBody
    @GetMapping("enum2")
    public HttpStatus enum2(HttpStatus state) {
        return state;
    }

    @GetMapping("semantic")
    public String semantic() {
        return "semantic";
    }

    /**
     * 使用 @RequestParam 把参数封装为 Map，required 为 true 时参数也可不传
     *
     * @param params -
     * @return -
     */
    @ResponseBody
    @GetMapping("find-map1")
    public Map<String, Object> findMap1(@RequestParam(required = false) Map<String, Object> params) {
        return params;
    }

    /**
     * 使用 @RequestBody 把 JSON 参数封装为 Map
     *
     * @param params -
     * @return -
     */
    @ResponseBody
    @PostMapping("find-map2")
    public Map<String, Object> findMap2(@RequestBody Map<String, Object> params) {
        return params;
    }

    /**
     * 映射请求头信息
     *
     * @param userAgent -
     * @param accept    -
     */
    @GetMapping("header")
    public void header(
            @RequestHeader("user-agent") String userAgent
            // 请求头name与参数名称一致会自动注入 自动转为集合
            , @RequestHeader List<String> accept
    ) {
        System.out.println(userAgent);
        accept.forEach(System.out::println);
    }

    /**
     * 将类对象转化成 JSON 需要 get 方法
     * 将 JSON 转化成类对象需要 set 方法
     */
    @Getter
    @Setter
    @Builder
    static class User implements Serializable {
        private Long id;
        private String name;
        private Byte age;
    }
}
