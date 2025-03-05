package com.example.deligo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    REQUEST_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "요청값 검증에 실패했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일로 가입한 계정이 존재합니다."),

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "로그인 정보가 올바르지 않습니다."),

    NO_PERMISSION_ACTION(HttpStatus.FORBIDDEN, "권한이 없는 작업입니다."),
    ALREADY_ORDERED(HttpStatus.FORBIDDEN, "승인된 주문은 취소할 수 없습니다. 가게에 연락해주세요."),
    ALREADY_CANCELED(HttpStatus.FORBIDDEN, "이미 취소된 주문입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "토큰이 제공되지 않았습니다."),
    TOKEN_SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, "토큰 서명이 유효하지 않습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}