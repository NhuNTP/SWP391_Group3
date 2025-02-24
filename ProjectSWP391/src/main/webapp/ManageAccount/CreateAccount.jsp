<%--
    Document   : CreateAccount
    Created on : Feb 21, 2025, 8:28:24 PM
    Author     : ADMIN
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="DAO.AccountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create Employee Account</title> <!-- Updated title -->
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
                display: block; /* Ensure label is block for full width */
                text-align: left; /* Align labels to the left */
            }

            .form-control {
                margin-bottom: 1.5rem;
                border-radius: 5px;
                border: 1px solid #ced4da;
                padding: 0.75rem;
                width: 100%; /* Ensure form controls take full width of their container */
                box-sizing: border-box; /* Include padding and border in element's total width and height */
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
                background-color: #27ae60; /* Green submit button */
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
                display: inline-block; /* To allow margin-right to work if needed */
                transition: background-color 0.3s ease;
            }

            .btn-secondary:hover {
                background-color: #6d7c7c;
            }

            .form-group {
                margin-bottom: 20px;
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
        <nav class="navbar">
            <a class="navbar-brand" href="ViewAccountList">Employee Accounts Management</a>
        </nav>

        <div class="container">
            <h2>Create New Employee Account</h2>
            <div class="form-container">
                <form method="post" action="CreateAccount" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <div class="form-group">
                        <label for="UserEmail" class="form-label">Email Address</label>
                        <input type="email" class="form-control" id="UserEmail" name="UserEmail" placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label for="UserPassword" class="form-label">Password</label>
                        <input type="password" class="form-control" id="UserPassword" name="UserPassword" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label for="UserName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="UserName" name="UserName" placeholder="Enter full name">
                    </div>
                    <div class="form-group">
                        <label for="UserRole" class="form-label">Role</label>
                        <select class="form-select" id="UserRole" name="UserRole">
                            <option value="Manager">Manager</option>
                            <option value="Cashier">Cashier</option>
                            <option value="Waiter">Waiter</option>
                            <option value="Kitchen staff">Kitchen staff</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="IdentityCard" class="form-label">Identity Card (12 digits)</label>
                        <input type="text" class="form-control" id="IdentityCard" name="IdentityCard" placeholder="Enter 12-digit ID Card number">
                    </div>
                    <div class="form-group">
                        <label for="UserAddress" class="form-label">Address</label>
                        <input type="text" class="form-control" id="UserAddress" name="UserAddress" placeholder="Enter address">
                    </div>
                    <div class="form-group">
                        <label for="UserImage" class="form-label">Profile Image</label>
                        <input type="file" class="form-control" id="UserImage" name="UserImage">
                    </div>
                    <div class="form-group">
                        <input type="submit" name="btnSubmit" value="Create Account" class="btn btn-primary"/>
                        <a href="ViewAccountList" class="btn btn-secondary">Back to List</a>
                    </div>
                </form>
            </div>
        </div>
        <!-- Bootstrap JS CDN -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>