package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.requestLogic.CommandDescription;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BaseCommand {
    String result = null;
    public default String getResult(){
        return result;
    }
    public void execute (CommandDescription descr) throws WrongValuesOfCommandArgumentException, IOException;
    public String descr();
    public String getName();
}
