package com.nechet.server.fileManipulation;

import java.io.IOException;

public interface WriteToObject<T>{
    public void write (String path, T obj) throws IOException;
}
