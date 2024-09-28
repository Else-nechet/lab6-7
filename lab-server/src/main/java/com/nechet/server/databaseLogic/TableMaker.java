package com.nechet.server.databaseLogic;

import java.sql.SQLException;
import java.sql.Statement;

public class TableMaker extends DatabaseConnection{
    public TableMaker(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Chapters (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, parent_legion VARCHAR(255), marine_count INTEGER NOT NULL CHECK (marine_count > 0 AND marine_count <= 1000), world VARCHAR(255) NOT NULL);");
        statement.execute("CREATE TABLE IF NOT EXISTS Coordinates (id SERIAL PRIMARY KEY, x DOUBLE PRECISION NOT NULL, y INTEGER NOT NULL CHECK (y <= 58));");
        statement.execute("CREATE TABLE IF NOT EXISTS SpaceMarines (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, coord_id INTEGER NOT NULL, creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, health DOUBLE PRECISION NOT NULL CHECK (health > 0), loyal BOOLEAN NOT NULL, category VARCHAR(255) NOT NULL CHECK (category IN ('SCOUT','ASSAULT','SUPRESSOR','APOTHECARY')), weapon_type VARCHAR(255) NOT NULL CHECK (weapon_type IN ('BOLTGUN','MELTAGUN','BOLT_PISTOL','BOLT_RIFLE','HEAVY_FLAMER')), chapter_id INTEGER NOT NULL, FOREIGN KEY (chapter_id) REFERENCES Chapters(id) ON DELETE CASCADE, FOREIGN KEY (coord_id) REFERENCES Coordinates(id) ON DELETE CASCADE);");
        statement.execute("CREATE TABLE IF NOT EXISTS Users (login VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL);");
        statement.execute("CREATE TABLE IF NOT EXISTS UsersRelationship (login VARCHAR(255) NOT NULL, object_id INTEGER NOT NULL, PRIMARY KEY (login, object_id), FOREIGN KEY (login) REFERENCES Users(login) ON DELETE CASCADE, FOREIGN KEY (object_id) REFERENCES SpaceMarines(id) ON DELETE CASCADE);");
    }
}
