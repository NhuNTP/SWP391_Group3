<%--
    Document   : DeleteAccount
    Created on : Feb 21, 2025, 8:28:47 PM
    Author     : ADMIN
--%>

<%@page import="Model.Account"%>
<%@page import="DAO.AccountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Delete Employee Account</title> <!-- Updated title -->
        <!-- Bootstrap CSS CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery CDN -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f7f9;
                color: #34495e;
            }

            .navbar {
                background-color: #2c3e50; /* Professional navbar color */
                padding: 15px 20px;
            }

            .navbar-brand {
                color: white;
                font-size: 1.8rem;
                font-weight: bold;
            }

            .container {
                margin-top: 30px;
            }

            h2 {
                font-size: 2.4rem;
                color: #2c3e50;
                margin-bottom: 25px;
                border-bottom: 2px solid #2c3e50;
                padding-bottom: 10px;
            }

            .form-container {
                background-color: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                margin-top: 20px;
            }

            .form-label {
                font-weight: bold;
                color: #34495e;
                margin-bottom: 0.5rem;
                display: block;
                text-align: left;
            }

            .form-control {
                margin-bottom: 1.5rem;
                border-radius: 5px;
                border: 1px solid #ced4da;
                padding: 0.75rem;
                width: 100%;
                box-sizing: border-box;
            }

            .btn-danger {
                background-color: #e74c3c; /* Red delete button */
                color: white;
                border: none;
                padding: 12px 25px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                margin-right: 10px;
            }

            .btn-danger:hover {
                background-color: #c0392b;
            }

            .btn-secondary {
                background-color: #7f8c8d; /* Gray back button */
                color: white;
                border: none;
                padding: 12px 25px;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                transition: background-color 0.3s ease;
            }

            .btn-secondary:hover {
                background-color: #6d7c7c;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .image-preview-container {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                overflow: hidden;
                margin-bottom: 1.5rem;
                box-shadow: 0 0 8px rgba(0,0,0,0.2);
            }

            .image-preview {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .form-row {
                display: flex;
                align-items: flex-start; /* Align items to the start, especially for image and details */
            }

            .form-column-left {
                width: 180px; /* Adjust width as needed for image and spacing */
                padding-right: 20px;
                text-align: center; /* Center image and label if needed */
            }

            .form-column-right {
                flex-grow: 1; /* Take remaining space for form fields */
            }
            .confirmation-message {
                color: #e74c3c; /* Red color for confirmation message */
                font-weight: bold;
                margin-bottom: 20px;
                font-size: 1.2rem;
            }
        </style>
    </head>
    <body>
        <%
            if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
                int id;
                Account obj = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                    AccountDAO dao = new AccountDAO();
                    obj = dao.getAccountId(id);
                } catch (Exception e) {
                    response.sendRedirect("ViewAccountList.jsp");
                    return;
                }
                if (obj == null) {
                    response.sendRedirect("ViewAccountList.jsp");
                    return;
                } else {
        %>
        <!-- Navbar -->
        <nav class="navbar">
            <a class="navbar-brand" href="ViewAccountList">Employee Accounts Management</a>
        </nav>

        <!-- Title and form content -->
        <div class="container">
            <h2>Delete Employee Account</h2>
            <div class="form-container">
                <form method="post" action="DeleteAccount">
                    <input type="hidden" name="UserIdHidden" value="<%= obj.getUserId()%>"/>

                    <div class="form-row">
                        <div class="form-column-left">
                            <div class="image-preview-container">
                                <img src="<%= request.getContextPath() + obj.getUserImage()%>" alt="User Image" class="image-preview"/>
                            </div>
                        </div>
                        <div class="form-column-right">
                            <p class="confirmation-message">Are you sure you want to delete the account for <strong><%= obj.getUserName()%></strong>?</p>
                            <div class="form-group">
                                <label for="UserId" class="form-label">User ID</label>
                                <input type="text" class="form-control" id="UserId" name="UserId" value="<%= obj.getUserId()%>" readonly/>
                            </div>
                            <div class="form-group">
                                <label for="UserEmail" class="form-label">Email Address</label>
                                <input type="email" class="form-control" id="UserEmail" name="UserEmail" value="<%= obj.getUserEmail()%>" readonly/>
                            </div>
                            <div class="form-group">
                                <label for="UserName" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="UserName" name="UserName" value="<%= obj.getUserName()%>" readonly/>
                            </div>
                            <div class="form-group">
                                <label for="UserRole" class="form-label">Role</label>
                                <input type="text" class="form-control" id="UserRole" name="UserRole" value="<%= obj.getUserRole()%>" readonly/>
                            </div>
                            <div class="form-group">
                                <label for="IdentityCard" class="form-label">Identity Card</label>
                                <input type="text" class="form-control" id="IdentityCard" name="IdentityCard" value="<%= obj.getIdentityCard()%>" readonly/>
                            </div>
                            <div class="form-group">
                                <label for="UserAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" id="UserAddress" name="UserAddress" value="<%= obj.getUserAddress()%>" readonly/>
                            </div>
                        </div>
                    </div>

                    <!-- Delete and Back to List Buttons -->
                    <div class="form-group">
                        <input type="submit" class="btn btn-danger" name="btnSubmit" value="Delete Account"/>
                        <a href="ViewAccountList" class="btn btn-secondary">Back to List</a>
                    </div>
                </form>
            </div>
        </div>
        <%
                }
            } else {
                response.sendRedirect("ViewAccountList.jsp");
            }
        %>

        <!-- Bootstrap JS CDN -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>