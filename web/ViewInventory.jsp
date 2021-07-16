<%-- 
    Document   : ViewInventory
    Created on : May 8, 2021, 4:00:29 PM
    Author     : Bekalu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory</title>
    </head>
    <body>
        <br>
        Branch #: <b>${store.storeid}</b><br>
        Branch Name: <b>${store.storename}</b><br>
        Branch Location: <b>${store.storeaddr}</b>
        <br>
       ${msg}
        <c:if test="${u.adminlevel == 'Admn'}">
            <form action="ViewInventory" method="post">
                <input type="hidden" name="action" value="update" >
                <b><p>Book Cd:
                    <input type="text" name="bookcd" id="bookcd">
                    <input type="submit" value="Edit Record"> 
                </p></b>
            </form>
        </c:if>
        <table border="1">      
            <tr>
                <th>Store</th>
                <th>Book Cd</th>
                <th>Title</th>
                <th>Retail Price</th>
                <th>Quantity</th>
            </tr>
            <c:forEach var="b" items="${books}">
                <fmt:formatNumber value="${b.price}"
                                  type="currency" var="price"/>
                <tr>
                    <td align="right">${b.storeid}</td>
                    <td align="right">${b.bookcd}</td>
                    <td align="left">${b.title}</td>
                    <td align="right">${price}</td>
                    <td align="right">${b.onhand}</td>
                </tr>
            </c:forEach>
        </table>
        <a href="StoreSelection.jsp">Back to Stores</a>
    </body>
</html>
