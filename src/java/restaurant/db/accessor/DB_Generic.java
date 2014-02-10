/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.db.accessor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    
    public static void main(String[] args) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        DB_Generic db = new DB_Generic();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Restaurant", "root", null);
        List<String> columns = new ArrayList<>();
        columns.add("item_id");
        columns.add("item_name");
        columns.add("item_price");
        List<Map> rawData = db.findRecords("Menu", columns, "item_name", Boolean.TRUE);
        List<Map> records = new ArrayList<>();
        for(int i=0; i < rawData.size();i++){
            
            
            
        }
    }
}
