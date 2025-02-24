<%--
    Document   : UpdateAccount
    Created on : Feb 21, 2025, 8:28:39 PM
    Author     : ADMIN
--%>

<%@page import="DAO.AccountDAO"%>
<%@page import="Model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Employee Account</title> <!-- Updated title -->
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

            .form-select {
                margin-bottom: 1.5rem;
                border-radius: 5px;
                border: 1px solid #ced4da;
                padding: 0.75rem;
                width: 100%;
                box-sizing: border-box;
                appearance: none;
                background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 4 5"><path fill="none" stroke="gray" stroke-width="0.75" d="M0 0l2 2 2-2"/></svg>');
                background-repeat: no-repeat;
                background-position: right 0.75rem center;
                background-size: 12px 12px;
            }

            .btn-primary {
                background-color: #27ae60; /* Green save button */
                color: white;
                border: none;
                padding: 12px 25px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                margin-right: 10px;
            }

            .btn-primary:hover {
                background-color: #219853;
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

        </style>

        <script>
            function validateForm() {
                let email = document.getElementById("UserEmail").value.trim();
                let password = document.getElementById("UserPassword").value.trim();
                let name = document.getElementById("UserName").value.trim();
                let role = document.getElementById("UserRole").value;
                let idCard = document.getElementById("IdentityCard").value.trim();
                let address = document.getElementById("UserAddress").value.trim();

                if (!email || !password || !name || !role || !idCard || !address) {
                    alert("All fields are required.");
                    return false;
                }

                if (!email.endsWith("@gmail.com")) {
                    alert("Email must end with '@gmail.com'.");
                    return false;
                }

                if (!/^\d{12}$/.test(idCard)) {
                    alert("Identity Card must be exactly 12 digits.");
                    return false;
                }

                return true;
            }
        </script>
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
                    response.sendRedirect("ViewAccountList");
                    return;
                }
                if (obj == null) {
                    response.sendRedirect("ViewAccountList");
                    return;
                } else { //co id thi hien thong tin form
        %>


        <!-- Navbar -->
        <nav class="navbar">
            <a class="navbar-brand" href="ViewAccountList">Employee Accounts Management</a>
        </nav>

        <!-- Title and form content -->
        <div class="container">
            <h2>Edit Employee Account</h2>
            <div class="form-container">
                <form method="post" action="UpdateAccount" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <input type="hidden" id="UserIdHidden" name="UserIdHidden" value="<%= obj.getUserId()%>"/>

                    <div class="form-row">
                        <div class="form-column-left">
                            <div class="image-preview-container">
                                <img src="<%= request.getContextPath() + obj.getUserImage()%>" alt="Current Image" class="image-preview"/>
                            </div>
                            <label for="UserImage" class="form-label">Update Profile Image</label>
                            <input type="file" class="form-control" id="UserImage" name="UserImage"/>
                        </div>
                        <div class="form-column-right">
                            <div class="form-group">
                                <label for="UserId" class="form-label">User ID</label>
                                <input type="text" class="form-control" id="UserId" name="UserId" value="<%= obj.getUserId()%>" readonly/>
                            </div>
                            <div class="form-group">
                                <label for="UserEmail" class="form-label">Email Address</label>
                                <input type="email" class="form-control" id="UserEmail" name="UserEmail" value="<%= obj.getUserEmail()%>"/>
                            </div>
                            <div class="form-group">
                                <label for="UserPassword" class="form-label">Password</label>
                                <input type="password" class="form-control" id="UserPassword" name="UserPassword" value="<%= obj.getUserPassword()%>"/>
                            </div>
                            <div class="form-group">
                                <label for="UserName" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="UserName" name="UserName" value="<%= obj.getUserName()%>"/>
                            </div>
                            <div class="form-group">
                                <label for="UserRole" class="form-label">Role</label>
                                <select class="form-select" id="UserRole" name="UserRole">
                                    <option value="Manager" <%= "Manager".equals(obj.getUserRole()) ? "selected" : "" %>>Manager</option>
                                    <option value="Cashier" <%= "Cashier".equals(obj.getUserRole()) ? "selected" : "" %>>Cashier</option>
                                    <option value="Waiter" <%= "Waiter".equals(obj.getUserRole()) ? "selected" : "" %>>Waiter</option>
                                    <option value="Kitchen staff" <%= "Kitchen staff".equals(obj.getUserRole()) ? "selected" : "" %>>Kitchen staff</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="IdentityCard" class="form-label">Identity Card (12 digits)</label>
                                <input type="text" class="form-control" id="IdentityCard" name="IdentityCard" value="<%= obj.getIdentityCard()%>"/>
                            </div>
                            <div class="form-group">
                                <label for="UserAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" id="UserAddress" name="UserAddress" value="<%= obj.getUserAddress()%>"/>
                            </div>
                        </div>
                    </div>


                    <!-- Save and Back to List Buttons -->
                    <div class="form-group">
                        <input type="submit" class="btn btn-primary" name="btnSubmit" value="Save Changes"/>
                        <a href="ViewAccountList" class="btn btn-secondary">Back to List</a>
                    </div>
                </form>
            </div>
        </div>
        <% }
            } else {
                response.sendRedirect("ViewAccountList.jsp");
            }%>

        <!-- Bootstrap JS CDN -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>