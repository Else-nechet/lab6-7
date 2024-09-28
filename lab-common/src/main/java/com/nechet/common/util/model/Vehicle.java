package com.nechet.common.util.model;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.util.Date;
import java.util.Objects;

public class Vehicle implements Serializable, Comparable<Vehicle> {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private long enginePower;
    private VehicleType type;
    private FuelType fuelType;

    public Vehicle() {
    }

    public Coordinates getCord() {
        return this.coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setPower(long enginePower) {
        this.enginePower = enginePower;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public void setFuel(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public VehicleType getType() {
        return this.type;
    }

    public long getPower() {
        return this.enginePower;
    }

    public long getId() {
        return this.id;
    }

    public FuelType getFuel() {
        return this.fuelType;
    }

    public String toString() {
        String var10000 = this.name;
        return "Name: " + var10000 + " Coordinates: x:" + this.coordinates.getX() + " y: " + this.coordinates.getY() + " Power: " + this.enginePower + " Type: " + this.type + " Fuel: " + this.fuelType + " ID:" + this.id + " Inicialization date: " + this.creationDate;
    }

    public String toSave() {
        String var10000 = this.name;
        return var10000 + "," + this.coordinates.getX() + "," + this.coordinates.getY() + "," + this.enginePower + "," + this.type + "," + this.fuelType + ",";
    }

    public int compareTo(Vehicle o) {
        return o.getType().compareTo(this.type);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return  Objects.equals(this.name, vehicle.name) && this.coordinates.equals(vehicle.coordinates)
                && (this.creationDate == vehicle.creationDate) && this.enginePower == vehicle.enginePower
                && this.type.equals(vehicle.type) && this.fuelType.equals(vehicle.fuelType);
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}