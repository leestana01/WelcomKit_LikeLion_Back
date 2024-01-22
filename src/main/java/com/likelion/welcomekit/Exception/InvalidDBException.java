package com.likelion.welcomekit.Exception;

public class InvalidDBException extends RuntimeException{
    public InvalidDBException(){
        super("알수 없는 오류 발생. DB가 손상되었습니다.");
    }
}
