/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.db.accessor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import restaurant.DataAccessException;

/**
 *
 * @author schereja
 */
public class DB_Generic {

    private Connection conn;

    public void openConnection(String driverClassName, String url, String username, String password)
            throws IllegalArgumentException, ClassNotFoundException, SQLException {
        String msg = "Error: url is null or zero length!";
        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
        username = (username == null) ? "" : username;
        password = (password == null) ? "" : password;
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, password);
    }

 
    public List<Map> findRecords(String tableName, List<String> columnNames, String sortCol, Boolean sortAsc) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        final List list = new ArrayList();
        Map record = null;

        //Create String for Sql Query
        String sql = "SELECT ";
        for (String col : columnNames) {
            sql += col + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " FROM " + tableName + " ORDER BY " + sortCol;

        //Get results
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();
            while (rs.next()) {
                record = new HashMap();
                for (int i = 1; i <= fields; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));

                }
                list.add(record);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                stmt.close();
                if (true) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }

        return list;
    }

  

    public int deleteRecord(String tableName, String whereField, Object whereValue, Boolean closeConnection) throws SQLException {
        PreparedStatement prepState = null;
        int recordsDeleted = 0;
        
        try {
            prepState = buildRemovalStatement(conn, tableName, whereField);
            
            if (whereField != null) {
                if (whereValue instanceof String) {
                   prepState.setString(1, ((String) whereValue).toString());
                } else if (whereValue instanceof Integer) {
                    prepState.setInt(1, ((Integer) whereValue).intValue());
                } else if (whereValue instanceof Long) {
                    prepState.setLong(1, ((Long) whereValue).longValue());
                } else if (whereValue instanceof Double) {
                    prepState.setDouble(1, ((Double) whereValue).doubleValue());
                } else if (whereValue instanceof java.sql.Date) {
                    prepState.setDate(1, (java.sql.Date) whereValue);
                } else if (whereValue instanceof Boolean) {
                    prepState.setBoolean(1, ((Boolean) whereValue).booleanValue());
                } else if (whereValue != null) {
                        prepState.setObject(1, whereValue);
                    }
                
            }
            recordsDeleted= prepState.executeUpdate();

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            try {
                prepState.close();
                if(closeConnection)
                {conn.close();}
            } catch (SQLException sqlExcept) {
                throw sqlExcept;
            }
        }

        return recordsDeleted;
    }

    private PreparedStatement buildRemovalStatement(Connection conn_loc, String tableName, String whereField)
            throws SQLException {
        final StringBuffer sb = new StringBuffer("DELETE FROM ");
        
        if (tableName != null && whereField != null) {
            sb.append(tableName);
            sb.append(" WHERE ");
            (sb.append(whereField)).append(" = ?");
        }
        final String finalSqlStatement = sb.toString();
        
        return conn_loc.prepareStatement(finalSqlStatement);

    }

    public static void main(String[] args) throws IllegalArgumentException, SQLException, ClassNotFoundException, DataAccessException, Exception {
        DB_Generic db = new DB_Generic();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Restaurant", "root", null);
        List<String> columns = new ArrayList<>();

        columns.add("item_name");
        columns.add("item_price");
       // List<Map> rawData = db.findRecords("Menu", columns, "item_name", Boolean.TRUE);
        //List<Map> records = new ArrayList<>();
        // List newAddition = new ArrayList();
        // newAddition.add("Milk");
        // newAddition.add(3.99);
        // System.out.println(newAddition);
        // db.insertRecord("menu", columns, newAddition, true);
        //System.out.println(db.findRecords("Menu", columns, "item_name", Boolean.FALSE));
        //db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Restaurant", "root", null);
        System.out.println(db.deleteRecord("Menu", "item_name", "Fries",Boolean.TRUE));
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Restaurant", "root", null);
        System.out.println(db.findRecords("Menu", columns, "item_name", Boolean.TRUE));
    }
}
