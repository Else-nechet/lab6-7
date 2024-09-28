package com.nechet.client.comands;

import com.nechet.client.TCPconnection.Sender;
import com.nechet.client.exceptions.CommandManagerException;
import com.nechet.client.exceptions.ConsoleReadException;
import com.nechet.client.exceptions.InvokerException;
import com.nechet.client.system.UserConsole;

import java.util.Scanner;

public class Invoker {

    public void startToInvoke (Readable inObj, Sender sender) throws InvokerException, ClassNotFoundException {
        Scanner in = new Scanner(inObj);
        UserConsole console = new UserConsole(in);
        CommandManager com = new CommandManager(in,sender);
        while (in.hasNextLine()) {
            try {
                String line = console.read();
                com.executer(line);
            } catch (ConsoleReadException | CommandManagerException e) {
                throw  new InvokerException("Ошибка: "+e.getMessage());
            }
        }
    }

}
