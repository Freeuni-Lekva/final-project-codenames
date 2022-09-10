<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="com.example.codenames.service.WordService" %><%--
  Created by IntelliJ IDEA.
  User: keti
  Date: 09.09.22
  Time: 00:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style><%@include file="/JSP/forCategories.css"%></style>
<head>
    <title>Choose Categories</title>
</head>
<body>
<form action="../ChooseCategoriesServlet" method="post">
    <div>
        <%
            List<String> allCategories =
                    ((WordService) request.getServletContext().getAttribute(NameConstants.WORD_SERVICE)).getAllCategories();
            for (int i = 0; i < allCategories.size(); i++) {
        %>
        <input type="checkbox" id="<%="checkbox" + i%>" name ="<%="checkbox" + i%>"
               value="<%=allCategories.get(i)%>">
        <label for="<%="checkbox" + i%>"><%=allCategories.get(i)%></label>
        <br> <br> <br>
        <%
            }
        %>
    </div>
    <div>
        <button class="button_submit" role="button">Submit</button>
    </div>
</form>
</body>
</html>
