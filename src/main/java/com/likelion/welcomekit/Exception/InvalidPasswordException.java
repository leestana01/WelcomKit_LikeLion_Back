package com.likelion.welcomekit.Exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String username){
        super(username+"의 비밀번호가 일치하지 않습니다.");
    }
}
