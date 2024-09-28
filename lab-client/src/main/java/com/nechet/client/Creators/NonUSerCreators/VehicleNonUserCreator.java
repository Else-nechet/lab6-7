package com.nechet.client.Creators.NonUSerCreators;

import com.nechet.client.system.UserConsole;
import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.model.Coordinates;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.FuelType;
import com.nechet.common.util.model.Vehicle;
import com.nechet.common.util.model.VehicleType;
import com.nechet.common.util.model.checkers.Checked;
import com.nechet.common.util.model.checkers.NameChecker;
import com.nechet.common.util.model.checkers.VehicleEnginePowerChecker;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

public class VehicleNonUserCreator implements BaseObjectUserCreator<Vehicle> {
    private Date initDate;
    private final Scanner out;
    public VehicleNonUserCreator (Scanner scanner){
        out = scanner;
    }
    @Override
    public Vehicle create() throws CreateObjectException {
        try {
            Vehicle vehicle = new Vehicle();
            initDate = Date.from(Instant.now());
            vehicle.setCreationDate(initDate);
            UserConsole console = new UserConsole(out);
            String line = console.read();
            Checked<String> nameCheck = new NameChecker();
            if (nameCheck.check(line)) {
                vehicle.setName(line);
            } else {
                throw new CreateObjectException("Значение строки должно быть не пустым и не null.");
            }
            BaseObjectUserCreator<Coordinates> cordCreator = new CoordinatesNonUserCreator(out);
            vehicle.setCoordinates(cordCreator.create());
            Checked<Long> powerCheck = new VehicleEnginePowerChecker();
            line = console.read();
            long value = Long.parseLong(line);
            if (powerCheck.check(value)) {
                vehicle.setPower(value);
            } else {
                throw new CreateObjectException("Значение числа должно быть >0.");
            }
            line = console.read();
            VehicleType type = VehicleType.valueOf(line);
            vehicle.setType(type);
            line = console.read();
            FuelType fuel = FuelType.valueOf(line);
            vehicle.setFuel(fuel);
            return vehicle;
        } catch (Exception e) {
            throw new CreateObjectException(e.getMessage());
        }
    }
}