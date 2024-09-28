package com.nechet.common.util.model.comparators;

import com.nechet.common.util.model.Vehicle;

import java.util.Comparator;

public class VehicleIdComparator implements Comparator<Vehicle>{
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}
