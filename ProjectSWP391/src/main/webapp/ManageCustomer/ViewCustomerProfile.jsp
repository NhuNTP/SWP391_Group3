<%-- 
    Document   : ViewCustomerProfile
    Created on : Feb 23, 2025, 8:44:38 PM
    Author     : HuynhPhuBinh
--%>

<%-- 
    Document   : ViewCustomerProfile
    Created on : Feb 23, 2025, 8:44:38 PM
    Author     : HuynhPhuBinh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Customer" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }

        .navbar {
            background-color: #0056b3;
            padding: 12px 15px;
            color: white;
            text-align: center;
            font-size: 1.5rem;
            font-weight: bold;
        }

        .container {
            max-width: 600px;
            margin: 40px auto;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #007bff;
        }

        p {
            font-size: 1.1rem;
            margin: 10px 0;
        }

        .btn {
            display: inline-block;
            padding: 8px 12px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
            border: none;
            cursor: pointer;
            text-align: center;
        }

        .btn-update {
            background-color: #007bff;
            color: white;
        }

        .btn-update:hover {
            background-color: #0056b3;
        }

        .btn-back {
            background-color: #6c757d;
            color: white;
        }

        .btn-back:hover {
            background-color: #5a6268;
        }

        .btn-container {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>

    <div class="navbar">Customer Profile</div>

    <div class="container">
        <h2>Customer Profile</h2>
        <% Customer customer = (Customer) request.getAttribute("customer"); 
           if (customer != null) { %>
            <p><strong>ID:</strong> <%= customer.getCustomerId() %></p>
            <p><strong>Name:</strong> <%= customer.getCustomerName() %></p>
            <p><strong>Phone:</strong> <%= customer.getCustomerPhone() %></p>
            <p><strong>Number of Payment: </strong> <%= customer.getNumberOfPayment() %></p>
            
            <div class="btn-container">
                <a href="UpdateCustomerProfile?customerId=<%= customer.getCustomerId() %>" class="btn btn-update">Update</a>
                <a href="ViewCustomerList" class="btn btn-back">Back</a>
            </div>
        <% } else { %>
            <p style="color: red; text-align: center;">Customer is not available!</p>
            <div class="btn-container">
                <a href="ViewCustomerList" class="btn btn-back">Back</a>
            </div>
        <% } %>
    </div>

</body>
</html>

