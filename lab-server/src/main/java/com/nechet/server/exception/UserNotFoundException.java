package com.nechet.server.exception;

import com.nechet.server.databaseLogic.UserDAO;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("Пользователь не найден");
    }
}
