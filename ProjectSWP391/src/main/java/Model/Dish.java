/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LxP
 */
import java.util.List;

public class Dish {

    private int dishId;
    private String dishName;
    private String dishType;
    private double dishPrice;
    private String dishDescription;
    private String dishImage;
    private List<DishInventory> ingredients; // Danh sách nguyên liệu

    public Dish() {
    }

    public Dish(int dishId, String dishName, String dishType, double dishPrice, String dishDescription, String dishImage, List<DishInventory> ingredients) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishType = dishType;
        this.dishPrice = dishPrice;
        this.dishDescription = dishDescription;
        this.dishImage = dishImage;
        this.ingredients = ingredients;
    }

    // Getter và Setter
    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public List<DishInventory> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<DishInventory> ingredients) {
        this.ingredients = ingredients;
    }
}
