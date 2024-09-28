package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.Server;

public class ExecuteScriptCommand implements BaseCommand {
    private final String name = "execute_script";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription descr) {
        result += Server.realisator.ExecuteScript(descr);
    }

    @Override
    public String descr() {
        return "Вызывает скрипт из файла";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
