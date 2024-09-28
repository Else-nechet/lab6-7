package com.nechet.common.util.model;

import java.io.Serializable;

public enum VehicleType implements Serializable, Comparable<VehicleType> {
    CAR,
    DRONE,
    SUBMARINE;

    private VehicleType() {
    }
}
