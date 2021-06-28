package com.example.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author 李磊
 */
@RequestMapping("restful")
@RestController
public class RestfulController {

    @GetMapping("{id}")
    private void get(@PathVariable Long id) {
    }

    @PostMapping("post")
    private void post(@RequestBody Object o) {
    }

    @PutMapping("put")
    private void put(@RequestBody Object o) {
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
    }
}
