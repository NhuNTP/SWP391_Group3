/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LxP
 */
public class DishInventory {

    private int DishId;
    private int ItemId;
    private int QuantityUsed;

    public DishInventory() {
    }

    public DishInventory(int DishId, int ItemId, int QuantityUsed) {
        this.DishId = DishId;
        this.ItemId = ItemId;
        this.QuantityUsed = QuantityUsed;
    }

    // Getter và Setter
    public int getDishId() {
        return DishId;
    }

    public void setDishId(int DishId) {
        this.DishId = DishId;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int ItemId) {
        this.ItemId = ItemId;
    }

    public int getQuantityUsed() {
        return QuantityUsed;
    }

    public void setQuantityUsed(int QuantityUsed) {
        this.QuantityUsed = QuantityUsed;
    }
}
