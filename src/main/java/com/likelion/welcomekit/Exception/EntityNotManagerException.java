package com.likelion.welcomekit.Exception;

public class EntityNotManagerException extends RuntimeException {
    public EntityNotManagerException(Long userId){
        super("user가 Manager가 아닙니다. userId: "+userId);
    }
    public EntityNotManagerException(String userName){
        super("user가 Manager가 아닙니다. userName: "+userName);
    }
}
