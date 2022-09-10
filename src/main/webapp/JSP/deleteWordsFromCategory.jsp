<%@ page import="com.example.codenames.service.WordService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.DTO.WordAndCategoryDto" %>
<%@ page import="com.example.codenames.listener.NameConstants" %><%--
  Created by IntelliJ IDEA.
  User: ruska-ubuntu
  Date: 10.09.22
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DeleteFromCat</title>
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
    </style>
</head>
<body>
<div id="head">
    <div id="headLine">Delete word from Category</div>
</div>

<div align="center">
    <div id="user_list">

        <div align="center">
            <table border="1" cellpadding="5"  WIDTH=850>
                <tr>
                    <th>Word number</th>
                    <th>Word</th>
                    <th>Category</th>
                    <th>Delete</th>
                </tr>
                <%
                    ServletContext sc = request.getServletContext();
                    WordService service = (WordService) sc.getAttribute(NameConstants.WORD_SERVICE);
                    List<WordAndCategoryDto> wordAndCat = service.getWordsWithCategories();
                    int i = 1;
                    for(WordAndCategoryDto dto : wordAndCat ) {%>
                <tr>
                    <td><%=i%></td>
                    <td><%=dto.getWord()%></td>
                    <td><%=dto.getCategory()%></td>
                    <td>
                        <form action="../DeleteSingleWordFromCategoryServlet" method="post">
                            <input type="image" name="Name of image button" src="https://as1.ftcdn.net/v2/jpg/03/46/38/40/1000_F_346384068_e06I3cC4n0BCyB8f5PZ9cG2YR3N68ZYc.jpg" style="width: 40px; height: 40px"  alt="delete">
                            <input type="hidden" id=<%=NameConstants.WORDS%> name=<%=NameConstants.WORDS%> value=<%=dto.getWord()%>>
                            <input type="hidden"  name=<%=NameConstants.DELETE_FROM_CATEGORY%> value=<%=dto.getCategory()%>>                        </form>

                    </td>
                </tr>
                <% i++;
                } %>

            </table>
        </div>
    </div>
</div>


</body>
</html>
