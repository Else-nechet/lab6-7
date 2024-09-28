package com.nechet.common.util.requestLogic;

import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.Vehicle;

public class Command {
    private BaseObjectUserCreator<Vehicle> creator = null;
    private CommandDescription descr;
    private int numb;
    public Command(String name, RequestArgumentType types) {
        descr = new CommandDescription(name,types);
        this.numb = 0;
    }
    public Command(String name, RequestArgumentType types, BaseObjectUserCreator<Vehicle> creator, int numberOfObjects) {
        descr=new CommandDescription(name,types);
        this.creator = creator;
        this.numb = numberOfObjects;
    }
    public CommandDescription createDescription(String[] args,String log,String password) throws WrongValuesOfCommandArgumentException, CreateObjectException {
        descr.setAll(args,log,password);
        for (int i=0;i<numb;i++){
            descr.addObject(creator.create());
        }


        return descr;
    }
}
