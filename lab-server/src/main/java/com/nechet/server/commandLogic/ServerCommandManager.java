package com.nechet.server.commandLogic;

import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.common.util.requestLogic.Requests.AnswerRequests;
import com.nechet.server.commandLogic.comands.*;
import com.nechet.server.commandLogic.comands.BaseCommand;
import com.nechet.server.exception.CommandManagerException;
import com.nechet.server.exception.UnknownCommandException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;


public class ServerCommandManager {
    private LinkedHashMap<String, BaseCommand> commands;

    public ServerCommandManager()
    {
        this.commands = new LinkedHashMap<>();


        commands.put("exit",new ExitCommand());
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("clear", new ClearCommand());
        commands.put("head", new HeadCommand());
        commands.put("filter_greater_than_weapon_type", new FilterGreaterThanTypeCommand());
        commands.put("filter_less_than_engine_power", new FilterLessThanEnginePowerCommand());
        commands.put("add", new AddCommand());
        commands.put("update", new UpdateByIdCommand());
        commands.put("remove_greater", new RemoveGreaterCommand());
        commands.put("count_less_than_type", new CountLessThanTypeCommand());
        commands.put("execute_script",new ExecuteScriptCommand());

    }
    public AnswerRequests executer(CommandDescription descr) throws CommandManagerException {
        try {
            ArrayList<String> tokens = descr.getArgs();
            BaseCommand command = Optional.ofNullable(commands.get(tokens.get(0))).orElseThrow(() -> new UnknownCommandException("Указанная команда не была обнаружена."));
            command.execute(descr);
            return new AnswerRequests(command.getResult());
        }catch (WrongValuesOfCommandArgumentException e){
            throw new CommandManagerException("Не верное колличество аргументов: "+e.getMessage());
        }catch (UnknownCommandException e){
            throw new CommandManagerException(e.getMessage());
        }catch (NullPointerException e) {
            throw new CommandManagerException("Введена пустая строка.");
        } catch (FileNotFoundException e) {
            throw new CommandManagerException("Файл не найден: "+e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public LinkedHashMap<String,BaseCommand> getAllCommands(){
        return this.commands;
    }
}
