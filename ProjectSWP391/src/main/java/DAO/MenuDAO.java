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

    // Dish operations
    public int addDish(Dish dish) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Dish (DishName, DishType, DishPrice, DishDescription, DishImage) VALUES (?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, dish.getDishName());
            preparedStatement.setString(2, dish.getDishType());
            preparedStatement.setDouble(3, dish.getDishPrice());
            preparedStatement.setString(4, dish.getDishDescription());
            preparedStatement.setString(5, dish.getDishImage());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return -1; // Thêm thất bại
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Trả về ID của món vừa thêm
                }
                else {
                    return -1; // Không lấy được ID
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Thêm thất bại
        }
    }

    // Check if dish name exists
    public boolean dishNameExists(String dishName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Dish WHERE DishName = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, dishName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // True if dish name exists
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Assume not exists in case of error
    }


    // Inventory operations
    public List<Inventory> getAllInventory() throws SQLException, ClassNotFoundException {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT ItemId, ItemName, ItemType, ItemPrice, ItemQuantity, ItemUnit, ItemDescription FROM Inventory";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Inventory inventory = new Inventory();
                inventory.setItemId(resultSet.getInt("ItemId"));
                inventory.setItemName(resultSet.getString("ItemName"));
                inventory.setItemType(resultSet.getString("ItemType"));
                inventory.setItemPrice(resultSet.getDouble("ItemPrice"));
                inventory.setItemQuantity(resultSet.getInt("ItemQuantity"));
                inventory.setItemUnit(resultSet.getString("ItemUnit"));
                inventory.setItemDescription(resultSet.getString("ItemDescription"));
                inventoryList.add(inventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    // DishInventory operations
    public boolean addDishInventory(DishInventory dishInventory) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Dish_Inventory (DishId, ItemId, QuantityUsed) VALUES (?, ?, ?)";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, dishInventory.getDishId());
            preparedStatement.setInt(2, dishInventory.getItemId());
            preparedStatement.setInt(3, dishInventory.getQuantityUsed());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}