package com.nechet.client.comands;

import com.nechet.client.TCPconnection.Sender;
import com.nechet.client.exceptions.InvokerException;
import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.client.system.UserConsole;
import com.nechet.client.system.Utils;
import com.nechet.common.util.model.Vehicle;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.common.util.requestLogic.Command;
import com.nechet.common.util.requestLogic.RequestArgumentType;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    private final String name = "execute_script";
    private static ArrayDeque<Path> openScriptDeque = new ArrayDeque<>();
    private Sender sender;

    public ExecuteScriptCommand(Sender sender) {
        super("execute_script", RequestArgumentType.STRING);
        this.sender=sender;
    }

    public CommandDescription createDescription(String[] str) throws WrongValuesOfCommandArgumentException, CreateObjectException, ClassNotFoundException {
        UserConsole console = new UserConsole(new Scanner(System.in));
        Invoker invoker = new Invoker();
        Utils.checkArgumentsOrThrow(str.length, 1);
        Path path = Path.of(str[1]);
        try {
            if (openScriptDeque.contains(path)){
                System.out.println("Найдена рекурсия! Файл "+path+" пропущен.");
                return null;
            }
            BufferedReader input = new BufferedReader(new FileReader(path.toFile()));
            UserConsole.onScriptMode();
            openScriptDeque.addLast(path);
            invoker.startToInvoke(input,sender);
            openScriptDeque.removeLast();
            UserConsole.onUserMode();
        } catch (InvokerException e) {
            openScriptDeque.removeLast();
            UserConsole.onUserMode();
            console.printLine("Ошибки во время исполнения команд скрипта. Подробнее:\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            console.printLine("Файл скрипта не найден.");
        }
        CommandDescription description =  new CommandDescription("execute_script",RequestArgumentType.STRING);
        description.setAll(str,Utils.getLogin(),Utils.getPassword());
        return  description;
    }
}
