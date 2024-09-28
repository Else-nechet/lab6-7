package com.nechet.server.commandLogic;

import com.nechet.common.util.model.Vehicle;
import com.nechet.common.util.model.VehicleType;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.Utils;
import com.nechet.server.system.VehiclesManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandsRealisator {

    private String filter = "";
    public String Add(CommandDescription descr){
        CollectionReceiver<LinkedList<Vehicle>, Vehicle> colMan = VehiclesManager.getInstance();
        Vehicle vehicle = descr.getObjectArray().get(0);
        vehicle.setId(Utils.getNewId());
        try {
            colMan.addElementToCollection(vehicle);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Транспорт успешно добавлен в коллекцию";
    }

    public String Clear(CommandDescription descr){
        VehiclesManager colMan = VehiclesManager.getInstance();
        colMan.clearCollection();
        return "Коллекция была очищена.";
    }

    public String CountLessThanType(CommandDescription descr){
        LinkedList<Vehicle> coll = VehiclesManager.getInstance().getCollection();
        ArrayList<String> str = descr.getArgs();

        try {
            VehicleType type = VehicleType.valueOf(str.get(1));
            if (coll.isEmpty()) {
                return "В коллекции нет объектов";
            } else {
                AtomicInteger count = new AtomicInteger();
                coll.stream().filter(vh -> type.ordinal() > vh.getType().ordinal()).forEach(vh -> count.addAndGet(1));
                return count.toString();
            }
        } catch (IllegalArgumentException | EnumConstantNotPresentException e) {
            return "Нет такой переменной. Попробуйте заново.";
        }
    }
    public String ExecuteScript(CommandDescription descr){
        return "Скрипт выполнен";
    }
    public String Exit(CommandDescription descr){
        return "клиент закрыт";
    }
    public String FilterGreaterThanType(CommandDescription descr){
        LinkedList<Vehicle> coll = VehiclesManager.getInstance().getCollection();
        ArrayList<String> str = descr.getArgs();

        try {
            VehicleType type = VehicleType.valueOf(str.get(1));
            if (coll.isEmpty()) {
                return "В коллекции нет объектов";
            } else {
                ;
                coll.stream().filter(obj -> type.ordinal() < obj.getType().ordinal()).forEach(obj -> filter += obj.toString()+"\n");
                String ans = filter;
                filter = "";
                return ans;
            }
        } catch (IllegalArgumentException | EnumConstantNotPresentException e) {
            return "Нет такой переменной. Попробуйте заново.";
        }
    }
    public String FilterLessThanEnginePower(CommandDescription descr){
        ArrayList<String> str = descr.getArgs();
        LinkedList<Vehicle> coll = VehiclesManager.getInstance().getCollection();
        try {
            double health = Double.parseDouble(str.get(1));
            if (coll.isEmpty()) {
                return "В коллекции нет объектов";
            } else {
                coll.stream().filter(obj -> health > obj.getPower()).forEach(obj -> filter += obj.toString() + "\n");
            String ans = filter;
            filter = "";
            return ans;
            }
        } catch (NumberFormatException e) {
            return "Не правильный ввод переменной engine power. Попробуйте заново.";
        }
    }
    public String Head(CommandDescription descr){
        LinkedList<Vehicle> coll = VehiclesManager.getInstance().getCollection();
        try {
            return coll.getFirst().toString();
        } catch (Exception e){
            return "Kоллекция пуста";
        }

    }
    public String Help(CommandDescription descr){
        ServerCommandManager manager = new ServerCommandManager();
        manager.getAllCommands().forEach((name,command) -> filter+=name  + ": " + command.descr()+"\n");
        String ans = filter;
        filter = "";
        return ans;
    }
    public String Info(CommandDescription descr){
        var colMan = VehiclesManager.getInstance();
        return"Тип коллекции: "+colMan.getCollection().getClass()+"\n"+
                "Дата создания: "+colMan.getInitDate().toString()+"\n"+
                "Количество элементов: "+colMan.getSize()+"\n";
    }
    public String RemoveById(CommandDescription descr){
        VehiclesManager colMan = VehiclesManager.getInstance();
        LinkedList<Vehicle> coll = colMan.getCollection();
        ArrayList<String> str = descr.getArgs();
        long id;
        try{
            id = Long.parseLong(str.get(1));
            if(!coll.removeIf(marine -> Objects.equals(marine.getId(), id)))
            {
                return "Элемента с таким Id коллекции нет";
            }
            else {
                colMan.setCollection(coll);
                return "Успешно удалено!";
            }
        } catch (NumberFormatException e) {
            return "Неправильный ввод Id. Попробуйте заново.";
        }
    }
    public String RemoveGreater(CommandDescription descr){
        LinkedList<Vehicle> coll = VehiclesManager.getInstance().getCollection();
        Vehicle newVehicle = descr.getObjectArray().get(0);
        newVehicle.setId(Utils.getNewId());
        coll.removeIf(vh -> newVehicle.getPower() < vh.getPower());
        return "Удалены все объекты больше полученного";
    }
    public String Show(CommandDescription descr){
        LinkedList<Vehicle> coll = VehiclesManager.getInstance().getCollection();
        if (coll.isEmpty()){
            return "В коллекции нет объектов";
        } else {
            coll.forEach(obj -> filter+=obj.toString()+"\n");
        }
        String ans = filter;
        filter = "";
        return ans;
    }
    public String UpdateById(CommandDescription descr){
        VehiclesManager colMan = VehiclesManager.getInstance();
        LinkedList<Vehicle> coll = colMan.getCollection();
        ArrayList<String> str = descr.getArgs();
        long id;
        try{
            id = Long.parseLong(str.get(1));
            if(!coll.removeIf(vh -> Objects.equals(vh.getId(), id)))
            {
                return "Элемента с таким Id коллекции нет";
            }
            else{
                Vehicle update = descr.getObjectArray().get(0);
                update.setId(id);
                coll.add(update);
                colMan.setCollection(coll);
                return "Успех!";
            }
        } catch (NumberFormatException e) {
            return "Неправильный ввод значения Id. Попробуйте заново.";
        }

    }

}
