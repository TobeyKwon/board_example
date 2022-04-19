package com.example.board.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter @ToString
public class Board {

    @Id @GeneratedValue
    @Column(name = "id")
    private Integer boardId;
    private String title;
    private String content;
}
