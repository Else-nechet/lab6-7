package com.nechet.server.connectionLogic;

import com.nechet.common.util.requestLogic.Requests.BaseRequests;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer<V,W> {
    public <T extends BaseRequests<V>> ByteBuffer serializeObject(T response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(response);
        oos.close();
        ByteBuffer buffer = ByteBuffer.allocate(baos.size());
        buffer.put(baos.toByteArray());
        return buffer;
    }

    public <T extends BaseRequests<W>> T deserializeObject(ByteBuffer buffer) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}