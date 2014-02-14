/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package restaurant.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import restaurant.DataAccessException;
import restaurant.db.accessor.DB_Generic;

/**
 *
 * @author schereja
 */
public class MenuItemsDAO implements IMenuItemDAO{

    private DB_Generic db;
    
    public MenuItemsDAO(){ 
    }
    
    public MenuItemsDAO(DB_Generic db){
        setDb(db);
    }

    public DB_Generic getDb() {
        return db;
    }

    public void setDb(DB_Generic db) {
        this.db = db;
    }
    /**
     * Opens basic connection to the local database
     * 
     * @throws DataAccessException Throws a data access exception when illegal
     * class not found and sql exceptions are done
     */
    public void openLocalDbConnection() throws DataAccessException{
        try{
            db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/Restaurant", "root", null);
        } catch (IllegalArgumentException | ClassNotFoundException | SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        
        
    }
    //Add items to the menu
    
    //Gets full list of menu items
    @Override
    public List<MenuItem> getAllMenuItems() throws DataAccessException{
         this.openLocalDbConnection();
        List<String> colNames = new ArrayList<>();
        colNames.add("item_id");
        colNames.add("item_name");
        colNames.add("item_price");
        List<Map> rawData = new ArrayList<>();
        List<MenuItem> records = new ArrayList<>();
        try {
            rawData = db.findRecords("Menu", colNames, "item_id", Boolean.TRUE);
        } catch (SQLException e2) {
            throw new DataAccessException(e2.getMessage(), e2);
        }

        MenuItem menuItem = null;

        // Translate List<Map> into List<Employee>
        for (Map m : rawData) {
            menuItem = new MenuItem();

            String id = m.get("item_id").toString();
            menuItem.setItemId(new Integer(id));
            String itemName = m.get("item_name").toString();
            menuItem.setItemName(itemName);
            String itemPrice = m.get("item_price").toString();
            menuItem.setItemPrice(new Double(itemPrice));
    
            records.add(menuItem);
        }

        return records;
    }
    public void addItemsToMenu(String itemName, double itemPrice) throws DataAccessException{
        
    }
    public static void main(String[] args) throws DataAccessException {
        MenuItemsDAO midao = new MenuItemsDAO(new DB_Generic());
        midao.openLocalDbConnection();
        List<MenuItem> records = midao.getAllMenuItems();

        System.out.println("Found Menu Items...\n");
        for (MenuItem items : records) {
            System.out.println(items.getItemName() + " For $"+ items.getItemPrice());
        }
    }
}
