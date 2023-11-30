package com.godcoder.myhome.controller;

import com.godcoder.myhome.dto.MapDTO;
import com.godcoder.myhome.model.Hosp;
import com.godcoder.myhome.model.QHosp;
import com.godcoder.myhome.model.QSidoFixed;
import com.godcoder.myhome.repository.HospRepository;
import com.godcoder.myhome.repository.MapRepositoryImpl;
import com.godcoder.myhome.repository.SidoFixedRepository;
import com.godcoder.myhome.util.MapUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/map")
public class MapController {

    @Autowired
    private JPAQueryFactory jqf;

    @Autowired
    private HospRepository hospRepository;

    @Autowired
    private SidoFixedRepository sidoFixedRepository;

    @Autowired
    private MapUtil mapUtil;

    @Autowired
    private MapRepositoryImpl mapRepository;

    @GetMapping("/maps")
    public String map(Model model, Pageable pageable,
                      @RequestParam(required = false, defaultValue = "") String values, @RequestParam(required = false) String clNm){
        QHosp qHosp = QHosp.hosp;
        QSidoFixed qSidoFixed = QSidoFixed.sidoFixed;
        BooleanBuilder builder = new BooleanBuilder();

        List<?> clNmList = jqf
                .selectDistinct(qHosp.clNm)
                .from(qHosp)
                .orderBy(qHosp.clNm.desc())
                .fetch();

        Page<MapDTO> hospList = null;
        if(values != null && !values.isBlank()){
            builder.or(qHosp.yadmNm.contains(values));
            builder.or(qHosp.sggNm.contains(values));
            builder.or(qHosp.addr.contains(values));
            builder.or(qHosp.telNo.contains(values));
        }
        if(clNm != null && !"all".contentEquals(clNm)){
            builder.and(qHosp.clNm.eq(clNm));
        }

        hospList = mapRepository.searchPageComplex(pageable, builder);
        int startPage = Math.max(1, hospList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(hospList.getTotalPages(),hospList.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("hosp", hospList);
        model.addAttribute("clNm", clNmList);
        return "map/maps";
    }

}