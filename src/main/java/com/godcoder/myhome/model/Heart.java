package com.godcoder.myhome.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "board_id")
    private long boardId;

    @ManyToOne
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Account account;

}
