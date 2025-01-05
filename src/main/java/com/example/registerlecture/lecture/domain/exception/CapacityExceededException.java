package com.example.registerlecture.lecture.domain.exception;

public class CapacityExceededException extends IllegalStateException{
    public CapacityExceededException() {
        super("정원이 초과되었습니다.");
    }
}
