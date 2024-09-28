package com.nechet.common.util.requestLogic.Requests;

import com.nechet.common.util.requestLogic.CommandDescription;

public class CommandRequest extends BaseRequests<CommandDescription> {
    private CommandDescription container;
    public CommandRequest(CommandDescription req){
        container=req;
    }
    public CommandDescription getContainer(){
        return  container;
    }
}
