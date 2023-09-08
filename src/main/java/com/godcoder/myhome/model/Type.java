package com.godcoder.myhome.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long type_id;

    private String type_name;


}
