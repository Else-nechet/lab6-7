package com.nechet.client.system;

import com.nechet.client.exceptions.ConsoleReadException;

import java.util.Scanner;

public class UserConsole {
    private static int UserMode = 1;
    private final Scanner scanner;
    public UserConsole(Scanner scanner){
        this.scanner = scanner;
    }
    public static void onScriptMode() {
        UserMode -=1;
    }

    public static void onUserMode() {
        UserMode +=1;
    }
    public  static boolean whatsMode(){
        return UserMode>0;
    }

    public void print(String message) {
        if (whatsMode()) {
            System.out.print(message);
        }
    }

    public void printLine(String message) {
        if (whatsMode()) {
            System.out.println(message);
        }
    }
    public String read() throws ConsoleReadException {
        String line;
        while (true) {
            try {
                line = Utils.isEmptyLine(scanner.nextLine());
            } catch (Exception e) {
                throw new ConsoleReadException("Непредвиденные проблемы с вводом. Это была ошибка:\n"+e.getMessage());
            }
            break;
        }
        return line;
    }
}
