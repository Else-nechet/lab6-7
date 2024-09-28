package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;
public class RemoveByIdCommand implements BaseCommand{
    private final String name = "remove_by_id";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription descr) throws WrongValuesOfCommandArgumentException {
        result += Server.realisator.RemoveById(descr);
    }

    @Override
    public String descr() {
        return "Удаляет элемент, Id которого равен указанному.";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
