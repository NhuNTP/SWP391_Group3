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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author ADMIN
 */
@WebServlet("/UpdateAccount")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 50, // 50MB (tăng từ 10MB)
        maxRequestSize = 1024 * 1024 * 100 // 100MB (tăng từ 50MB)
)

public class UpdateAccountController extends HttpServlet {

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
            out.println("<title>Servlet UpdateAccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateAccountController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("ManageAccount/UpdateAccount.jsp").forward(request, response);
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
        int UserId = Integer.parseInt(request.getParameter("UserIdHidden"));
        String UserEmail = request.getParameter("UserEmail");
        String UserPassword = request.getParameter("UserPassword");
        String UserName = request.getParameter("UserName");
        String UserRole = request.getParameter("UserRole");
        String IdentityCard = request.getParameter("IdentityCard");
        String UserAddress = request.getParameter("UserAddress");

        // Lấy tài khoản cũ từ database
        AccountDAO dao = new AccountDAO();
        Account oldAccount = dao.getAccountId(UserId);

        // Xử lý file ảnh
        Part part = request.getPart("UserImage");
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

        String UserImage = ""; // Khai báo UserImage ở đây, ban đầu để rỗng

        if (!fileName.isEmpty()) { // Kiểm tra nếu có file mới được upload
            UserImage = "/img/" + fileName; // Nếu có file mới, tạo đường dẫn đúng
        } else {
            UserImage = oldAccount.getUserImage(); // Nếu không có file mới, giữ nguyên ảnh cũ
        }

        Account account = new Account(UserId, UserEmail, UserPassword, UserName, UserRole, IdentityCard, UserAddress, UserImage);
        int count = dao.Update(UserId, account);

        // Redirect based on whether the update was successful
        if (count > 0) {
            response.sendRedirect("ViewAccountList");
        } else {
            response.sendRedirect("UpdateAccount?id=" + UserId);
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
