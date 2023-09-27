package com.example.dbdemo.config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceUtils {

    private static final String SQL_HEALTH_CHECKING = "SELECT 1 FROM DUAL";
    public static boolean isHealthy(DataSource dataSource) {
        return checkHealth(dataSource) == null;
    }

    public static SQLException checkHealth(DataSource dataSource) {
        try (
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_HEALTH_CHECKING);
        ) {
            return null;
        } catch (SQLException ex) {
            return ex;
        }
    }
}
