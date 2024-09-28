package com.nechet.server.system;

import com.nechet.common.util.model.Vehicle;
import com.nechet.common.util.model.comparators.VehicleEnginePowerComparator;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

public class VehiclesManager implements CollectionReceiver<LinkedList<Vehicle>, Vehicle> {
    static private VehiclesManager collection;
    public static  VehiclesManager getInstance(){
        if (collection == null){
            collection = new VehiclesManager();
        }
        return collection;
    }
    private CopyOnWriteArraySet<Vehicle> vehicles;
    private final Date initDate;

    private VehiclesManager() {
        vehicles = new CopyOnWriteArraySet<>();
        initDate = Date.from(Instant.now());
    }

    @Override
    public LinkedList<Vehicle> getCollection() {
        return new LinkedList<>(vehicles);
    }

    @Override
    public void setCollection(LinkedList<Vehicle> value) {
        this.vehicles = new CopyOnWriteArraySet<>(value);
    }

    @Override
    public void addElementToCollection(Vehicle value) {
        this.vehicles.add(value);
        baseSort();
    }

    @Override
    public void clearCollection() {
        this.vehicles.clear();
    }

    @Override
    public void baseSort() {
        CopyOnWriteArraySet<Vehicle> sortedVehicles = new CopyOnWriteArraySet<>();

        for (Iterator<Vehicle> obj = vehicles.stream().sorted(new VehicleEnginePowerComparator()).iterator(); obj.hasNext(); ) {
            Vehicle sortedItem = obj.next();

            sortedVehicles.add(sortedItem);
        }

        this.vehicles = sortedVehicles;
    }


    @Override
    public Date getInitDate() {
        return initDate;
    }

    public int getSize(){
        return vehicles.size();
    }
    public  Vehicle getMinElement(Comparator<Vehicle> comparator){
        return getCollection().stream().min(comparator).orElse(null);
    }
    public Vehicle getMaxElement(Comparator<Vehicle> comparator){
        return getCollection().stream().max(comparator).orElse(null);
    }

    public boolean removeIf(Predicate<? super Vehicle> predicate) {
        return vehicles.removeIf(predicate);
    }

    @Override
    public boolean isEmpty() {
        return vehicles.isEmpty();
    }
}
