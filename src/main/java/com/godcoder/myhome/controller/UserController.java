package com.godcoder.myhome.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {

    @GetMapping("role")
    public String role(Model model){

        return "user/role";
    }
}
