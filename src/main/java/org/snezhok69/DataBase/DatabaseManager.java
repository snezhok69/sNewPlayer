package org.snezhok69.DataBase;

import org.snezhok69.DifferentMethods.Variables;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.snezhok69.DifferentMethods.Variables.connection;

public class DatabaseManager {

    public DatabaseManager(Connection connection) {
        Variables.connection = connection;
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS player_protection (" +
                "uuid VARCHAR(36) NOT NULL," +
                "protection_end BIGINT NOT NULL," +
                "PRIMARY KEY (uuid)" +
                ");";

        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
