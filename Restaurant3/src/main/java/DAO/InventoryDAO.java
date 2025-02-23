/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Coupon;
import Model.Inventory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL-Laptop
 */
public class InventoryDAO extends DB.DBContext {

    public List<Inventory> getAllItem() {
        String sql = "SELECT * FROM Inventory";
        List<Inventory> inventoryItemList = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Inventory inventoryItem = new Inventory(
                            rs.getInt("ItemId"),
                            rs.getString("ItemName"),
                            rs.getString("ItemType"),
                            rs.getString("ItemPrice"),
                            rs.getString("ItemQuantity"),
                            rs.getString("ItemUnit"),
                            rs.getString("ItemDescription")
                    );
                    //    System.out.println("--- Inventory Item " + rowCount + " ---");
                    System.out.println("ItemId: " + inventoryItem.getItemId());
                    System.out.println("ItemName: " + inventoryItem.getItemName());
                    System.out.println("ItemType: " + inventoryItem.getItemType());
                    System.out.println("ItemPrice: " + inventoryItem.getItemPrice());
                    System.out.println("ItemQuantity: " + inventoryItem.getItemQuantity());
                    System.out.println("ItemUnit: " + inventoryItem.getItemUnit());
                    System.out.println("ItemDescription: " + inventoryItem.getItemDescription());
                    System.out.println("-----------------------");
                    inventoryItemList.add(inventoryItem);
                }
                return inventoryItemList;
            }

        } catch (Exception e) {
            System.out.println("Error when querying by ID: " + e.getMessage());
        }
        return null;
    }
    
 
}

