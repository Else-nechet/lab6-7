package com.nechet.server.databaseLogic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RelationshipsDAO extends DatabaseConnection{
    public RelationshipsDAO(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }
    public void addRelationship(long id, String ownerLogin) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO UsersRelationship (" +
                "login,object_id) VALUES (?,?) ");

        ps.setString(1,ownerLogin);
        ps.setLong(2,id);

        ps.executeUpdate();
    }
    public boolean isOwner(long id, String ownerLogin) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("SELECT login FROM UsersRelatioships WHERE object_id = ?");
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            String realOwner = resultSet.getString("user_login");
            return realOwner.equals(ownerLogin);
        }
        return false;
    }

    public ArrayList<Long> getUserSpaceMarine(String ownerLogin) throws SQLException {
        ArrayList<Long> ids = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement("SELECT object_id FROM UsersRelationships WHERE login = ?");
        ps.setString(1,ownerLogin);
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            ids.add(resultSet.getLong("product_id"));
        }
        return ids;
    }
}