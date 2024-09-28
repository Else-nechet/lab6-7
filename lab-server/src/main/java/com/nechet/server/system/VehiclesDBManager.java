package com.nechet.server.system;

import com.nechet.common.util.model.Vehicle;
import com.nechet.server.databaseLogic.RelationshipsDAO;
import com.nechet.server.databaseLogic.VehicleDAO;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

public class VehiclesDBManager implements CollectionReceiver<LinkedList<Vehicle>, Vehicle>{
    static private VehiclesManager collection;
    private String login;

    public VehiclesDBManager(String login) {
        collection = VehiclesManager.getInstance();
        this.login = login;
    }
    @Override
    public LinkedList<Vehicle> getCollection() throws SQLException {
        VehicleDAO vehicles = new VehicleDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        collection.setCollection(new LinkedList<Vehicle>(vehicles.getAllVehicles()));
        return collection.getCollection();
    }

    @Override
    public void setCollection(LinkedList<Vehicle> value) throws SQLException {
        VehicleDAO vehicles = new VehicleDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        clearCollection();
        collection.clearCollection();
        for (Vehicle val:value){
            vehicles.saveVehicle(val);
            collection.addElementToCollection(val);
        }
    }

    @Override
    public void addElementToCollection(Vehicle value) throws SQLException {
        VehicleDAO vehicles = new VehicleDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        RelationshipsDAO rel = new RelationshipsDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        long id = vehicles.saveVehicle(value);
        value.setId(id);
        rel.addRelationship(id,login);
        collection.addElementToCollection(value);

    }

    @Override
    public void clearCollection() throws SQLException {
        VehicleDAO vehicles = new VehicleDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        vehicles.deleteVehicle(login,"id > 0");
        collection.setCollection(new LinkedList<>(vehicles.getAllVehicles()));

    }
    public  boolean update(long id, Vehicle vehicle) throws SQLException {
        VehicleDAO vehicles = new VehicleDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        return vehicles.updateId(login,id, vehicle);
    }

    @Override
    public int getSize() {
        return collection.getSize();
    }

    @Override
    public void baseSort() {
        collection.baseSort();
    }

    @Override
    public Date getInitDate() {
        return collection.getInitDate();
    }

    @Override
    public Vehicle getMaxElement(Comparator<Vehicle> comp) {
        return collection.getMaxElement(comp);
    }

    @Override
    public Vehicle getMinElement(Comparator<Vehicle> comp) {
        return collection.getMinElement(comp);
    }

    public boolean removeIf(String partSqlRequest) throws SQLException {
        VehicleDAO vehicles = new VehicleDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        if (vehicles.deleteVehicle(login,partSqlRequest)){
            LinkedList<Vehicle> vehicle = new LinkedList<>(vehicles.getAllVehicles());
            collection.setCollection(vehicle);
            return  true;
        } else { return false;}
    }
    public boolean removeId(long id) throws SQLException {
        VehicleDAO vehicles = new VehicleDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        if (vehicles.deleteVehicleId(login,id)){
            LinkedList<Vehicle> vehicle = new LinkedList<>(vehicles.getAllVehicles());
            collection.setCollection(vehicle);
            return  true;
        } else { return false;}
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }
}
