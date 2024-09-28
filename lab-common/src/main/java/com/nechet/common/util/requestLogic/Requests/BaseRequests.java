package com.nechet.common.util.requestLogic.Requests;

import java.io.Serializable;

public abstract class BaseRequests<T> implements Serializable {
    private T container;
    public T getContainer(){
        return  container;
    }
}