/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.ManageMenu;

import DAO.MenuDAO;
import Model.Dish;
import Model.Inventory;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LxP
 */
@WebServlet(name = "addnewdish", urlPatterns = {"/addnewdish"})
public class AddnewdishController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MenuDAO menuDAO;

    @Override
    public void init() {
        menuDAO = new MenuDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Inventory> inventoryList = menuDAO.getAllInventory();
            request.setAttribute("inventoryList", inventoryList);
            request.getRequestDispatcher("addnewdish.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải danh sách nguyên liệu: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String dishName = request.getParameter("DishName");
            String dishType = request.getParameter("DishType");
            double dishPrice = Double.parseDouble(request.getParameter("DishPrice").trim());
            String dishDescription = request.getParameter("DishDescription").trim();
            String dishImage = request.getParameter("DishImage").trim();

            Dish newDish = new Dish();
            newDish.setDishName(dishName.trim());
            newDish.setDishType(dishType.trim());
            newDish.setDishPrice(dishPrice);
            newDish.setDishDescription(dishDescription);
            newDish.setDishImage(dishImage);

            int dishId = menuDAO.addDish(newDish);
            if (dishId > 0) {
                String[] ingredientIds = request.getParameterValues("ItemId");
                if (ingredientIds != null) {
                    for (String itemIdStr : ingredientIds) {
                        int itemId = Integer.parseInt(itemIdStr);
                        String quantityStr = request.getParameter("QuantityUsed_" + itemId);
                        if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                            int quantityUsed = Integer.parseInt(quantityStr.trim());
                            menuDAO.addDishIngredient(dishId, itemId, quantityUsed);
                        }
                    }
                }
                response.sendRedirect("menu.jsp?success=1");
            } else {
                response.sendRedirect("addnewdish.jsp?error=insert_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("addnewdish.jsp?error=invalid_number");
        } catch (Exception e) {
            e.printStackTrace(); // In ra log lỗi
            response.sendRedirect("addnewdish.jsp?error=server_error");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding new dishes";
    }
}