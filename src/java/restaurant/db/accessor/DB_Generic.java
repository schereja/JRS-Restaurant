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
    public void addRecords(String tableName, List<String> columnNames, List values) throws DataAccessException, SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        String sql = "INSERT INTO " + tableName + " (";
        for(String col: columnNames){
            sql +=col + ",";
        }
        sql += ") VALUES (" + "'" + values.get(0) + "''" + values.get(1) + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            metaData = rs.getMetaData();
   
            
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
    public boolean insertRecord(String tableName, List colDescriptors, List colValues, boolean closeConnection)
	throws SQLException, Exception
	{
		PreparedStatement pstmt = null;
		int recsUpdated = 0;

		// do this in an excpetion handler so that we can depend on the
		// finally clause to close the connection
		try {
			pstmt = buildInsertStatement(conn,tableName,colDescriptors);

			final Iterator i=colValues.iterator();
			int index = 1;
			while( i.hasNext() ) {
				final Object obj=i.next();
				if(obj instanceof String){
					pstmt.setString( index++,(String)obj );
				} else if(obj instanceof Integer ){
					pstmt.setInt( index++,((Integer)obj).intValue() );
				} else if(obj instanceof Long ){
					pstmt.setLong( index++,((Long)obj).longValue() );
				} else if(obj instanceof Double ){
					pstmt.setDouble( index++,((Double)obj).doubleValue() );
				} else if(obj instanceof java.sql.Date ){
					pstmt.setDate(index++, (java.sql.Date)obj );
				} else if(obj instanceof Boolean ){
					pstmt.setBoolean(index++, ((Boolean)obj).booleanValue() );
				} else {
					if(obj != null) pstmt.setObject(index++, obj);
				}
			}
			recsUpdated = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
				if(closeConnection) conn.close();
			} catch(SQLException e) {
				throw e;
			} // end try
		} // end finally

		if(recsUpdated == 1){
			return true;
		} else {
			return false;
		}
	}
    private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors)
	throws SQLException {
		StringBuffer sql = new StringBuffer("INSERT INTO ");
		(sql.append(tableName)).append(" (");
		final Iterator i=colDescriptors.iterator();
		while( i.hasNext() ) {
			(sql.append( (String)i.next() )).append(", ");
		}
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) + ") VALUES (" );
		for( int j = 0; j < colDescriptors.size(); j++ ) {
			sql.append("?, ");
		}
		final String finalSQL=(sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ")) + ")";
		//System.out.println(finalSQL);
		return conn_loc.prepareStatement(finalSQL);
	}
    
    public static void main(String[] args) throws IllegalArgumentException, SQLException, ClassNotFoundException, DataAccessException, Exception {
        DB_Generic db = new DB_Generic();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Restaurant", "root", null);
        List<String> columns = new ArrayList<>();
        
        columns.add("item_name");
        columns.add("item_price");
       // List<Map> rawData = db.findRecords("Menu", columns, "item_name", Boolean.TRUE);
        //List<Map> records = new ArrayList<>();
        List newAddition = new ArrayList();
        newAddition.add("Milk");
        newAddition.add(14.99);
        System.out.println(newAddition);
        db.insertRecord("menu", columns, newAddition, true);
        
    }
}
