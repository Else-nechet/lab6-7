package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

public class UpdateByIdCommand implements BaseCommand {
    private final String name = "update";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription descr){
        result += Server.realisator.UpdateById(descr);
    }

    @Override
    public String descr() {
        return "Обновляет элемент, Id которого равен указанному.";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
