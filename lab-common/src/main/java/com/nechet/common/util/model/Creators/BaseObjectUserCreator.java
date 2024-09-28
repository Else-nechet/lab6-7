package com.nechet.common.util.model.Creators;

import com.nechet.common.util.exceptions.CreateObjectException;

import java.io.Serializable;

public interface BaseObjectUserCreator<T> extends Serializable {
    public T create () throws CreateObjectException;
}
