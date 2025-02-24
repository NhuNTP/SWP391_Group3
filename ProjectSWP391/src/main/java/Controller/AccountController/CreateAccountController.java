/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.AccountController;

import DAO.AccountDAO;
import Model.Account;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author ADMIN
 */
@WebServlet("/CreateAccount")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 50, // 50MB (tăng từ 10MB)
        maxRequestSize = 1024 * 1024 * 100 // 100MB (tăng từ 50MB)
)

public class CreateAccountController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateAccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateAccountController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("ManageAccount/CreateAccount.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Step 1: Get all form parameters
        String UserEmail = request.getParameter("UserEmail");
        String UserPassword = request.getParameter("UserPassword");
        String UserName = request.getParameter("UserName");
        String UserRole = request.getParameter("UserRole");
        String IdentityCard = request.getParameter("IdentityCard");
        String UserAddress = request.getParameter("UserAddress");

        // Xử lý file ảnh
        Part part = request.getPart("UserImage");
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

        String UserImage = ""; // Khai báo UserImage, mặc định là rỗng

        if (!fileName.isEmpty()) { // Kiểm tra nếu có file được upload
            UserImage = "/img/" + fileName; // Nếu có file, tạo đường dẫn với "/img/"
        } else {
            UserImage = "/img/default-avatar.jpg"; // Hoặc một ảnh mặc định khác nếu muốn, đảm bảo file này có trong webapp/img
        }

        // The relative path to be stored in the database (relative to the app root)
        Account account = new Account(UserEmail, UserPassword, UserName, UserRole, IdentityCard, UserAddress, UserImage);
        AccountDAO dao = new AccountDAO();
        int count = dao.Create(account);

        if (count > 0) {
            response.sendRedirect("ViewAccountList");
        } else {
            response.sendRedirect("CreateAccount");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
