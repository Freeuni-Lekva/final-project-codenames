<%--
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
    #user_list{
      border: 5px dashed orangered;
      position: absolute;
      margin: 1%;
      top: 10%;
      left: 150px;
      width: 80%;
      border-radius: 10px;
    }
    p{
      text-align: center;
      font-size: 24px;
    }

    form{
      text-align: center;
      margin-right: 60px;
    }

    input[type=submit] {
      margin-top: 12px;
      margin-left: 80px;
      padding:5px 15px;
      background:#ccc;
      border:black 5px;
      cursor:pointer;
      -webkit-border-radius: 5px;
      border-radius: 5px;
    }
  </style>

</head>
<body>
  <div id="head">
    <div id="headLine">Add words</div>
  </div>

  <form action="../AddSingleWordServlet" method="post">

      <label Enter Word to add: > </label> <input type="text" name="word"> <br>
      <label Enter Category > </label>  <input type="text" name = "category">
      <input type="submit" name="Addword" value="Addword">


  </form>

</body>
</html>
