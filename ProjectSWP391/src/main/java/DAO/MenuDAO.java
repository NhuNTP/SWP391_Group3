/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Dish;
import Model.Inventory;
import Model.DishInventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.DBContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuDAO {
    
    public int addDish(Dish dish) throws SQLException ,ClassNotFoundException {
        String sql = "INSERT INTO Dish (DishName, DishType, DishPrice, DishDescription, DishImage) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dish.getDishName());
            stmt.setString(2, dish.getDishType());
            stmt.setDouble(3, dish.getDishPrice());
            stmt.setString(4, dish.getDishDescription());
            stmt.setString(5, dish.getDishImage());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Trả về DishId mới
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra log lỗi
        }
        return -1; // Lỗi
    }

    public boolean addDishIngredient(int dishId, int itemId, int quantityUsed) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Dish_Inventory (DishId, ItemId, QuantityUsed) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dishId);
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantityUsed);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // In ra log lỗi
        }
        return false;
    }

    public List<Inventory> getAllInventory() throws SQLException, ClassNotFoundException {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ItemId"));
                item.setItemName(rs.getString("ItemName"));
                item.setItemType(rs.getString("ItemType"));
                item.setItemPrice(rs.getDouble("ItemPrice"));
                item.setItemQuantity(rs.getInt("ItemQuantity"));
                item.setItemUnit(rs.getString("ItemUnit"));
                item.setItemDescription(rs.getString("ItemDescription"));
                inventoryList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra log lỗi
        }
        
        return inventoryList;
    }
}