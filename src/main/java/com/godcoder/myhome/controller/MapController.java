package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.QHosp;
import com.godcoder.myhome.model.QSidoFixed;
import com.godcoder.myhome.repository.HospRepository;
import com.godcoder.myhome.repository.SidoFixedRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map")
public class MapController {

    @Autowired
    private JPAQueryFactory jqf;

    @Autowired
    private HospRepository hospRepository;

    @Autowired
    private SidoFixedRepository sidoFixedRepository;

    @GetMapping("/maps")
    public String map(Model model){
        QHosp qHosp = QHosp.hosp;
        QSidoFixed qSidoFixed = QSidoFixed.sidoFixed;
        BooleanBuilder builder = new BooleanBuilder();


        return "map/maps";
    }

}




