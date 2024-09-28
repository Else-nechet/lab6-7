package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;
public class ClearCommand implements BaseCommand{
    private final String name = "clear";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription descr) {

        result += Server.realisator.Clear(descr);

    }

    @Override
    public String descr() {
        return "Очищает коллекцию";
    }

    @Override
    public String getName() {
        return this.name;
    }

}
