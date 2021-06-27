package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

/**
 * @author 李磊
 */
@Controller
public class TestController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("data", new HashMap() {{
            for (int i = 0; i < 100; i++) {
                put(i, i);
            }
        }});
        model.addAttribute("value", "value");
        return "index";
    }
}
