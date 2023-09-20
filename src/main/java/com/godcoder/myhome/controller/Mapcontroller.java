package com.godcoder.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map")
public class Mapcontroller {

    @GetMapping("/maps")
    public String map(Model model){
        return "map/maps";
    }

}
