package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.*;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;
import java.util.Arrays;

public class FilterGreaterThanTypeCommand implements BaseCommand{
    private final String name = "filter_greater_than_type";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription descr){
        result += Server.realisator.FilterGreaterThanType(descr);
    }

    @Override
    public String descr() {
        var types = Arrays.toString(VehicleType.values());
        return "Выводит все элементы коллекции, значение поля type которых больше заданного";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
