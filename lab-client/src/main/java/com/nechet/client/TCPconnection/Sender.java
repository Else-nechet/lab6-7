package com.nechet.client.TCPconnection;



import com.nechet.client.exceptions.ClosureFailedException;
import com.nechet.common.util.requestLogic.Requests.AnswerRequests;
import com.nechet.common.util.requestLogic.Requests.CommandRequest;

import java.io.*;

import java.net.SocketException;
import java.util.Objects;

public class Sender {
    private final TCPclient client;

    public Sender(TCPclient client) {
        this.client = client;
    }


    public AnswerRequests sendRequest(CommandRequest request) throws IOException, ClassNotFoundException {
        if(Objects.equals(request.getContainer().getName(), "exit")){
            try{
                sendCommand(request);
                client.close();
                System.exit(0);
            } catch (ClosureFailedException e) {
                return new AnswerRequests("fail close");
            }
        }
        sendCommand(request);
        //try {
            return (AnswerRequests) recieveObject();
        //} catch (IOException | ClassNotFoundException e) {
            //throw new SocketException();
        //}

    }

    public  void sendCommand(CommandRequest request)  {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.close();

            sendData(baos.toByteArray());
        }
        catch(SocketException e){
            System.out.println("Сервер не запущен!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendData(byte[] bytes) throws IOException {
        client.getOutputStream().write(bytes);

    }

    private Object recieveObject() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(this.client.getInputStream());
        return objectInputStream.readObject();
    }
}