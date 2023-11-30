package com.godcoder.myhome.repository;

import com.godcoder.myhome.dto.MapDTO;
import com.godcoder.myhome.model.QHosp;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MapRepositoryImpl implements MapRepositoryCustom{

    @Autowired
    private JPAQueryFactory jqf;

    @Override
    public Page<MapDTO> searchPageComplex(Pageable pageable, BooleanBuilder builder) {
        List<MapDTO> content = getMemberTeamDtos( pageable, builder);

        Long count = getCount(builder);

        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(BooleanBuilder builder) {
        QHosp qHosp = QHosp.hosp;
        Long count = jqf
                .select(qHosp.count())
                .from(qHosp)
                .where(builder)
                .fetchOne();
        return count;
    }

    private List<MapDTO> getMemberTeamDtos(Pageable pageable, BooleanBuilder builder) {
        QHosp qHosp = QHosp.hosp;
        List<MapDTO> content = jqf
                .select(Projections.constructor(MapDTO.class,
                        qHosp.id,qHosp.ykiho,qHosp.yadmNm,qHosp.clNm,qHosp.sggNm,qHosp.addr,qHosp.telNo))
                .from(qHosp)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

}
