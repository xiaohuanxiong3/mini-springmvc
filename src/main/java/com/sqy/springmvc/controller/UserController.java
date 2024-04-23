package com.sqy.springmvc.controller;

import org.jalidun.stereotype.Controller;
import org.jalidun.web.bind.annotation.RequestMapping;
import org.jalidun.web.bind.annotation.RequestMethod;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/22 上午10:03
 */
@Controller
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
