package com.example.deligo.order.entity;

public enum OrderStatus {
    COMPLETED, //추가
    ORDER_RECEIVED, //주문 들어간 상태. 승인은 아직 안됨
    ACCEPTED, //주문 승인
    COOKING, //조리중
    DELIVERING, //배달중
    DELIVERED, //배달 완료
    CANCELED, //주문 취소
    PICKUP_WAITING //픽업 준비 완료(손님이 가져가야하는 상태)
}
