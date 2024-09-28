package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

public class AddCommand implements BaseCommand{
    private final String name = "add";
    private String result = "";

    @Override
    public void execute(CommandDescription descr) {

        result += Server.realisator.Add(descr);
    }
    public String getResult(){
        return result;
    }

    @Override
    public String descr() {
        return "Добавляет объект в коллекцию";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
