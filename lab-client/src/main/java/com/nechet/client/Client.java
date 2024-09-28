package com.nechet.client;

import com.nechet.client.TCPconnection.Sender;
import com.nechet.client.TCPconnection.TCPclient;
import com.nechet.client.comands.CommandManager;
import com.nechet.client.comands.Invoker;
import com.nechet.client.comands.SignManager;
import com.nechet.client.exceptions.CommandManagerException;
import com.nechet.client.exceptions.ConsoleReadException;
import com.nechet.client.exceptions.InvokerException;
import com.nechet.client.system.UserConsole;
import com.nechet.client.system.Utils;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;


import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) throws CommandManagerException, WrongValuesOfCommandArgumentException, ClassNotFoundException {

        UserConsole console = new UserConsole(new Scanner(System.in));
        TCPclient client = new TCPclient("localhost", 5060);
        Sender sender = new Sender(client);
        Invoker invoker = new Invoker();
        while (true) {
            try {
                client.run();
                var in = new Scanner(System.in);
                SignManager sign = new SignManager(in, sender);

                while (Utils.getLogin().isEmpty()) {
                    try {
                        console.printLine("Для входа введите команду sign_in your_login your_password");
                        console.printLine("Для создания пользователя введите команду sign_up your_login your_password");
                        String line = console.read();
                        sign.executer(line);
                    } catch (CommandManagerException e) {
                        System.out.println("Нельзя выполнять команды кроме этих:");
                    } catch (ConsoleReadException e) {
                        throw new RuntimeException(e);
                    }
                }
                invoker.startToInvoke(new InputStreamReader(System.in), sender);
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    {
                        System.out.println("Получен сигнал завершения работы (Ctrl+D).");
                        System.out.println("Закрываем программу");
                        System.exit(1);
                    }

                    @Override
                    public void run() {
                    }
                });
            } catch (InvokerException e) {
                console.printLine(e.getMessage());
            } catch (ConnectException e) {
                System.out.println("Сервер не запущен! Запустите программу через время");
                System.exit(0);

            } catch (SocketTimeoutException e) {
                System.out.println(e.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
