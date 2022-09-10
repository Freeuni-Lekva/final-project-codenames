<%@ page import="com.example.codenames.Servlets.ServletUtils" %>
<%@ page import="com.example.codenames.listener.NameConstants" %><%--
  Created by IntelliJ IDEA.
  User: ruska-ubuntu
  Date: 10.09.22
  Time: 02:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AddWords</title>
  <style>
    body {
      margin: 0;
      overflow-x: hidden;
    }
    #head{
      position: absolute;
      height: 10%;
      width: 100%;
      background-color: chocolate;
      margin: 0;
    }
    #headLine {
      margin: 0;
      position: absolute;
      top: 50%;
      left: 50%;
      -ms-transform: translate(-50%, -50%);
      transform: translate(-50%, -50%);
      text-align: center;
      font-size: 3vw;
    }
    input[type=text], select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type=submit] {
        width: 100%;
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type=submit]:hover {
        background-color: #45a049;
    }

    div {
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
    }
  </style>

</head>
<body>

<div>
    <form action="AddSingleWordServlet" method="post">
        <label for="word">Word to add</label>
        <input type="text" placeholder="Your word.." id="word" name=<%=NameConstants.ADD_WORD_PARAMETER%>>

        <label for="category">Category </label>
        <input type="text" placeholder="Category.." id="category" name=<%=NameConstants.ADD_CATEGORY_PARAMETER%>>
        <input type="submit" name = "Submit" value="Submit">
    </form>
</div>

</body>
</html>
