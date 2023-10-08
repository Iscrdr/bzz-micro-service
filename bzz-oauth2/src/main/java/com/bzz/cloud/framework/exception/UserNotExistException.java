package com.bzz.cloud.framework.exception;

import lombok.Getter;

@Getter
public class UserNotExistException extends RuntimeException{

    private String id;

    public UserNotExistException( String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
