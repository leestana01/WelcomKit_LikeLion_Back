package com.likelion.welcomekit.Exception;

public class EntityNotBabyLionException extends RuntimeException{
    public EntityNotBabyLionException(Long userId){
        super("user가 아기사자가 아닙니다. userId: "+userId);
    }
    public EntityNotBabyLionException(String userName){
        super("user가 아기사자가 아닙니다. userName: "+userName);
    }
}