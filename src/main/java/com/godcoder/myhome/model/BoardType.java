package com.godcoder.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class BoardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long type_id;

    private String type_name;

}
