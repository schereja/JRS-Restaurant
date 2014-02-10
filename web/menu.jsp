<%-- 
    Document   : menu
    Created on : Feb 9, 2014, 7:21:55 PM
    Author     : schereja
--%>

<%@page import="java.util.List"%>
<%@page import="restaurant.dao.MenuItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Restaurant Menu</title>
    </head>
    <body>
        The following items are on the menu for today:
        <br />
        <%
         String msg = "";
        Object objMsg = request.getAttribute("menu");
        List<MenuItem> menuItems = (List)objMsg;
        for (MenuItem items : menuItems) {
             msg += items.getItemName() + " For $"+ items.getItemPrice() + "<br />";
        }
            %>
            <%= msg %>
    </body>
</html>
