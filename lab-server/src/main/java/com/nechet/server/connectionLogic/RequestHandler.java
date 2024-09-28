package com.nechet.server.connectionLogic;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.common.util.requestLogic.Requests.AnswerRequests;
import com.nechet.common.util.requestLogic.Requests.CommandRequest;
import com.nechet.server.commandLogic.ServerCommandManager;

import java.nio.ByteBuffer;

public class RequestHandler {

    private Serializer<String, CommandDescription> serializer = new Serializer<>();

    public AnswerRequests handleRequest(ByteBuffer buffer) {
        CommandRequest command;
        try {
            command = serializer.deserializeObject(buffer);
            System.out.println("Получено: " + command.getClass());
            ServerCommandManager manager = new ServerCommandManager();
            System.out.println(command.getContainer().getName());
            System.out.println(command.getContainer().getLogin());
            return manager.executer(command.getContainer());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}