package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

public class FilterLessThanEnginePowerCommand implements BaseCommand {
    private final String name = "filter_less_than_engine_power";

    private String result = "";

    public String getResult() {
        return result;
    }

    @Override
    public void execute(CommandDescription descr) {
        result += Server.realisator.FilterLessThanEnginePower(descr);
    }

    @Override
    public String descr() {
        return " engine_power - выводит элементы, значение поля enginePower которых меньше заданного";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
