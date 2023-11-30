package com.godcoder.myhome.repository;

import com.godcoder.myhome.dto.MapDTO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MapRepositoryCustom {
    Page<MapDTO> searchPageComplex(Pageable pageable, BooleanBuilder builder);

}
