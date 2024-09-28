package com.nechet.common.util.model;

import java.io.Serializable;

public enum FuelType implements Serializable, Comparable<FuelType> {
    ALCOHOL,
    MANPOWER,
    NUCLEAR,
    PLASMA;

    private FuelType() {
    }
}

