package com.lam.locationmapwebservice.db;

import com.lam.locationmapwebservice.db.data.IDBCalls;

final public class DummyDatabase {
    private static final String DB_URL = "jdbc:mysql://192.168.0.15:3306/";
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_SCHEMA = "location_map_service";
    private static final String DB_TIME_ZONE = "UTC";
    private static final String DB_USERNAME = "lms";
    private static final String DB_PASSWORD = "..Sommer18";
    
	private static IDBCalls database = null;
    
    public static IDBCalls getInstance() {
    	if (database == null) {
    		database = new DBComm(new MySQLFactory(DummyDatabase.DB_URL, DummyDatabase.DB_SCHEMA, DummyDatabase.DB_TIME_ZONE, DummyDatabase.DB_USERNAME, DummyDatabase.DB_PASSWORD));
    	}
    	return database;
    }
}
