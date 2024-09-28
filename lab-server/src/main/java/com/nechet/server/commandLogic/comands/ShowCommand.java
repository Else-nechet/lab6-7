package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

public class ShowCommand implements BaseCommand{
    private final String name = "show";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription descr) {
        result += Server.realisator.Show(descr);
    }

    @Override
    public String descr() {
        return "Выводит все элементы коллекции в строковом представлении";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
