<%--
    Document   : ViewAccountList
    Created on : Feb 21, 2025, 8:28:07 PM
    Author     : ADMIN
--%>

<%@page import="Model.Account"%>
<%@page import="DAO.AccountDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAO.AccountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">        <!-- Bootstrap CSS CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            /* Custom CSS for the navbar */
            .navbar {
                background-color: #0056b3; /* A more professional blue */
                padding: 12px 15px; /* Slightly increased padding */
                display: flex;
                justify-content: flex-start;
                align-items: center;
            }

            /* Ensuring the items are next to each other in the navbar */
            .navbar-brand {
                color: white;
                font-size: 1.5rem; /* Slightly larger font size */
                margin-right: 20px; /* More space */
                font-weight: bold; /* Bold title */
            }

            /* Custom CSS for table and title */
            h2 {
                font-size: 2.2rem; /* Slightly smaller title */
                margin-bottom: 25px; /* More margin below title */
                color: #333; /* Darker text color */
            }

            .logout-section {
                text-align: right;
                margin-bottom: 20px;
            }
            .logout-section h4 {
                display: inline;
                margin-right: 15px;
                color: #555; /* Muted logout text */
            }

            .btn-create { /* Renamed class for clarity */
                margin-bottom: 20px;
                background-color: #28a745; /* Green for create */
                color: white;
                border: none;
                padding: 10px 20px;
                display: inline-block;
                border-radius: 5px; /* Rounded corners */
                text-decoration: none; /* Ensure it's not treated as a link visually */
            }
            .btn-create:hover {
                background-color: #218838; /* Darker green on hover */
            }

            /* Reset table alignment */
            .table th {
                vertical-align: middle;
                text-align: center; /* Keep headers centered */
                background-color: #f8f9fa; /* Light gray header background */
                border-bottom: 2px solid #dee2e6; /* Stronger header border */
                color: #495057; /* Darker header text */
            }

            .table td {
                vertical-align: middle;
                text-align: left;
                padding: 0.75rem; /* Standard padding */
            }
            .table td:nth-child(2) { /* Center align image column */
                text-align: center;
            }

            /* Custom buttons for edit and delete - slightly adjusted */
            .btn-edit, .btn-delete {
                border: none;
                padding: 8px 15px; /* Adjusted padding */
                border-radius: 5px;
                text-decoration: none; /* Remove underline if used as link */
                display: inline-block; /* Ensure inline display */
            }

            .btn-edit {
                background-color: #007bff; /* Blue for edit */
                color: white;
            }

            .btn-delete {
                background-color: #dc3545;
                color: white;
            }

            .btn-edit:hover {
                background-color: #0056b3; /* Darker blue on hover */
            }

            .btn-delete:hover {
                background-color: #c82333; /* Darker red on hover */
            }

            .btn-rounded {
                border-radius: 5px;
            }

            /* Ensuring proper table display */
            .table-container {
                margin-top: 20px;
                width: 100%;
                margin-left: auto;
                margin-right: auto;
            }
        </style>
    </head>
    <body>
        <!-- Navbar -->
        <nav class="navbar">
            <span class="navbar-brand">Employee Accounts</span> <!-- More HRM-like Navbar Title -->
        </nav>
        <a href="CreateAccount" class="btn btn-create btn-rounded">Create New Employee Account</a> <!-- More HRM-like Button Text -->
        <!-- Account Table -->
        <div class="table-container"> <!-- Added a container for better table management if needed -->
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Image</th> <!-- Image Column moved to second position -->
                        <th>Account Email</th>
                        <th>Account Password</th> <!-- Password moved to the end, less frequently needed -->
                        <th>Account Name</th>
                        <th>Account Role</th>
                        <th>Identity Card</th>
                        <th>User Address</th>
                        <th>Actions</th> <!-- Changed from empty <th> to "Actions" for clarity -->
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Step 1: Initialize DAO to interact with the database
                        AccountDAO dao = new AccountDAO();

                        // Step 2: Call method to get the list of accounts
                        ResultSet rs = dao.getAll();

                        // Step 3: Loop through the list of accounts
                        while (rs.next()) {
                    %>
                    <tr>
                        <td><%= rs.getInt("UserId")%></td>
                        <td style="text-align: center;"> <!-- Image Column in second position -->
                            <img src="<%= request.getContextPath() + rs.getString("UserImage")%>" alt="User Image" width="80" height="80" style="border-radius: 50%;"/> <!-- Slightly smaller image, circular style -->
                        </td>
                        <td><%= rs.getString("UserEmail")%></td>
                        <td><%= rs.getString("UserPassword")%></td> <!-- Password at the end -->
                        <td><%= rs.getString("UserName")%></td>
                        <td><%= rs.getString("UserRole")%></td>
                        <td><%= rs.getString("IdentityCard")%></td>
                        <td><%= rs.getString("UserAddress")%></td>
                        <td style="text-align: center;"> <!-- Centered buttons -->
                            <a href="UpdateAccount?id=<%= rs.getInt("UserId")%>" class="btn btn-edit btn-rounded">Edit</a>
                            <a href="DeleteAccount?id=<%= rs.getInt("UserId")%>" class="btn btn-delete btn-rounded">Delete</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Bootstrap JS CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>