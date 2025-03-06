package com.example.deligo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    REQUEST_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "요청값 검증에 실패했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일로 가입한 계정이 존재합니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "해당 닉네임으로 가입한 계정이 존재합니다."), // 닉네임 중복 예외 추가
    ACTIVE_ORDER_EXISTS(HttpStatus.BAD_REQUEST, "진행 중인 주문이 있어 요청을 처리할 수 없습니다."), // 주문 관련 예외 추가,
    MENU_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 메뉴입니다."),
    ALREADY_ORDERED(HttpStatus.BAD_REQUEST, "승인된 주문은 취소할 수 없습니다. 가게에 연락해주세요."),
    ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 주문입니다."),

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "로그인 정보가 올바르지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "토큰이 제공되지 않았습니다."),
    TOKEN_SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, "토큰 서명이 유효하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다."),

    NO_PERMISSION_ACTION(HttpStatus.FORBIDDEN, "권한이 없는 작업입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,  "해당 사용자를 찾을 수 없습니다."),
    //최대 가게 수 초과 예외 타입
    MAX_STORE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "최대 가게 수를 초과했습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),
    OWNER_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사장님 댓글을 찾을 수 없습니다."),

    REVIEW_CONDITION_NOT_MET(HttpStatus.BAD_REQUEST, "리뷰를 작성할 수 있는 상태가 아닙니다."),
    REVIEW_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 주문에 대한 리뷰가 이미 존재합니다."),
    REVIEW_ALREADY_HAS_COMMENT(HttpStatus.BAD_REQUEST, "해당 리뷰에 이미 사장님 댓글이 존재합니다."),
    INVALID_RATING(HttpStatus.BAD_REQUEST, "별점은 1~5점 사이여야 합니다."),
    EMPTY_REVIEW_CONTENT(HttpStatus.BAD_REQUEST, "리뷰 내용은 비어 있을 수 없습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

