package com.nechet.common.util.model.comparators;

import com.nechet.common.util.model.Vehicle;
import com.nechet.common.util.model.VehicleType;

import java.util.Comparator;

public class VehicleTypeComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getType().compareTo(o2.getType());
    }
}