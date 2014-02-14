<%-- 
    Document   : delFromMenu
    Created on : Feb 12, 2014, 6:51:12 PM
    Author     : schereja
--%>

<%@page import="restaurant.dao.MenuItem"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
       <form method="POST" action="control.do?action=emp_update">
        <input type="submit" value="Add/Edit" name="addEdit" />&nbsp;
        <input type="submit" value="Delete" name="delete" />
        <br><br>
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr>
                <th>&nbsp;</th>
                <th align="left">Last Name</th>
                <th align="left">First Name</th>
                <th align="left">Email</th>
                <th align="right">Hire Date</th>
            </tr>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
            List<MenuItem> menuItems = (List<MenuItem>)request.getAttribute("menuItems");
            for(MenuItem mi : menuItems) {
        %>
        <tr>
            <td><input type="checkbox" name="id" value="<%=%>" /></td>
            <td align="left"><%=mi.getLastName()%></td>
            <td align="left"><%=emp.getFirstName()%></td>
            <td align="left"><%=emp.getEmail()%></td>
            <td align="right"><%=sdf.format(emp.getHireDate())%></td>
        </tr>
        <%
            }
        %>
        </table>
        <br>
        <input type="submit" value="Add/Edit" name="addEdit" />&nbsp;
        <input type="submit" value="Delete" name="delete" />
        </form>
    </body>
</html>
