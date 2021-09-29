package com.board.board.exception;

import lombok.Getter;

@Getter
public class ErrorResult { //API 에 내려줄 에러 형태
    private String code; //enum 을 이용하여 에러코드를 정의한 후 내려줄 것이다

    private String description; //어떠한 에러인지 간단히 넣어줄 것이고  ex) 필수값이 누락되었습니다

    private String detail; //에러의 세부내용을 넣어줄 것이다. ex)이름은 필수값입니다

    public ErrorResult(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorResult(String code, String description, String detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
    }
}

//https://cchoimin.tistory.com/entry/Valid-%EC%99%80-ControllerAdvice%EB%A1%9C-DTO-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0
//https://lemontia.tistory.com/416