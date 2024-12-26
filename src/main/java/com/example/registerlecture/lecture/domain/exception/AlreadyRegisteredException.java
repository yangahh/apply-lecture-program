package com.example.registerlecture.lecture.domain.exception;

public class AlreadyRegisteredException extends IllegalStateException {
    public AlreadyRegisteredException() {
        super("이미 신청한 특강입니다.");
    }
}
