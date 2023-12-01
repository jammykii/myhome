package com.godcoder.myhome.repository;

import com.godcoder.myhome.dto.MapDTO;
import com.godcoder.myhome.model.QHosp;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class MapRepositoryImpl implements MapRepositoryCustom{

    @Autowired
    private JPAQueryFactory jqf;

    @Override
    public Page<MapDTO> searchPageComplex(Pageable pageable, String values, String sido, String clNm) {
        List<MapDTO> content = getHospListDTO( pageable, values, sido, clNm);

        Long count = getCount(values, sido, clNm);

        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String values, String sido, String clNm) {
        QHosp qHosp = QHosp.hosp;
        Long count = jqf
                .select(qHosp.count())
                .from(qHosp)
                .where(eqValues(values)
                        ,eqSido(sido),eqClNm(clNm))
                .fetchOne();
        return count;
    }

    private List<MapDTO> getHospListDTO(Pageable pageable, String values, String sido, String clNm) {
        QHosp qHosp = QHosp.hosp;
        List<MapDTO> content = jqf
                .select(Projections.constructor(MapDTO.class,
                        qHosp.id,qHosp.ykiho,qHosp.yadmNm,qHosp.clNm,qHosp.sggNm,qHosp.addr,qHosp.telNo))
                .from(qHosp)
                .where(eqValues(values)
                        ,eqSido(sido),eqClNm(clNm))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qHosp.id.asc())
                .fetch();
        return content;
    }

    private BooleanExpression eqValues(String values){
        QHosp qHosp = QHosp.hosp;
        if (StringUtils.isEmpty(values) ) {
            return null;
        }
        return qHosp.yadmNm.contains(values)
                .or(qHosp.sggNm.contains(values))
                .or(qHosp.addr.contains(values))
                .or(qHosp.telNo.contains(values));
    }

    private BooleanExpression eqSido(String sido){
        QHosp qHosp = QHosp.hosp;
        if (StringUtils.isEmpty(sido)||"all".contentEquals(sido)) {
            return null;
        }
        return qHosp.sidoNm.eq(sido);
    }

    private BooleanExpression eqClNm(String clNm){
        QHosp qHosp = QHosp.hosp;
        if (StringUtils.isEmpty(clNm)||"all".contentEquals(clNm)) {
            return null;
        }
        return qHosp.clNm.eq(clNm);
    }


    public List<?> getClNmList(){
        QHosp qHosp = QHosp.hosp;
        return jqf
                .selectDistinct(qHosp.clNm)
                .from(qHosp)
                .orderBy(qHosp.clNm.desc())
                .fetch();
    }

    public List<?> getSidoList(){
        QHosp qHosp = QHosp.hosp;
        return jqf
                .selectDistinct(qHosp.sidoNm)
                .from(qHosp)
                .orderBy(qHosp.sidoNm.asc())
                .fetch();
    }

}
