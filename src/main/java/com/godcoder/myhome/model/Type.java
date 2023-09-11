package com.godcoder.myhome.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private long typeId;

    @Column(name = "type_name")
    private String typeName;


}
