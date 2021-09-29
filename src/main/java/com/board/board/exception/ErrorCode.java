package com.board.board.exception;

import lombok.Getter;

public enum ErrorCode {

    SUCCESS("E0000", "성공") // 성공
    , ERROR("E9999" ,"서버에러") // 서버에러
    , NO_PERMISSION("E0001", "접근 권한 없음") // 접근 권한 없음
    , INVALID_PARAMETER("E0002", "잘못된 파라미터") // 잘못된 파라미터
    , DATA_NOT_FOUND("E0003", "데이터 없음") // 데이터 없음
    /* end */
    ;

    private String code;

    private String description;

    private ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
