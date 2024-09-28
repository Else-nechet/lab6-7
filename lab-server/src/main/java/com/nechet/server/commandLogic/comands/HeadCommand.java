package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

public class HeadCommand implements BaseCommand {
    private final String name = "help";
    private String result = "";

    public String getResult() {
        return result;
    }

    @Override
    public void execute(CommandDescription descr) {
        result += Server.realisator.Head(descr);
    }


    @Override
    public String descr() {
        return "Показывает описание для каждой команды";
    }

    @Override
    public String getName() {
        return this.name;
    }
}