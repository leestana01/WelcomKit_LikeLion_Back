package com.likelion.welcomekit.Exception;

public class EntityDuplicatedException extends RuntimeException{
    public EntityDuplicatedException(Long userId){
        super(userId+"에 대한 요청이 기존의 조건과 일치합니다.");
    }
}
