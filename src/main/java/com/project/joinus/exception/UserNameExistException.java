package com.project.joinus.exception;

public class UserNameExistException extends RuntimeException {
    public UserNameExistException(String s) {
        super(s);
    }
}
