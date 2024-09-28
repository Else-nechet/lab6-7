package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

public class RemoveGreaterCommand implements BaseCommand{
    private final String name = "remove_greater";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription descr){
        result += Server.realisator.RemoveGreater(descr);
    }

    @Override
    public String descr() {
        return "Удаляет элементы, большие заданного";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
