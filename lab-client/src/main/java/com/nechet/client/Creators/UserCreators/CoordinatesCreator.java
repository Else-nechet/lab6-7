package com.nechet.client.Creators.UserCreators;

import com.nechet.client.exceptions.ConsoleReadException;
import com.nechet.common.util.model.Coordinates;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.checkers.CoordinateYChecker;
import com.nechet.client.system.UserConsole;

import java.util.Scanner;

public class CoordinatesCreator implements BaseObjectUserCreator<Coordinates> {
    private final Scanner out;
    public CoordinatesCreator (){
        out = new Scanner(System.in);
    }
    @Override
    public Coordinates create() {
        Coordinates coordinates = new Coordinates();
        UserConsole console = new UserConsole(out);
        while (true) {
            try {
                console.printLine("Введите значение координаты x");
                float value = 0;
                String line = console.read();
                if (line!= null)
                    value = Float.parseFloat(line);
                else{
                    console.printLine("Значение числа должно быть не null. Попробуйте заново.");
                    continue;
                }
                coordinates.setX(value);
            } catch (NumberFormatException e) {
                console.printLine("Неправильный ввод числа. Попробуйте заново.");
                continue;
            }catch (ConsoleReadException e){
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }
        while (true) {
            try {
                CoordinateYChecker Ycheck = new CoordinateYChecker();
                console.printLine("Введите значение координаты y");
                int value = 0;
                String line = console.read();
                if (line!=null)
                    value = Integer.parseInt(line);
                else {
                    console.printLine("Значение числа должно быть не null. Попробуйте заново.");
                    continue;
                }
                if (Ycheck.check(value)) {
                    coordinates.setY(value);
                }
                else{
                    console.printLine("Значение числа должно быть <58. Попробуйте заново.");
                    continue;
                }
            } catch (NumberFormatException e) {
                console.printLine("Неправильный ввод. Попробуйте заново.");
                continue;
            }catch (ConsoleReadException e){
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }
        return coordinates;
    }
}
