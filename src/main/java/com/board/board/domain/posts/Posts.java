package com.board.board.domain.posts;

import com.board.board.domain.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(/*access = AccessLevel.PROTECTED*/) //뭐였더라
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue //뭐였더라
    @Column(name = "posts_id")
    private Long id;

    @NotNull
    private String title;

    private String content;

    // private 사진 동영상 파일
    
    @NotNull
    private String writer;

    @Builder
    public Posts(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
