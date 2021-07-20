<%--
  Created by IntelliJ IDEA.
  User: Lehend
  Date: 2021/6/21
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    pageContext.setAttribute("name6","lehend6",PageContext.SESSION_SCOPE);
    pageContext.forward("index.jsp");
%>
</body>
</html>
