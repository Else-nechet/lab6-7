package com.nechet.client.Creators.UserCreators;

import com.nechet.client.exceptions.ConsoleReadException;
import com.nechet.client.system.UserConsole;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.FuelType;
import com.nechet.common.util.model.Vehicle;
import com.nechet.common.util.model.VehicleType;
import com.nechet.common.util.model.checkers.NameChecker;
import com.nechet.common.util.model.checkers.VehicleEnginePowerChecker;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class VehicleCreator implements BaseObjectUserCreator<Vehicle> {
    private Date initDate;
    private final Scanner out;
    public VehicleCreator (){
        out = new Scanner(System.in);
    }
    @Override
    public Vehicle create() {
        Vehicle vehicle = new Vehicle();
        initDate = Date.from(Instant.now());
        vehicle.setCreationDate(initDate);
        UserConsole console = new UserConsole(out);
        console.printLine("Начинаем создание транспорта:");
        while(true){
            try {
                console.printLine("Введите название");
                String line = console.read();
                NameChecker nameCheck = new NameChecker();
                if (nameCheck.check(line)) {
                    vehicle.setName(line);
                } else {
                    console.printLine("Значение строки должно быть не пустым и не null. Попробуйте заново.");
                    continue;
                }
            } catch (ConsoleReadException e) {
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }
        CoordinatesCreator cordCreator = new CoordinatesCreator();
        vehicle.setCoordinates(cordCreator.create());
        while (true) {
            try {
                VehicleEnginePowerChecker powerCheck = new VehicleEnginePowerChecker();
                console.printLine("Введите мощность");
                long value;
                String line = console.read();
                if (line!=null)
                    value = Long.parseLong(line);
                else {
                    console.printLine("Значение числа должно быть не null. Попробуйте заново.");
                    continue;
                }
                if (powerCheck.check(value)) {
                    vehicle.setPower(value);
                }
                else{
                    console.printLine("Мощность должна быть больше 0. Попробуйте заново.");
                    continue;
                }
            } catch (NumberFormatException e) {
                console.printLine("Неправильный ввод числа. Попробуйте заново.");
                continue;
            } catch (ConsoleReadException e){
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }

        String types = Arrays.toString(VehicleType.values());
        while (true) {
            try {
                VehicleType type;
                console.printLine("Введите один из типов транспорта на выбор из предложенных:");
                console.printLine(types);
                String line = console.read();
                if (line!=null)
                    type = VehicleType.valueOf(line);
                else {
                    console.printLine("Строка не должна быть null. Попробуйте заново.");
                    continue;
                }
                vehicle.setType(type);
            } catch (IllegalArgumentException|EnumConstantNotPresentException e) {
                console.printLine("Нет такого варианта. Попробуйте заново.");
                continue;
            }catch (ConsoleReadException e){
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }
        String types2 = Arrays.toString(FuelType.values());
        while (true) {
            try {
                FuelType type;
                console.printLine("Введите один из типов топлива на выбор из предложенных:");
                console.printLine(types2);
                String line = console.read();
                if (line!=null)
                    type = FuelType.valueOf(line);
                else {
                    console.printLine("Строка не должна быть null. Попробуйте заново.");
                    continue;
                }
                vehicle.setFuel(type);
            } catch (IllegalArgumentException|EnumConstantNotPresentException e) {
                console.printLine("Нет такого варианта. Попробуйте заново.");
                continue;
            }catch (ConsoleReadException e){
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }
        console.printLine("Успех!");
        return vehicle;
    }
}
