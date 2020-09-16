package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author 李磊
 * @datetime 2020/1/11 18:01
 * @description
 */
@Controller
public class BaseController<M> {

    protected M baseService;

    @Autowired
    public void setBaseService(M baseService) {
        this.baseService = baseService;
    }
}