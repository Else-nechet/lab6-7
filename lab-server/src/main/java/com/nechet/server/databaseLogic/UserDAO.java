package com.nechet.server.databaseLogic;

import com.nechet.server.exception.UserNotFoundException;
import com.nechet.server.exception.WrongPasswordException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DatabaseConnection {
    private final PasswordCoder passwordManager = new PasswordCoder();

    public UserDAO(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }

    public void authenticateUser(String login, String password) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
        ps.setString(1, login);

        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            String expectedPassword = resultSet.getString("password");
            if (expectedPassword.equals(passwordManager.hashPassword(password))) {
            }else{
                throw new WrongPasswordException();
            }
        }else{
            throw new UserNotFoundException();

        }
    }


    public boolean addUser(String login, String password) throws SQLException {
        PreparedStatement checkUserStatement = connection.prepareStatement("SELECT * FROM Users WHERE login = ?");
        checkUserStatement.setString(1, login);
        ResultSet resultSet = checkUserStatement.executeQuery();
        if (resultSet.next()) {
            System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
            return false;
        }
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO Users" +
                "(login, password) VALUES (?,?)");
        ps.setString(1, login);
        ps.setString(2, passwordManager.hashPassword(password));
        ps.executeUpdate();
        return true;
    }
}