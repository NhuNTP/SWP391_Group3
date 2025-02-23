<%-- 
    Document   : ViewCoupon
    Created on : Feb 22, 2025, 10:08:13 AM
    Author     : DELL-Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Coupon</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    </head>

    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">MANAGE COUPON</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">About us</a></li>
                    <li><a href="#">Contact Us</a></li>
                </ul>
            </div>
        </nav>
        <div class="container body-content">
            <h2>MANAGE COUPON</h2>
            <p>
                <a href="ManageCoupon/AddCoupon.jsp" class="btn btn-info" id="btnCreate">Add New</a>
            </p>
            <table class="table table-bordered"  method="post">
                <tr>
                    <th>Coupon ID</th>

                    <th>Discount Amount</th>
                    <th>Expiration Date</th>
                    <th>Coupon Status</th>
                </tr>
                <c:forEach items="${requestScope.couponList}" var="coupon">
                    <tr>
                        <Td valign="middle">${coupon.couponId}</td>
                        <Td valign="middle">${coupon.discountAmount}</td>
                        <Td valign="middle">${coupon.expirationDate}</td>
                        <Td valign="middle">${coupon.isUsed?'Used':'Not Used'}</td>

                        <Td valign="middle">


                            <form action="ManageCoupon/UpdateCoupon.jsp?couponID=${coupon.couponId}" method="post" style="display:inline;">
                                <input type="hidden" name="couponId" value="${coupon.couponId}">
                                <input type="hidden" name="discountAmount" value="${coupon.discountAmount}">
                                <input type="hidden" name="expirationDate" value="${coupon.expirationDate}">
                                <input type="hidden" name="isUsed" value="${coupon.isUsed?'Used':'Not Used'}">

                                <button type="submit" class="btn btn-warning">Update</button>
                            </form>

                            <form action="ManageCoupon/DeleteCoupon.jsp" method="post" style="display:inline;">
                                <input type="hidden" name="couponId" value="${coupon.couponId}">
                                <input type="hidden" name="discountAmount" value="${coupon.discountAmount}">
                                <input type="hidden" name="expirationDate" value="${coupon.expirationDate}">
                                <input type="hidden" name="isUsed" value="${coupon.isUsed?'Used':'Not Used'}">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>

                </c:forEach>


            </table>
        </div>

    </body>
</html>
