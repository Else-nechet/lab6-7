package com.nechet.server.databaseLogic;

import com.nechet.common.util.model.*;
import com.nechet.server.system.Utils;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class VehicleDAO extends DatabaseConnection {
    private final String URL = Utils.getUrl();
    private final String USERNAME = Utils.getLogin();
    private final String PASSWORD = Utils.getPassword();

    public VehicleDAO(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }

    public Long saveVehicle(Vehicle vehicle) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Vehicles (name, coord_id, creation_date, engine_power, type, fuel_type) " +
                             "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setInt(2, getCoordinateId(vehicle.getCoordinates()));
            preparedStatement.setDate(3, new java.sql.Date(vehicle.getCreationDate().getTime()));
            preparedStatement.setLong(4, vehicle.getPower());
            preparedStatement.setString(5, vehicle.getType().name());
            preparedStatement.setString(6, vehicle.getFuel().name());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getCoordinateId(Coordinates coordinates) {
        System.out.println("X: "+coordinates.getX()+" Y: "+coordinates.getY());
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Coordinates (x, y) VALUES (?,?)")){
            preparedStatement.setDouble(1, coordinates.getX());
            preparedStatement.setLong(2, coordinates.getY());
            preparedStatement.executeUpdate();
            return getLastInsertId(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int getLastInsertId(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastval()")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }
    public boolean updateId(String login, Long id, Vehicle vh) throws SQLException {
        if(deleteVehicleId(login,id)){
            return false;
        }
        long newid = saveVehicle(vh);
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Vehicles SET id=? WHERE id = ?;")) {
            preparedStatement.setLong(1, newid);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean deleteVehicleId(String login, Long id) throws SQLException {
        PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Usersrelationship WHERE login = ? AND object_id = ?;");
        checkStatement.setString(1, login);
        checkStatement.setLong(2, id);
        ResultSet resultSet = checkStatement.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Vehicles USING UsersRelationship WHERE Vehicles.id = UsersRelationship.object_id AND Vehicles.id = ? AND UsersRelationship.login = ?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteVehicle(String login, String queryPart) throws SQLException {
        PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Usersrelationship WHERE login = ?;");
        checkStatement.setString(1, login);
        ResultSet resultSet = checkStatement.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Vehicles USING UsersRelationship WHERE Vehicle.id = UsersRelationship.object_id AND Vehicle."+queryPart+" AND UsersRelationship.login = ?;")) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public Set<Vehicle> getAllVehicles() {
        Set<Vehicle> vehicles = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Vehicles");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(resultSet.getLong("id"));
                vehicle.setName(resultSet.getString("name"));
                Coordinates coordinates = getCoordinates(resultSet.getInt("coord_id"));
                vehicle.setCoordinates(coordinates);
                vehicle.setCreationDate(new java.util.Date(resultSet.getDate("creation_date").getTime()));
                vehicle.setPower(resultSet.getLong("engine_power"));
                vehicle.setType(VehicleType.valueOf(resultSet.getString("vehicle_type")));
                vehicle.setFuel(FuelType.valueOf(resultSet.getString("fuel_type")));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    private Coordinates getCoordinates(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Coordinates WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Coordinates coordinates = new Coordinates();
            coordinates.setX(resultSet.getFloat("x"));
            coordinates.setY(resultSet.getInt("y"));
            return coordinates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

