/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package restaurant.service;

import java.util.List;
import restaurant.dao.IMenuItemDAO;
import restaurant.dao.MenuItem;
import restaurant.dao.MenuItemsDAO;
import restaurant.db.accessor.DB_Generic;

/**
 *
 * @author schereja
 */
public final class RestaurantService {
    private List<MenuItem> menuList;
    private restaurant.dao.IMenuItemDAO menuItemDAO;
    private DB_Generic db = new DB_Generic();
    public RestaurantService(){
        
    }
    public RestaurantService(String dao) throws Exception{
        initMenu(dao);
    }
    
    public void initMenu(String dao) throws Exception{
        menuItemDAO = new MenuItemsDAO(db);
        menuList = menuItemDAO.getAllMenuItems();
    }
    
   
    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuItem> menuList) {
        this.menuList = menuList;
    }

    public IMenuItemDAO getMenuItemDAO() {
        return menuItemDAO;
    }

    public void setMenuItemDAO(IMenuItemDAO menuItemDAO) {
        this.menuItemDAO = menuItemDAO;
    }
    public static void main(String[] args) throws Exception {
        String dao = "restaurant.dao.IMenuItemDAO";
        RestaurantService rs = new RestaurantService(dao);
        for (MenuItem items : rs.menuList) {
            System.out.println(items.getItemName() + " For $"+ items.getItemPrice());
        }
    }
    
}
