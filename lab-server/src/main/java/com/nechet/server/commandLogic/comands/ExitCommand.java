package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

import java.io.IOException;

public class ExitCommand implements BaseCommand{
    private final String name = "exit";
    private String result = "";

    public String getResult(){
        return result;
    }

        @Override
        public void execute(CommandDescription descr) throws IOException {
            result += Server.realisator.Exit(descr);
        }

        @Override
        public String descr() {
            return "Завершает работу клиента";
        }

        @Override
        public String getName() {
            return this.name;
        }

}
