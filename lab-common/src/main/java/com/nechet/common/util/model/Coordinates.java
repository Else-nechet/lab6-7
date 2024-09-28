package com.nechet.common.util.model;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private Float x;
    private Long y;

    public Coordinates() {
    }

    public void makeCord(Float x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates cord = (Coordinates) o;
        return Double.compare(cord.x, this.x) == 0 && this.y == cord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x: " + x +
                ", y: " + y +
                '}';
    }
}
