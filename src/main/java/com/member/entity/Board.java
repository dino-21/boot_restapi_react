package com.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 게시글 번호

    private String title;     // 제목

    @Column(columnDefinition = "TEXT")
    private String content;   // 문의글 내용

    private String writer;    // 작성자 이름 또는 아이디

    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 등록일시
}
