package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.VehicleType;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

import java.util.Arrays;

public class CountLessThanTypeCommand implements BaseCommand{
    private final String name = "count_less_than_type";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription descr){
        result += Server.realisator.CountLessThanType(descr);
    }

    @Override
    public String descr() {
        var types = Arrays.toString(VehicleType.values());
        return "Выводит количество элементов коллекции, значение поля type которых меньше заданного";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
