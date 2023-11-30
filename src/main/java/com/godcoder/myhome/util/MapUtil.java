package com.godcoder.myhome.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapUtil {

    public Map<String, Integer> getPageInfo(Page<?> pageableObject) {
        int startPage = Math.max(1, pageableObject.getPageable().getPageNumber() - 4);
        int endPage = Math.min(pageableObject.getTotalPages(),pageableObject.getPageable().getPageNumber() + 4);

        int firstPage = 1;
        int lastPage = Math.max(1, pageableObject.getTotalPages());
        int currPage = pageableObject.getPageable().getPageNumber() + 1;
        int beforePage = Math.max(1, pageableObject.getPageable().getPageNumber() - 3);
        int afterPage = Math.min(pageableObject.getTotalPages(), pageableObject.getPageable().getPageNumber() + 5);
        if(afterPage==0) {
            afterPage = 1;
        }
        Map<String, Integer> pageInfo = new HashMap<>();
        pageInfo.put("firstPage", firstPage);
        pageInfo.put("lastPage", lastPage);
        pageInfo.put("currPage", currPage);
        pageInfo.put("beforePage", beforePage);
        pageInfo.put("afterPage", afterPage);
        return pageInfo;
    }

    public Page<?> getPageFromList(Pageable pageable, List<?> list){
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}