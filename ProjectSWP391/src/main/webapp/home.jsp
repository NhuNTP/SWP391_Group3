<%@page import="Model.Account"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <%
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("LoginPage.jsp");
            return;
        }

        String role = account.getUserRole();
    %>
    
    <h2>Xin chào, <%= account.getUserName() %>!</h2>
    <p>Vai trò: <%= role %></p>

    <form action="logout" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit">Đăng xuất</button>
    </form>

    <%
        // Chỉ hiển thị nút nếu vai trò là admin hoặc manager
        if ("Admin".equals(role) || "Manager".equals(role)) {
    %>
        <!-- Gọi tới controller để thêm món mới -->
        <form action="addnewdish" method="get">
            <button type="submit">Thêm món mới</button>
        </form>
    <%
        }
    %>

</body>
</html>