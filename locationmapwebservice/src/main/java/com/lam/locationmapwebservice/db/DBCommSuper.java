package com.lam.locationmapwebservice.db;

import java.net.ConnectException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

import com.lam.locationmapwebservice.db.data.IDBCalls;

public abstract class DBCommSuper {
    private static final int INTEGER_NULL = -1;
    private static final int INTEGER_NEG_1_OVERRIDE = Integer.MIN_VALUE;
	
    protected final MySQLFactory db;
    protected final ResourceBundle calls;
    protected Connection con = null;
    protected PreparedStatement ps;
    
    protected DBCommSuper(MySQLFactory dbComm) {
        this.db = dbComm;
        this.calls = ResourceBundle.getBundle("com/lam/locationmapwebservice/db/data/DBCalls");
    }
    
    protected ResultSet sendCall(String call, Object... args) throws ConnectException, SQLException {
        return createPreparedStatement(call, args).executeQuery();
    }

    protected int sendUpdate(String call, Object... args) throws ConnectException, SQLException {
        return createPreparedStatement(call, args).executeUpdate();
    }
    
    protected boolean sendQuery(String query, Object... args) throws ConnectException, SQLException {
        return createPreparedStatement(query, args).execute();
    }
    
    protected void sendVoidCall(String call, Object... args) throws ConnectException {
        ResultSet rs = null;
        
        try {
            rs = sendCall(calls.getString(call), args);
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
            throw new ConnectException(e.getMessage());
        } finally {
            closeConnection(rs);
        }
    }
    
    protected void closeConnection(ResultSet rs) {
        if(rs != null) { // Close result set
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if(ps != null) { // Close prepared statement
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if(con != null) { // Close connection
            try {
                con.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    protected String composeHeaderQuery(String header, String... args) {
        for (String arg : args) 
            header = header.replaceFirst("\\?", arg);
        
        return header;
    }
    
    protected String composeMultiRowsQuery(String header, int columns, int rows) {
        StringBuilder sb = new StringBuilder(header);
        
        for (int row = 0; row < rows; row++) {
            sb.append('(');
            
            for (int col = 0; col < columns; col++)
                sb.append("?,");
            
            sb.setLength(sb.length() - 1);
            
            sb.append("),");
        }
        sb.setLength(sb.length() - 1);
        
        return sb.toString();
    }
    
    private PreparedStatement createPreparedStatement(String request, Object... args) throws SQLException, ConnectException {
        con = db.createConnection();

        ps = con.prepareStatement(request);
        insertArguments(ps, args);
        
        System.out.println("PS=" + ps);
        return ps;
    }
    
    private void insertArguments(PreparedStatement ps, Object... args) throws SQLException {
        if(ps == null || args == null) return;
        
        Object arg;
        for(int i=1; i<args.length+1; i++) {
            arg = args[i-1];
            
            if(arg instanceof Boolean) {
            	ps.setBoolean(i, (boolean) arg);
            } else if (arg instanceof Integer) {
                switch((int) arg) {
                    case INTEGER_NULL:
                        ps.setNull(i, Types.INTEGER); break;
                    case INTEGER_NEG_1_OVERRIDE:
                        ps.setInt(i, -1); break;
                    default:
                        ps.setInt(i, (int) arg); break;
                }
            } else if(arg instanceof Long) {
            	ps.setLong(i, (long) arg);
            } else if(arg instanceof String) {
                ps.setString(i, (String) arg);
            } else if(arg instanceof Float) {
                float f = (float) arg;
                if(f == -1)
                    ps.setNull(i, Types.FLOAT);
                else
                    ps.setFloat(i, (float) arg);
            } else if(arg instanceof Double) {
                double d = (double) arg;
                if(d == -1)
                    ps.setNull(i, Types.DOUBLE);
                else
                    ps.setDouble(i, (double) arg);
            } else if(arg instanceof Date) {
                ps.setDate(i, (Date) arg);
            } else if(arg instanceof Array) {
                ps.setArray(i, (Array) arg);
            } else if(arg instanceof Blob) {
                ps.setBlob(i, (Blob) arg);
            } else {
                throw new IllegalArgumentException("Unknown type: " + arg + " (" + arg.getClass() + ')');
            }
        }
    }
}