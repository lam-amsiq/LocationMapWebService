package com.lam.locationmapwebservice.db;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLFactory {
    private final String url, schema, timeZone, userName, password;
    
    public MySQLFactory(String url, String schema, String timeZone, String userName, String password) {
        this.url = url;
        this.schema = schema;
        this.timeZone = timeZone;
        this.userName = userName;
        this.password = password;
    }
    
    protected Connection createConnection() throws SQLException, ConnectException {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url+schema+"?serverTimezone="+timeZone, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ConnectException("Missing MySQL JDBC driver");
		} catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectException("Could not connect to database");
        } 
    }
}
