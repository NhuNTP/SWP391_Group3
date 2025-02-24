/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.ManageMenu;

import DAO.MenuDAO;
import Model.Dish;
import Model.DishInventory;
import Model.Inventory;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author LxP
 */
@WebServlet("/addnewdish")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 100)    // 100MB
public class AddnewdishController extends HttpServlet {

    private final MenuDAO menuDAO = new MenuDAO();
    private static final String UPLOAD_DIRECTORY = "images"; // Thư mục lưu trữ ảnh (trong webapp)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Inventory> inventoryList = null;
        try {
            inventoryList = menuDAO.getAllInventory();
        } catch (SQLException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("inventoryList", inventoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ManageMenu/addnewdish.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dishName = request.getParameter("dishName");
        String dishType = request.getParameter("dishType");
        double dishPrice = Double.parseDouble(request.getParameter("dishPrice"));
        String dishDescription = request.getParameter("dishDescription");

        try {
            // Kiểm tra tên món đã tồn tại chưa
            if (menuDAO.dishNameExists(dishName)) {
                request.setAttribute("errorMessage", "Dish name already exists. Please choose a different name.");
                List<Inventory> inventoryList = null;
                try {
                    inventoryList = menuDAO.getAllInventory();
                } catch (SQLException ex) {
                    Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("inventoryList", inventoryList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("ManageMenu/addnewdish.jsp");
                dispatcher.forward(request, response);
                return; // Dừng lại nếu tên món đã tồn tại
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Xử lý upload ảnh
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;  // Đường dẫn tuyệt đối đến thư mục
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String dishImage = null;
        try {
            Part filePart = request.getPart("dishImage"); // Lấy file từ form
            String fileName = filePart.getSubmittedFileName();  // Lấy tên file
            if (fileName != null && !fileName.isEmpty()) {
                dishImage = UPLOAD_DIRECTORY + "/" + fileName;  // Lưu đường dẫn tương đối vào DB
                filePart.write(uploadPath + File.separator + fileName); // Lưu file lên server
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error uploading image: " + e.getMessage());
            doGet(request, response); // Quay lại form với thông báo lỗi
            return;
        }

        Dish dish = new Dish(dishName, dishType, dishPrice, dishDescription, dishImage);
        int newDishId = 0;
        try {
            newDishId = menuDAO.addDish(dish);
        } catch (SQLException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (newDishId > 0) {
            // Lấy danh sách các nguyên liệu đã chọn
            String[] itemIds = request.getParameterValues("itemId");

            if (itemIds != null && itemIds.length > 0) {

                boolean hasError = false;

                // Lọc ra các itemId và quantityUsed hợp lệ
                List<Integer> validItemIds = Arrays.stream(itemIds)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

                 for (Integer itemId : validItemIds) {
                    try {
                        // Tìm quantity tương ứng với itemId
                        String quantityParam = request.getParameter("quantityUsed" + itemId);
                        if (quantityParam == null || quantityParam.isEmpty()) {
                            // Bỏ qua nếu không có quantity, người dùng có thể không nhập
                            continue;
                        }
                        int quantityUsed = Integer.parseInt(quantityParam);

                        DishInventory dishInventory = new DishInventory();
                        dishInventory.setDishId(newDishId);
                        dishInventory.setItemId(itemId);
                        dishInventory.setQuantityUsed(quantityUsed);

                        menuDAO.addDishInventory(dishInventory);

                    } catch (NumberFormatException e) {
                        // Xử lý lỗi nếu dữ liệu không hợp lệ
                        e.printStackTrace();
                        request.setAttribute("errorMessage", "Invalid quantity for an ingredient.");
                        hasError = true;
                        break; // Thoát khỏi vòng lặp nếu có lỗi
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("errorMessage", "Error adding ingredient to dish.");
                        hasError = true;
                        break;  // Thoát nếu lỗi SQL
                    }
                }
                if (!hasError){
                    request.setAttribute("message", "Dish added successfully!");
                }


            }
            else{
                request.setAttribute("message", "Dish added successfully but there are no ingredients added!");
            }

        } else {
            request.setAttribute("errorMessage", "Failed to add dish.");
        }

        List<Inventory> inventoryList = null;
        try {
            inventoryList = menuDAO.getAllInventory();
        } catch (SQLException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddnewdishController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("inventoryList", inventoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ManageMenu/addnewdish.jsp");
        dispatcher.forward(request, response);
    }
}