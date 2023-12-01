package com.godcoder.myhome.repository;

import com.godcoder.myhome.dto.MapDTO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MapRepositoryCustom {
    Page<MapDTO> searchPageComplex(Pageable pageable, String values, String sido, String clNm);

}
