package com.board.board.domain.dto;

import com.board.board.domain.posts.Posts;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*
기본 생성자 생성, Entity 나 Dto 에 주로 사용, 프록시 때 뭐시기 때문에 있어야 함. 외부 생성 안되게 protected (DTO에서도??)
그런데 @Builder 와 @NoArgsConstructor 같이 쓰면 컴파일 에러. 모든 필드 가지는 생성자 만들어야 함.
직접 만들거나 @AllArgsConstructor 사용해야.
*/
public class PostsSaveRequestDto {

    @NotNull
    private String title;
    private String content;
    @NotNull
    private String writer;

    public PostsSaveRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    @Builder //??
    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
