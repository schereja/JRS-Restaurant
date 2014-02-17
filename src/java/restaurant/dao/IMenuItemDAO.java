/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package restaurant.dao;

import java.sql.SQLException;
import java.util.List;
import restaurant.DataAccessException;

/**
 *
 * @author schereja
 */
public interface IMenuItemDAO {
    public abstract List<MenuItem> getAllMenuItems() throws DataAccessException;
    public abstract void addItemToMenu(MenuItem mi) throws DataAccessException, SQLException;
    public abstract void deleteItemFromMenuById(int id) throws DataAccessException, SQLException;
            }
