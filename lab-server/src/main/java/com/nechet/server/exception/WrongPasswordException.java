package com.nechet.server.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(){
        super("Пароль неверный");
    }
}
