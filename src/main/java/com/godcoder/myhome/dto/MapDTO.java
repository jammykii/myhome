package com.godcoder.myhome.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapDTO {

    private long id;
    private String ykiho;
    private String yadmNm;
    private String clNm;
    private String sggNm;
    private String addr;
    private String telNo;

    public MapDTO(long id,String ykiho,String yadmNm, String clNm, String sggNm, String addr, String telNo) {
        this.id = id;
        this.ykiho = ykiho;
        this.yadmNm = yadmNm;
        this.clNm = clNm;
        this.sggNm = sggNm;
        this.addr = addr;
        this.telNo = telNo;
    }
}
