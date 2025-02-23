<%@ page import="java.util.List" %>
<%@ page import="Model.Inventory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Thêm Món Mới</title>
</head>
<body>
    <h2>Thêm Món Mới</h2>
    <form action="addnewdish" method="post">
        Tên món: <input type="text" name="DishName" required><br>
        Loại món: 
        <select name="DishType">
            <option value="Food">Food</option>
            <option value="Drink">Drink</option>
        </select><br>
        Giá: <input type="number" name="DishPrice" step="0.01" required><br>
        Mô tả: <input type="text" name="DishDescription"><br>
        Hình ảnh (URL): <input type="text" name="DishImage"><br>

        <h3>Chọn nguyên liệu:</h3>
        <%
            List<Inventory> inventoryList = (List<Inventory>) request.getAttribute("inventoryList");
            if (inventoryList != null && !inventoryList.isEmpty()) {
                for (Inventory item : inventoryList) {%>
                    <input type="checkbox" name="ItemId" value="<%= item.getItemId()%>" onchange="toggleQuantity(this)"> 
                    <%= item.getItemName()%> - 
                    <input type="number" name="QuantityUsed_<%= item.getItemId()%>" min="1" required disabled> <%= item.getItemUnit()%><br>
            <%      }
            } else { %>
                <p>Không có nguyên liệu nào trong kho!</p>
            <% }%>

        <script>
            function toggleQuantity(checkbox) {
                var quantityInput = document.getElementsByName("QuantityUsed_" + checkbox.value)[0];
                quantityInput.disabled = !checkbox.checked;
                if (!checkbox.checked) {
                    quantityInput.value = ""; // Xóa giá trị nếu bỏ chọn
                }
            }
        </script>

        <button type="submit">Thêm Món</button>
    </form>
</body>
</html>