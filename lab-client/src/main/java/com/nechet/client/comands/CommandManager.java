package com.nechet.client.comands;

import com.nechet.client.TCPconnection.Sender;
import com.nechet.client.exceptions.CommandManagerException;
import com.nechet.client.system.Utils;
import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.client.exceptions.UnknownCommandException;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.requestLogic.CommandDescription;
import static com.nechet.common.util.requestLogic.RequestArgumentType.*;

import com.nechet.common.util.requestLogic.Command;
import com.nechet.common.util.requestLogic.Requests.AnswerRequests;
import com.nechet.common.util.requestLogic.Requests.CommandRequest;
import com.nechet.client.Creators.NonUSerCreators.VehicleNonUserCreator;
import com.nechet.client.Creators.UserCreators.VehicleCreator;
import com.nechet.common.util.model.Vehicle;
import com.nechet.client.system.UserConsole;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Scanner;

public class CommandManager {
    private LinkedHashMap<String, Command> commands;
    private Sender sender;
    private UserConsole console = new UserConsole(new Scanner(System.in));

    public CommandManager(Scanner scanner,Sender sender)
    {
        this.sender=sender;
        this.commands = new LinkedHashMap<>();

        commands.put("exit",new Command("exit",NO_ARGS));
        commands.put("help", new Command("help",NO_ARGS));
        commands.put("head", new Command("head",NO_ARGS));
        commands.put("info", new Command("info",NO_ARGS));
        commands.put("show", new Command("show",NO_ARGS));
        commands.put("remove_by_id", new Command("remove_by_id",LONG));
        commands.put("clear", new Command("clear",NO_ARGS));
        commands.put("remove_first", new Command("remove_first",NO_ARGS));
        commands.put("filter_greater_than_type", new Command("filter_greater_than_type",TYPE));
        commands.put("filter_less_than_engine_power", new Command("filter_less_than_engine_power",LONG));
        commands.put("count_less_than_type", new Command("count_less_than_type",TYPE));

        BaseObjectUserCreator<Vehicle> vehicleCreator;
        if (UserConsole.whatsMode()) {
            vehicleCreator = new VehicleCreator();
        } else {
            vehicleCreator = new VehicleNonUserCreator(scanner);
        }
        commands.put("add", new Command("add",NO_ARGS,vehicleCreator,1));
        commands.put("update", new Command("update",LONG,vehicleCreator,1));
        commands.put("remove_greater", new Command("remove_greater",NO_ARGS,vehicleCreator,1));
        commands.put("execute_script",new ExecuteScriptCommand(sender));

    }
    public void executer(String line) throws CommandManagerException, ClassNotFoundException {
        try {
            String[] tokens = line.trim().split(" ");
            Command command = Optional.ofNullable(commands.get(tokens[0])).orElseThrow(() -> new UnknownCommandException("Указанная команда не была обнаружена."));
            CommandDescription descr = command.createDescription(tokens, Utils.getLogin(),Utils.getPassword());

            CommandRequest request = new CommandRequest(descr);
            AnswerRequests answer = sender.sendRequest(request);
            console.printLine(answer.getContainer());
        }catch (WrongValuesOfCommandArgumentException e){
            throw new CommandManagerException("Не верное колличество аргументов: "+e.getMessage());
        }catch (UnknownCommandException e){
            throw new CommandManagerException(e.getMessage());
        }catch (NullPointerException e) {
            throw new CommandManagerException("Введена пустая строка");
        } catch (CreateObjectException e) {
            throw new CommandManagerException("Проблемы во время создания объекта: "+e.getMessage());
        } catch (SocketException e){
            throw new CommandManagerException("Сервер упал -\\__/-\n Выйдете и перезагрузитесь через время");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
