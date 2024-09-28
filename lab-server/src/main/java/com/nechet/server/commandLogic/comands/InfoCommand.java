package com.nechet.server.commandLogic.comands;


import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;


public class InfoCommand implements BaseCommand{
    private final String name = "info";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription descr) {
        result += Server.realisator.Info(descr);
    }

    @Override
    public String descr() {
        return "Показывает информацию о коллекции(тип, дату создания, количество элементов)";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

