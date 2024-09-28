package com.nechet.client.comands;

import com.nechet.client.Creators.NonUSerCreators.VehicleNonUserCreator;
import com.nechet.client.Creators.UserCreators.VehicleCreator;
import com.nechet.client.TCPconnection.Sender;
import com.nechet.client.exceptions.CommandManagerException;
import com.nechet.client.exceptions.UnknownCommandException;
import com.nechet.client.system.UserConsole;
import com.nechet.client.system.Utils;
import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.Vehicle;
import com.nechet.common.util.requestLogic.Command;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.common.util.requestLogic.Requests.AnswerRequests;
import com.nechet.common.util.requestLogic.Requests.CommandRequest;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Scanner;

import static com.nechet.common.util.requestLogic.RequestArgumentType.*;
import static com.nechet.common.util.requestLogic.RequestArgumentType.NO_ARGS;

public class SignManager {
    private LinkedHashMap<String, Command> commands;
    private Sender sender;
    private UserConsole console = new UserConsole(new Scanner(System.in));

    public SignManager(Scanner scanner,Sender sender)
    {
        this.sender=sender;
        this.commands = new LinkedHashMap<>();

        commands.put("sign_in",new Command("sign_in",STRING));
        commands.put("sign_up", new Command("sign_up",STRING));
    }
    public void executer(String line) throws CommandManagerException, WrongValuesOfCommandArgumentException, ClassNotFoundException {
        try {
            String[] token = line.trim().split(" ");
            String[] tokens = new String[2];
            System.out.println(Arrays.toString(token));
            if (token.length == 3){
                tokens[0] = token[0];
                tokens[1] = token[1]+" "+token[2];
            } else {
                throw new CommandManagerException("Неверное количество аргументов");
            }
            System.out.println(Arrays.toString(tokens));
            Command command = Optional.ofNullable(commands.get(tokens[0])).orElseThrow(() -> new UnknownCommandException("Указанная команда не была обнаружена."));
            System.out.println(Utils.getLogin() + Utils.getPassword());
            CommandDescription descr = command.createDescription(tokens, Utils.getLogin(),Utils.getPassword());
            System.out.println(Arrays.toString(tokens));

            CommandRequest request = new CommandRequest(descr);
            AnswerRequests answer = sender.sendRequest(request);
            System.out.println(answer.getContainer());
            if (answer.getContainer().contains("Пользователь успешно подключен")){
                Utils.setLogin(token[1]);
                Utils.setPassword(token[2]);
            }
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
