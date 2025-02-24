/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.CustomerController;

import DAO.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author HuynhPhuBinh
 */
@WebServlet("/DeleteCustomer")
public class DeleteCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        System.out.println("Deleting customer with ID: " + customerId);

        boolean success = customerDAO.deleteCustomer(customerId);
        
        if (success) {
            request.getSession().setAttribute("message", "Xóa khách hàng thành công!");
        } else {
            request.getSession().setAttribute("error", "Không thể xóa khách hàng!");
        }
        
        response.sendRedirect("ViewCustomerList");
    }
}