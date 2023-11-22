package com.godcoder.myhome.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "hosp_1")
public class Hosp {
    //long = 원시 타입(Primitive Type)
    //Long = 참조 타입(Reference Type)
    //long은 null값 지정 불가 Long은 null값 지정 가능
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ykiho;
    private String yadmNm;
    private Long clCd;
    private String clNm;
    private Long sidoCd;
    private String sidoNm;
    private Long sggCd;
    private String sggNm;
    private String emd;
    private Long postNo;
    private String addr;
    private String telNo;
    private String url;
    private Long estbD;
    private Long drCnt;
    private Long pnursCnt;
    private Long lon;
    private Long lat;


}
